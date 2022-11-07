/*
 * Copyright 2020 ICONLOOP Inc.
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
import java.math.BigInteger;
import java.security.Principal;
// package mypack;
// import mypack.*;
import java.util.*;
import score.Context;
import score.Address;
import score.ArrayDB;
import score.DictDB;
import score.VarDB;
import score.BranchDB;
import score.annotation.EventLog;
import score.annotation.External;
import score.annotation.Optional;
import score.annotation.Payable;


public class vote {
    
    

    public String name ;
    public Address manager;
    public int UserCount;
    private  DictDB<Address, User> Users  = Context.newDictDB("User", User.class);
    private BranchDB<Address, DictDB<Integer, Pool>> pools = Context.newBranchDB("poolTasks", Pool.class);
    private BranchDB<Address, BranchDB<Integer,  ArrayDB<Candidate>>> List_Candidate =Context.newBranchDB("Candidates", Candidate.class);
    private DictDB<Address, String>  List_PoolDetail = Context.newDictDB("poolDetail", String.class);
    public vote(){
        this.name ="vote";
        this.manager=Context.getOwner();
        this.UserCount =0;

    }
    @Payable
    public void fallback() {}
    
    @External(readonly = true)
    public String name(){

        return this.name;

    }
    
    @External(readonly = true)
    public Address Manager(){
        return this.manager;

    }
    @External(readonly = true)
    public int UserCount(){
        return this.UserCount;
    }
    @External
    public void registration(Address address){
        Context.require(Users.get(address)==null,"exist Address");
        User user =new User(address);
        Users.set(address,user);

    }
    @External
    public void createPool(String poolName,String start,String end) {
        Address owner = Context.getCaller();
        Context.require(Users.get(owner)!=null,"not exist Address");
        
        User user = Users.get(owner);
        Pool newPool = new Pool(poolName,start,end, owner);
        pools.at(owner).set(user.getPoolCount()+1,newPool);
        user.setpoolCount(user.getPoolCount()+1);
        Users.set(owner,user);
        
    }
    
    @External
    public void addCandidate(int indexPool,String name, String qual, String imgURL){
        Address owner = Context.getCaller();
        Context.require(pools.at(owner) != null);
        Context.require(pools.at(owner).get(indexPool) != null);
        Pool pool=pools.at(owner).get(indexPool);

        Candidate newcandidate= new Candidate(name, qual, imgURL);
        List_Candidate.at(owner).at(indexPool).add(newcandidate);
        pool.setcandidatesCount(pool.getCandidateCount()+1);
        pools.at(owner).set(indexPool,pool);
        List_PoolDetail.set(owner,toJsonFormat(owner));

    }

    @External
    public void  Vote(Address manager_contract,int indexVote,int indexPool){
        Address voter = Context.getCaller();
        Context.require(Users.get(voter) != null,"you are not registered !");
        Context.require(!voter.equals(manager_contract),"you can't vote !");
        Context.require(pools.at(manager_contract).get(indexPool) != null,"not exist Pool");
        Context.require(List_Candidate.at(manager_contract).at(indexPool).get(indexVote-1) != null,"not Exist Candidate");

        Candidate candidate = List_Candidate.at(manager).at(indexPool).get(indexVote-1);
        candidate.setVotes(candidate.getNumVotes()+1);
        List_Candidate.at(manager).at(indexPool).set(indexVote-1, candidate);

        

    }
    
    @External(readonly = true)
    public String toJsonFormat_Pool(Address address){
        String result = List_PoolDetail.get(address);
        return result;
    }
    
    @External(readonly = true)
    public int getNumPoolByAddress(Address address){
        User user = getUser(address);
        return  user.getPoolCount();
    }

    @External(readonly = true)
    public String  Login(Address address){
        User user = getUser(address);
        Context.require(user != null,"No Registration !!!!!!!!!!!");
        return "success";
        
    }

    private User getUser(Address address){
        Context.require(Users.get(address) != null,"No Registration !!!!!!!!!!!");
        User user = Users.get(address);
        return user;
    }

    private Pool getPool(Address address,int index){
        Pool pool  =pools.at(address).get(index);
        Context.require(pool != null,"No Pool exist !!!!!!!!!!!");
        return pool;
    }
    private Candidate getCandidate(Address address,int indexPool,int indexCan){
        Candidate candidate = List_Candidate.at(address).at(indexPool).get(indexCan);
        Context.require(candidate != null,"No Candidate exist !!!!!!!!!!!");
        return candidate;
    }
    private String toJsonFormat(Address address){
        String list_candidate ="";
        list_candidate +="{" ;
        User user = getUser(address);
        Context.require(user.getPoolCount() >0,"No Pool exist !!!");
        //Context.println("Count Pool  = " + String.valueOf(user.getPoolCount()));
        for( int i =1;i <= user.getPoolCount();i++){
            Pool pool = getPool(address, i);
            list_candidate += "\""+pool.name()+"\":" +toJsonFormat_Candidate(address,i);
            if (i==user.getPoolCount()){
                break;
            }
            list_candidate +=",";

        }
        list_candidate +="}" ;
        Context.println(list_candidate);
        return list_candidate;
    }
    private String toJsonFormat_Candidate(Address address,int indexPool){
        String list_candidate ="";
        list_candidate +="{" ;
        Pool pool = getPool(address, indexPool);
        int cand= pool.getCandidateCount();
        //Context.println("Count Candidate  = " + String.valueOf(cand));
        for( int j =0;j < cand;j++){
            //Context.println( "truoc candidate ");
            Candidate candidate = getCandidate(address, indexPool, j);
            list_candidate += "\""+candidate.getName()+"\":" +candidate.toJsonFormat();
            if (j==cand-1){
                break;
            }
            list_candidate +=",";

        }
        list_candidate +="}" ;
        //Context.println(list_candidate);
        return list_candidate;
    }

    
}

//cx5563e469f9ed5b43cd2e58864624fdc69bdf7e5a
