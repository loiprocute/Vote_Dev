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
    private DictDB<Integer, Candidate> candidates = Context.newDictDB("candidates" + String.valueOf(uniqueId),Candidate.class);
    private DictDB<Address, Boolean> hasVoted = Context.newDictDB("hasVoted"+ String.valueOf(uniqueId),Boolean.class);
    public Pool(String name,String start,String end,Address manager) {
        this.active = true;
        this.start =start;
        this.end =end;
        this.name=name;
        this.candidatesCount=0;
        this.votersCount=0;
        this.manager =manager;
        uniqueId ++;

    }
    // Only Manager can call this function

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


    public void  addCandidate(String name, String qual, String imgURL){

        this.candidatesCount++;
        Candidate candidate= new Candidate(name, 0, qual, imgURL);
        this.candidates.set(candidatesCount,candidate);
        

    }
    public void Vote(Address voter,int _id){
        if (this.hasVoted.get(voter) == null){
            this.candidates.get(_id).addVotes();;
            this.hasVoted.set(voter,true);
            this.votersCount++;
        }
        else{
            Context.require(!this.hasVoted.get(voter));
            this.candidates.get(_id).addVotes();;;
            this.hasVoted.set(voter,true);
            this.votersCount++;
        }

    

    }

    public String toJsonFormat(){
        String list_candidate ="";
        list_candidate +="{" ;
        for( int i =1;i <= this.candidatesCount;i++){
            Candidate candidate = this.candidates.get(i);
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
