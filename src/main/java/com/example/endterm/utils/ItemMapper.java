package com.example.endterm.utils;

import com.example.endterm.dto.ItemResponse;
import com.example.endterm.model.GameItem;
import com.example.endterm.model.Potion;
import com.example.endterm.model.Weapon;

public class ItemMapper {
    public static ItemResponse toResponse(GameItem item) {
        ItemResponse r = new ItemResponse();
        r.id = item.getId();
        r.name = item.getName();
        r.weight = item.getWeight();
        r.goldValue = item.getGold_value();
        r.type = item.getType();
        r.backpackId = item.getBackpackid();

        if (item instanceof Weapon) r.damage = ((Weapon) item).getDamage();
        if (item instanceof Potion) r.healAmount = ((Potion) item).getHealAmount();
        return r;
    }
}
