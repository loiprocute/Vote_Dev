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
    private ArrayDB<Candidate> candidates = Context.newArrayDB("candidates" + String.valueOf(uniqueId),Candidate.class);
    private DictDB<Address, Boolean> hasVoted = Context.newDictDB("hasVoted"+ String.valueOf(uniqueId),Boolean.class);
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
    public void  addCandidate(Candidate candidate){
        this.setcandidatesCount(this.getCandidateCount()+1);
        Context.println("count : " + String.valueOf(this.candidatesCount));
        //Candidate candidate= new Candidate(name, 0, qual, imgURL);
        Context.println(candidate.getName());
        candidates.add(candidate);
        Context.println(candidates.get(0).getName());
        //Context.println("context" + candidates.get(1).getName());
        //Context.println("count" + String.valueOf(this.candidatesCount));
        

    }
    public void Vote(Address voter,int _id){
        if (this.hasVoted.get(voter) == null){
            this.candidates.get(_id).setVotes(this.candidates.get(_id).getNumVotes()+1);
            this.hasVoted.set(voter,true);
            this.setvotersCount(this.votersCount+1);
        }
        else{
            Context.require(!this.hasVoted.get(voter));
            this.candidates.get(_id).setVotes(this.candidates.get(_id).getNumVotes()+1);
            this.hasVoted.set(voter,true);
            this.setvotersCount(this.votersCount+1);
        }

    

    }

    public String toJsonFormat(){
        String list_candidate ="";
        list_candidate +="{" ;
        Context.println("count candidate = " + String.valueOf(this.candidatesCount));
        //Context.println("candidate = " + this.candidates.get(1).getName());

        for( int i =1;i <= this.candidatesCount;i++){
            Context.println( "truoc candidate ");
            Candidate candidate = this.candidates.get(i);
            Context.println( "sau candidate succ");
            list_candidate += "\""+candidate.getName()+"\":" +candidate.toJsonFormat();
            if (i==this.candidatesCount){
                break;
            }
            list_candidate +=",";

        }
        list_candidate +="}" ;
        return list_candidate;
    }

 


}
