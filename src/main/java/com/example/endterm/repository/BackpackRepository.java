package com.example.endterm.repository;

import com.example.endterm.exception.DatabaseOperationException;
import com.example.endterm.model.Backpack;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BackpackRepository {
    private final JdbcTemplate jdbc;

    public BackpackRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public int create(Backpack b) {
        try {
            String sql = "INSERT INTO backpacks(owner, max_weight, is_deleted) VALUES (?, ?, FALSE) RETURNING id";
            Integer id = jdbc.queryForObject(sql, Integer.class, b.getOwner(), b.getMaxWeight());
            return id == null ? 0 : id;
        } catch (Exception e) {
            throw new DatabaseOperationException("DB create backpack failed: " + e.getMessage(), e);
        }
    }

    public List<Backpack> findAll() {
        try {
            return jdbc.query(
                    "SELECT * FROM backpacks WHERE is_deleted = FALSE ORDER BY id",
                    (rs, rowNum) -> new Backpack(
                            rs.getInt("id"),
                            rs.getString("owner"),
                            rs.getDouble("max_weight")
                    )
            );
        } catch (Exception e) {
            throw new DatabaseOperationException("DB findAll backpacks failed: " + e.getMessage(), e);
        }
    }

    public Backpack findById(int id) {
        try {
            List<Backpack> list = jdbc.query(
                    "SELECT * FROM backpacks WHERE id=? AND is_deleted = FALSE",
                    (rs, rowNum) -> new Backpack(
                            rs.getInt("id"),
                            rs.getString("owner"),
                            rs.getDouble("max_weight")
                    ),
                    id
            );
            return list.isEmpty() ? null : list.get(0);
        } catch (Exception e) {
            throw new DatabaseOperationException("DB find backpack failed: " + e.getMessage(), e);
        }
    }


    public int delete(int id) {
        try {
            return jdbc.update(
                    "UPDATE backpacks SET is_deleted = TRUE WHERE id=? AND is_deleted = FALSE",
                    id
            );
        } catch (Exception e) {
            throw new DatabaseOperationException("DB delete backpack failed: " + e.getMessage(), e);
        }
    }

    public boolean hasActiveItems(int backpackId) {
        try {
            Integer count = jdbc.queryForObject(
                    "SELECT COUNT(*) FROM items WHERE backpack_id = ? AND is_deleted = FALSE",
                    Integer.class,
                    backpackId
            );
            return count != null && count > 0;
        } catch (Exception e) {
            throw new DatabaseOperationException("DB check active items failed: " + e.getMessage(), e);
        }
    }
}
