package io.seon.hackaton.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import io.seon.hackaton.entity.Phone;

@Repository
public class PhoneRepository {
    private final JdbcTemplate jdbcTemplate;

    public PhoneRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Phone phone) {
        String sql = "INSERT INTO phone (user_id, phone_number, created_by, updated_by, version) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, phone.getUserId(), phone.getPhoneNumber(), phone.getCreatedBy(), phone.getUpdatedBy(), phone.getVersion());
    }

    public List<Phone> findByUserId(Long userId) {
        String sql = "SELECT * FROM phone WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, phoneRowMapper());
    }

    private RowMapper<Phone> phoneRowMapper() {
        return (rs, rowNum) -> Phone.builder()
          .id(rs.getLong("id"))
          .userId(rs.getLong("user_id"))
          .phoneNumber(rs.getString("phone_number"))
          .createdBy(rs.getString("created_by"))
          .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
          .updatedBy(rs.getString("updated_by"))
          .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
          .version(rs.getInt("version"))
          .build();
    }

}

