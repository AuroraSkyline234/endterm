package com.example.endterm.model;

public class Backpack {
    private int id;
    private String owner;
    private double maxWeight;

    public Backpack(int id, String owner, double maxWeight) {
        this.id = id;
        this.owner = owner;
        this.maxWeight = maxWeight;
    }

    public int getId() { return id; }
    public String getOwner() { return owner; }
    public double getMaxWeight() { return maxWeight; }

    public void setId(int id) { this.id = id; }
    public void setOwner(String owner) { this.owner = owner; }
    public void setMaxWeight(double maxWeight) { this.maxWeight = maxWeight; }
}
