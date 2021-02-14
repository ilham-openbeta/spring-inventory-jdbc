package id.web.ilham.api.inventory.repositories.impl;

import id.web.ilham.api.inventory.entities.Item;
import id.web.ilham.api.inventory.entities.ItemWithUnit;
import id.web.ilham.api.inventory.entities.Unit;
import id.web.ilham.api.inventory.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Item entity) {
        if (entity.getId() == null) {
            String sql = "INSERT INTO item(name, price, image_url, original_filename, unit_id) VALUES (?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"id"});
                stmt.setString(1, entity.getName());
                stmt.setInt(2, entity.getPrice());
                stmt.setString(3, entity.getImageUrl());
                stmt.setString(4, entity.getOriginalFilename());
                stmt.setInt(5, entity.getUnitId());
                return stmt;
            }, keyHolder);
            int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
            entity.setId(id);
        } else {
            String sql = "UPDATE item SET name=?, price=?, image_url=?, original_filename=?, unit_id=? WHERE id=?";
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, entity.getName());
                stmt.setInt(2, entity.getPrice());
                stmt.setString(3, entity.getImageUrl());
                stmt.setString(4, entity.getOriginalFilename());
                stmt.setInt(5, entity.getUnitId());
                stmt.setInt(6, entity.getId());
                return stmt;
            });
        }
    }

    @Override
    public Boolean removeById(Integer id) {
        String sql = "DELETE FROM item WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public Item findById(Integer id) {
        String sql = "SELECT id, name, price, image_url, original_filename, unit_id FROM item WHERE id = ?";
        return DataAccessUtils.singleResult(jdbcTemplate.query(sql, (rs, rowNum) ->
                new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("image_url"),
                        rs.getString("original_filename"),
                        rs.getInt("unit_id")
                ), id));
    }

    @Override
    public ItemWithUnit findByIdWithUnit(Integer id) {
        String sql = "SELECT item.id AS id, name, price, image_url, original_filename, unit_id, code, description FROM item " +
                "JOIN unit ON unit.id=item.unit_id " +
                "WHERE item.id = ?";
        return DataAccessUtils.singleResult(jdbcTemplate.query(sql, (rs, rowNum) -> {
            Unit unit = new Unit();
            unit.setId(rs.getInt("unit_id"));
            unit.setCode(rs.getString("code"));
            unit.setDescription(rs.getString("description"));
            return new ItemWithUnit(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("image_url"),
                    rs.getString("original_filename"),
                    unit
            );
        }, id));
    }

    @Override
    public List<Item> findAll() {
        String sql = "SELECT id, name, price, image_url, original_filename, unit_id FROM item";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("image_url"),
                        rs.getString("original_filename"),
                        rs.getInt("unit_id")
                )
        );
    }

    @Override
    public Boolean existsById(Integer id) {
        String sql = "SELECT 1 FROM item WHERE id = ? LIMIT 1";
        Integer count = DataAccessUtils.singleResult(jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("1"), id));
        if (count != null) {
            return count > 0;
        } else {
            return false;
        }
    }
}
