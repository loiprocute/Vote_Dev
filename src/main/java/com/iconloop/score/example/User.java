
package com.iconloop.score.example;
import score.*;

public class User {
    private Address address;
    private  DictDB<Integer, Pool> pools  = Context.newDictDB(Context.getCaller().toString(), Pool.class);
    private int poolCount;
    public User(Address address){
        this.address=address;
        this.poolCount=0;
    }
    public void addPool(Pool pool){
        this.poolCount++;
        this.pools.set(this.poolCount,pool);
    }
    public int getPoolCount(){
        return this.poolCount;
    }
    public Pool getPool(int index){
        return this.pools.get(index);

    }
    public Address getUser(){
        return this.address;
    }
}
