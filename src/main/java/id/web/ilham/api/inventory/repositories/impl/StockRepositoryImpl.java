package id.web.ilham.api.inventory.repositories.impl;

import id.web.ilham.api.inventory.entities.Item;
import id.web.ilham.api.inventory.entities.Stock;
import id.web.ilham.api.inventory.entities.StockSummary;
import id.web.ilham.api.inventory.entities.StockWithItem;
import id.web.ilham.api.inventory.repositories.StockRepository;
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
public class StockRepositoryImpl implements StockRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Stock entity) {
        if (entity.getId() == null) {
            String sql = "INSERT INTO stock(item_id, quantity) VALUES (?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"id"});
                stmt.setInt(1, entity.getItemId());
                stmt.setInt(2, entity.getQuantity());
                return stmt;
            }, keyHolder);
            Integer id = Objects.requireNonNull(keyHolder.getKey()).intValue();
            entity.setId(id);
        } else {
            String sql = "UPDATE stock SET item_id=?, quantity=? WHERE id=?";
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, entity.getItemId());
                stmt.setInt(2, entity.getQuantity());
                stmt.setInt(3, entity.getId());
                return stmt;
            });
        }
    }

    @Override
    public Boolean removeById(Integer id) {
        String sql = "DELETE FROM stock WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public Stock findById(Integer id) {
        String sql = "SELECT id, item_id, quantity FROM stock WHERE id = ?";
        return DataAccessUtils.singleResult(jdbcTemplate.query(sql, (rs, rowNum) ->
                new Stock(
                        rs.getInt("id"),
                        rs.getInt("item_id"),
                        rs.getInt("quantity")
                ), id));
    }

    @Override
    public StockWithItem findByIdWithItem(Integer id) {
        String sql = "SELECT stock.id AS id, item_id, quantity, name, price, image_url, original_filename, unit_id FROM stock " +
                "JOIN item ON item.id=stock.item_id " +
                "WHERE stock.id = ?";
        return DataAccessUtils.singleResult(jdbcTemplate.query(sql, (rs, rowNum) ->
        {
            Item item = new Item(
                    rs.getInt("item_id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("image_url"),
                    rs.getString("original_filename"),
                    rs.getInt("unit_id")
            );
            return new StockWithItem(
                    rs.getInt("id"),
                    item,
                    rs.getInt("quantity")
            );
        }, id));
    }

    @Override
    public List<Stock> findAll() {
        String sql = "SELECT id, item_id, quantity FROM stock";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Stock(
                        rs.getInt("id"),
                        rs.getInt("item_id"),
                        rs.getInt("quantity")
                ));
    }

    @Override
    public List<StockSummary> findAllSummaries() {
        String sql = "SELECT i.id AS id, image_url, name, original_filename, price, SUM(s.quantity) AS quantity " +
                "FROM stock s " +
                "JOIN item i ON i.id = s.item_id " +
                "GROUP BY i.id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Item item = new Item();
            item.setId(rs.getInt("id"));
            item.setImageUrl(rs.getString("image_url"));
            item.setName(rs.getString("name"));
            item.setName(rs.getString("original_filename"));
            item.setPrice(rs.getInt("price"));
            return new StockSummary(item, rs.getLong("quantity"));
        });
    }
}
