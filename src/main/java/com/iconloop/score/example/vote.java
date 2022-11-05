
package com.iconloop.score.example;
import java.math.BigInteger;
// package mypack;
// import mypack.*;
import java.util.*;
import score.Context;
import score.Address;
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

    public vote(){
        this.name ="vote";
        this.manager=Context.getOwner();
        this.UserCount =0;
    }
    
    
    @External(readonly = true)
    public String name(){

        return this.name;

    }
    
    @External(readonly = true)
    public Address Manager(){
        return this.manager;

    }
    @External(readonly = true)
    public int PoolCount(){
        return this.UserCount;
    }

    @External
    public void createPool(String poolName,String start,String end) {

        Address owner = Context.getCaller();
        if (Users.get(owner)==null){
            User user =new User(owner);
            Pool newPool = new Pool(poolName,start,end, owner);
            this.UserCount++;
            user.addPool(newPool);
            Users.set(owner,user);

        }
        else{
            User user = this.Users.get(owner);
            Pool newPool = new Pool(poolName,start,end, owner);
            user.addPool(newPool);
        }
    
    }
    
    // @External
    // public void closePool() {
    //     Address owner = Context.getCaller();
    //     Context.require(owner.equals(this.manager), "Not Admin");
    //     Pool pool = checkAndGetPool(this.poolCount);
    //     pool.setActive(false);
    //     pools.set(this.poolCount, pool);
    // }

    // @External
    // public void openPool() {
    //     Address owner = Context.getCaller();
    //     Context.require(owner.equals(this.manager), "Not Admin");
    //     Pool pool = checkAndGetPool(this.poolCount);
    //     pool.setActive(true);
    //     pools.set(this.poolCount, pool);
    // }

    @External
    public void addCandidate(int indexPool,String name, String qual, String imgURL){
        Address owner = Context.getCaller();
        User user = this.Users.get(owner);
        Context.require(user.getPool(indexPool) != null);
        Pool pool=user.getPool(indexPool);
        Context.require(owner.equals(pool.getManager()));
        pool.addCandidate(name, qual, imgURL);
        //Context.println(pool.toJsonFormat());
    }

    @External
    public void  Vote(Address manager,int indexVote,int indexPool){
        Address voter = Context.getCaller();
        Context.require(!voter.equals(manager));

        User user = this.Users.get(manager);
        //Context.println(user.getUser().toString());
        //Context.println(String.valueOf(user.getPoolCount()));

        Pool pool=user.getPool(indexPool);
        //Context.println(pool.getManager().toString());

        //Context.println(pool.name());
        pool.Vote(voter, indexVote);
        Context.println(pool.toJsonFormat());
        

    }
    public String searchPool(Address owner_pool,int indexPool){
        Context.require(Users.get(owner_pool)!=null);
        User user= Users.get(owner_pool);
        Context.require(user.getPool(indexPool)!=null);
        Pool pool = user.getPool(indexPool);
        return pool.toJsonFormat();
    }

    
}

//cx5563e469f9ed5b43cd2e58864624fdc69bdf7e5a
