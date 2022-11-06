
package com.iconloop.score.example;
import score.*;

public class User {
    private Address address;
    public static int uniqueId2 =0;
    private  ArrayDB<Pool> pools  = Context.newArrayDB("pools" + String.valueOf(uniqueId2), Pool.class);
    private int poolCount;
    public User(Address address){
        this.address=address;
        this.poolCount=0;
        uniqueId2++;
    }
    public void addPool(Pool pool){ 
        this.poolCount++;
        Context.println(String.valueOf(this.poolCount));
        //Context.println(pool.name());
        // pools.set(this.poolCount,pool);
        pools.add(pool);
        Context.println(pools.get(this.poolCount-1).name());
    }
    public int getPoolCount(){
        return this.poolCount;
    }
    public Pool getPool(int index){
        Pool pool = this.pools.get(index);
        //Context.println(pool.name());
        return pool;

    }
    public Address getUser(){
        return this.address;
    }
    public void setpoolCount(int count){
        this.poolCount=count;
    }
    public static void writeObject(ObjectWriter w, User u) {
        w.beginList(1);

        w.write(
            u.address,
            u.poolCount
        );
        w.end();
    }
    public static User readObject(ObjectReader r) {
        r.beginList();
        User u = new User(r.readAddress());
        u.setpoolCount(r.readInt());
        r.end();
        return u;
    }
}
