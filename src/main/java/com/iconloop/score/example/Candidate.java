package com.iconloop.score.example;

public class Candidate {
    private String name;
    private int votes;
    private String qualification;
    private String imageurl;

    public Candidate(String name,int votes,String qualification,String imageurl){
        this.name=name;
        this.votes=votes;
        this.qualification=qualification;
        this.imageurl=imageurl;
    }
    public String getImageurl() {
        return this.imageurl;
    }
    public String getName(){
        return this.name;
    }
    public String getQual(){
        return this.qualification;
    }
    public void addVotes(){
        this.votes ++;
    }
    public int getNumVotes(){
        return this.votes;
    }
    public String getVotes(){
        return String.valueOf(this.votes);
    }
    public String toJsonFormat() {
        return "{" +
                "\"name\":\"" + this.getName() +
                "\",\"votes\":\"" + this.getVotes() +
                "\",\"qualification\":\"" + this.getQual() +
                "\",\"imageurl\":\"" + this.getImageurl() +
                "\"}";
                
    }
}
