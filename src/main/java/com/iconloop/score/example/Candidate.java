package com.iconloop.score.example;
import score.*;

public class Candidate {
    private String name;
    private int votes;
    private String qualification;
    private String imageurl;

    public Candidate(String name,String qualification,String imageurl){
        this.name=name;
        this.qualification=qualification;
        this.imageurl=imageurl;
        this.votes=0;
    }

    public static void writeObject(ObjectWriter w, Candidate c) {
        w.beginList(1);
        w.write(
                c.name,
                c.qualification,
                c.imageurl,
                c.votes
        );
        w.end();
    }

    public static Candidate readObject(ObjectReader r) {
        r.beginList();
        Candidate c = new Candidate(r.readString(),r.readString(),r.readString());
        c.setVotes(r.readInt());
        r.end();
        return c;
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
    public void setVotes(int votes){
        this.votes =votes;
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
