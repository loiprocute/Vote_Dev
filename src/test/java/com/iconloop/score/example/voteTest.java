/*
 * Copyright 2021 ICONLOOP Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iconloop.score.example;
import com.iconloop.score.test.Account;
import com.iconloop.score.test.Score;
import com.iconloop.score.test.ServiceManager;
import com.iconloop.score.test.TestBase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import score.Address;
import score.Context;

class voteTest extends TestBase {
    private static final ServiceManager sm = getServiceManager();
    private static final Account owner = sm.createAccount(10);
    private static final Account alice = sm.createAccount(10);
    private static final Account bob = sm.createAccount(10);
    private static final Account jax = sm.createAccount(10);
    private static final Account nana = sm.createAccount(10);
    private static final Account jelly = sm.createAccount(10);
    private Score voteScore;
    @BeforeEach
    public void setup() throws Exception{
       voteScore = sm.deploy(owner,vote.class);
    }

    @Test
    public void checkname() {
        Context.require(voteScore.call("name") == "vote","success");
    }

    @Test
    public void checkmanager() {;
        Context.require(voteScore.call("Manager") == owner.getAddress());
    }
    
    @Test
    public void checkCaller() {

        voteScore.invoke(owner,"createPool","thi hoa hau","2","3");
        voteScore.invoke(owner,"createPool","thi hoa hau1","22","33");

        voteScore.invoke(owner,"createPool","thi hoa hau2","2q2","3a3");
        voteScore.invoke(owner,"addCandidate",2,"nghuuloi","deptrai","111");
        voteScore.invoke(owner,"addCandidate",2,"nghuulam","deptraithualoi","122");

        voteScore.invoke(bob,"createPool","thi hoa hau3","aaa","3a3a");
        voteScore.invoke(bob,"addCandidate",1,"nghuulang","deptrai","111a");
        voteScore.invoke(bob,"addCandidate",1,"nghuuanh","dxau","122b");

        voteScore.invoke(nana,"Vote",bob.getAddress(),2,1);
        voteScore.invoke(alice,"Vote",bob.getAddress(),2,1);
        voteScore.invoke(jax,"Vote",bob.getAddress(),2,1);
        voteScore.invoke(jelly,"Vote",bob.getAddress(),1,1);

        voteScore.invoke(nana,"Vote",owner.getAddress(),2,2);
        voteScore.invoke(alice,"Vote",owner.getAddress(),2,2);
        voteScore.invoke(jax,"Vote",owner.getAddress(),2,2);
        voteScore.invoke(jelly,"Vote",owner.getAddress(),1,2);

        // voteScore.invoke(alice,"Vote",alice.getAddress(),1);
        // voteScore.invoke(bob,"Vote",bob.getAddress(),1);
        
        // Context.println(String.valueOf(voteScore.call("PoolCount")));
        // voteScore.invoke(bob,"Vote",1);
        // voteScore.invoke(owner,"electionResult");

    //     //Context.println( voteScore.call("electionResult"));
    //     //Context.require(voteScore.call("electionResult") == "1");
     }

}
