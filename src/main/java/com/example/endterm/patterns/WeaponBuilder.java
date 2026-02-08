package com.example.endterm.patterns;

import com.example.endterm.model.Weapon;

public class WeaponBuilder {
    private int id = 0;
    private String name;
    private double weight;
    private int goldValue;
    private int damage;
    private int backpackId;

    public WeaponBuilder id(int id) { this.id = id; return this; }
    public WeaponBuilder name(String name) { this.name = name; return this; }
    public WeaponBuilder weight(double weight) { this.weight = weight; return this; }
    public WeaponBuilder goldValue(int goldValue) { this.goldValue = goldValue; return this; }
    public WeaponBuilder damage(int damage) { this.damage = damage; return this; }
    public WeaponBuilder backpackId(int backpackId) { this.backpackId = backpackId; return this; }

    public Weapon build() {
        Weapon w = new Weapon(id, name, weight, goldValue, damage);
        w.setBackpackid(backpackId);
        return w;
    }
}
