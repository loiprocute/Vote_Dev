package com.iconloop.score.example;
import score.*;

public class Pool {
    public boolean active;
    public String start;
    public String end;
    public String name;
    public int candidatesCount;
    public int votersCount;
    public Address manager;
    public static int uniqueId =0;
    public Pool(String name,String start,String end,Address manager) {
        this.active = true;
        this.start =start;
        this.end =end;
        this.name=name;
        this.manager =manager;
        this.candidatesCount=0;
        this.votersCount=0;
        uniqueId ++;

    }
    public static void writeObject(ObjectWriter w, Pool p) {
        w.beginList(3);
        w.write(
            p.name,
            p.start,
            p.end,
            p.manager,
            p.active,
            p.candidatesCount,
            p.votersCount
        );
        w.end();
    }

    public static Pool readObject(ObjectReader r) {
        r.beginList();
        Pool p = new Pool(r.readString(),r.readString(),r.readString(),r.readAddress());
        p.setActive(r.readBoolean());
        p.setcandidatesCount(r.readInt());
        p.setvotersCount(r.readInt());
        r.end();
        return p;
    }

    public String name(){

        return this.name;

    }
    public Address getManager(){
        return this.manager;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public boolean getActive() {
        return this.active;
    }
    public int getCandidateCount(){
        return this.candidatesCount;
    }
    public void setcandidatesCount(int candidatesCount){
        this.candidatesCount=candidatesCount;
    }
    public void setvotersCount(int votersCount){
        this.votersCount=votersCount;
    }

}
