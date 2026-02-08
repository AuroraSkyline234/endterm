package com.example.endterm.patterns;

import com.example.endterm.model.Potion;

public class PotionBuilder {
    private int id = 0;
    private String name;
    private double weight;
    private int goldValue;
    private int healAmount;
    private int backpackId;

    public PotionBuilder id(int id) { this.id = id; return this; }
    public PotionBuilder name(String name) { this.name = name; return this; }
    public PotionBuilder weight(double weight) { this.weight = weight; return this; }
    public PotionBuilder goldValue(int goldValue) { this.goldValue = goldValue; return this; }
    public PotionBuilder healAmount(int healAmount) { this.healAmount = healAmount; return this; }
    public PotionBuilder backpackId(int backpackId) { this.backpackId = backpackId; return this; }

    public Potion build() {
        Potion p = new Potion(id, name, weight, goldValue, healAmount);
        p.setBackpackid(backpackId);
        return p;
    }
}
