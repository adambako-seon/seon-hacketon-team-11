package io.seon.hackaton.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import io.seon.hackaton.entity.Shipping;


@Repository
public class ShippingRepository {
    private final JdbcTemplate jdbcTemplate;

    public ShippingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Shipping shipping) {
        String sql = "INSERT INTO shipping (user_id, created_by, updated_by, version) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, shipping.getUserId(), shipping.getCreatedBy(), shipping.getUpdatedBy(), shipping.getVersion());
    }

    public List<Shipping> findByUserId(Long userId) {
        String sql = "SELECT * FROM shipping WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, shippingRowMapper());
    }

    public void saveOrUpdate(Shipping shipping) {
        String sql = "INSERT INTO shipping (user_id) VALUES (?) ON CONFLICT (id) DO UPDATE SET user_id = EXCLUDED.user_id";
        jdbcTemplate.update(sql, shipping.getUserId());
    }


    private RowMapper<Shipping> shippingRowMapper() {
        return (rs, rowNum) -> Shipping.builder()
          .id(rs.getLong("id"))
          .userId(rs.getLong("user_id"))
          .createdBy(rs.getString("created_by"))
          .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
          .updatedBy(rs.getString("updated_by"))
          .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
          .version(rs.getInt("version"))
          .build();
    }

}
