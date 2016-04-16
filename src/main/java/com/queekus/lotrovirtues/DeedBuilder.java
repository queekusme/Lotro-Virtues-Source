package com.queekus.lotrovirtues;

import com.queekus.lotrovirtues.Models.Deed;

import java.util.List;

public class DeedBuilder {

    private String Virtue;
    private String Increase;
    private int Level;
    private String Type;
    private int Region;
    private String Name;
    private boolean Retired;

    public Deed toDeed(List<String> regions){ return new Deed(Virtue,Increase,Level,Type,regions.get(Region),Name,Retired); }
}
