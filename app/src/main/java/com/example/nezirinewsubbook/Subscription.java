package com.example.nezirinewsubbook;

//The constructor class that is used to create all the new Subscriptions

public class Subscription {
    private String name;
    private String date;
    private String comment;
    private String charge;

    //It takes as an input the name, date, charge, and comment from the user
    public Subscription(String name, String date, String charge, String comment){
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comment = comment;
    }

    public String getName(){
        return this.name;
    }

    public String getDate(){
        return this.date;
    }

    public String getCharge(){
        return this.charge;
    }

    public String getComment(){
        return this.comment;
    }
}
