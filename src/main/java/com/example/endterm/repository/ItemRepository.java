package com.example.endterm.repository;

import com.example.endterm.exception.DatabaseOperationException;
import com.example.endterm.model.GameItem;
import com.example.endterm.model.Potion;
import com.example.endterm.model.Weapon;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepository {

    private final JdbcTemplate jdbc;

    public ItemRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<GameItem> mapper = (rs, rowNum) -> {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double weight = rs.getDouble("weight");
        int gold = rs.getInt("gold_value");
        String type = rs.getString("type");
        int backpackId = rs.getInt("backpack_id");

        GameItem item;
        if ("WEAPON".equals(type)) {
            int damage = rs.getInt("damage");
            item = new Weapon(id, name, weight, gold, damage);
        } else {
            int heal = rs.getInt("heal_amount");
            item = new Potion(id, name, weight, gold, heal);
        }
        item.setBackpackid(backpackId);
        return item;
    };

    public int create(GameItem item) {
        try {
            String sql = "INSERT INTO items(name, weight, gold_value, type, backpack_id, damage, heal_amount, is_deleted) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, FALSE) RETURNING id";

            int damage = (item instanceof Weapon) ? ((Weapon) item).getDamage() : 0;
            int heal = (item instanceof Potion) ? ((Potion) item).getHealAmount() : 0;

            Integer id = jdbc.queryForObject(sql, Integer.class,
                    item.getName(),
                    item.getWeight(),
                    item.getGold_value(),
                    item.getType(),
                    item.getBackpackid(),
                    damage,
                    heal
            );

            return id == null ? 0 : id;
        } catch (Exception e) {
            throw new DatabaseOperationException("DB create failed: " + e.getMessage(), e);
        }
    }

    // EXCLUDE deleted
    public GameItem findById(int id) {
        try {
            List<GameItem> list = jdbc.query(
                    "SELECT * FROM items WHERE id = ? AND is_deleted = FALSE",
                    mapper, id
            );
            return list.isEmpty() ? null : list.get(0);
        } catch (Exception e) {
            throw new DatabaseOperationException("DB getById failed: " + e.getMessage(), e);
        }
    }

    // EXCLUDE deleted
    public List<GameItem> findAll() {
        try {
            return jdbc.query(
                    "SELECT * FROM items WHERE is_deleted = FALSE ORDER BY id",
                    mapper
            );
        } catch (Exception e) {
            throw new DatabaseOperationException("DB findAll failed: " + e.getMessage(), e);
        }
    }

    // DO NOT update deleted
    public int updateBasic(int id, String name, double weight, int goldValue) {
        try {
            return jdbc.update(
                    "UPDATE items SET name=?, weight=?, gold_value=? WHERE id=? AND is_deleted = FALSE",
                    name, weight, goldValue, id
            );
        } catch (Exception e) {
            throw new DatabaseOperationException("DB update failed: " + e.getMessage(), e);
        }
    }

    public int delete(int id) {
        try {
            return jdbc.update(
                    "UPDATE items SET is_deleted = TRUE WHERE id=? AND is_deleted = FALSE",
                    id
            );
        } catch (Exception e) {
            throw new DatabaseOperationException("DB delete failed: " + e.getMessage(), e);
        }
    }
}
