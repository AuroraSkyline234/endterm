package com.example.endterm.patterns;

import com.example.endterm.dto.ItemRequest;
import com.example.endterm.exception.InvalidInputException;
import com.example.endterm.model.GameItem;

public class GameItemFactory {

    public static GameItem create(ItemRequest req) {
        if (req == null) throw new InvalidInputException("Request body is required");

        if (!ConfigManager.getInstance().isAllowedType(req.type)) {
            throw new InvalidInputException("Invalid type. Allowed: WEAPON, POTION");
        }

        String type = req.type.toUpperCase();

        if ("WEAPON".equals(type)) {
            return new WeaponBuilder()
                    .name(req.name)
                    .weight(req.weight)
                    .goldValue(req.goldValue)
                    .damage(req.damage)
                    .backpackId(req.backpackId)
                    .build();
        }

        return new PotionBuilder()
                .name(req.name)
                .weight(req.weight)
                .goldValue(req.goldValue)
                .healAmount(req.healAmount)
                .backpackId(req.backpackId)
                .build();
    }
}
