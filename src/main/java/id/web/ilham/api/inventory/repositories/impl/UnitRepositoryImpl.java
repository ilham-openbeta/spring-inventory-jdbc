package id.web.ilham.api.inventory.repositories.impl;

import id.web.ilham.api.inventory.entities.Unit;
import id.web.ilham.api.inventory.repositories.UnitRepository;
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
public class UnitRepositoryImpl implements UnitRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Unit entity) {
        if (entity.getId() == null) {
            String sql = "INSERT INTO unit(code, description) VALUES (?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"id"});
                stmt.setString(1, entity.getCode());
                stmt.setString(2, entity.getDescription());
                return stmt;
            }, keyHolder);
            Integer id = Objects.requireNonNull(keyHolder.getKey()).intValue();
            entity.setId(id);
        } else {
            String sql = "UPDATE unit SET code=?, description=? WHERE id=?";
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, entity.getCode());
                stmt.setString(2, entity.getDescription());
                stmt.setInt(3, entity.getId());
                return stmt;
            });
        }
    }

    @Override
    public Boolean removeById(Integer id) {
        String sql = "DELETE FROM unit WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public Unit findById(Integer id) {
        String sql = "SELECT id, code, description FROM unit WHERE id = ?";
        return DataAccessUtils.singleResult(jdbcTemplate.query(sql, (rs, rowNum) ->
                new Unit(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("description")
                ), id));
    }

    @Override
    public List<Unit> findAll() {
        String sql = "SELECT id, code, description FROM unit";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Unit(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("description")
                ));
    }

    @Override
    public Boolean existsById(Integer id) {
        String sql = "SELECT 1 FROM unit WHERE id = ? LIMIT 1";
        Integer count = DataAccessUtils.singleResult(jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("1"), id));
        if (count != null) {
            return count > 0;
        } else {
            return false;
        }
    }
}
