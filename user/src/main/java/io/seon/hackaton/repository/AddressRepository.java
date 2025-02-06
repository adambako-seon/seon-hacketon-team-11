package io.seon.hackaton.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import io.seon.hackaton.entity.Address;


@Repository
public class AddressRepository {
    private final JdbcTemplate jdbcTemplate;

    public AddressRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Address address) {
        String sql = "INSERT INTO address (shipping_id, country, region, zip, street, created_by, updated_by, version) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, address.getShippingId(), address.getCountry(), address.getRegion(), address.getZip(), address.getStreet(), address.getCreatedBy(), address.getUpdatedBy(), address.getVersion());
    }

    public List<Address> findByShippingId(Long shippingId) {
        String sql = "SELECT * FROM address WHERE shipping_id = ?";
        return jdbcTemplate.query(sql, new Object[]{shippingId}, addressRowMapper());
    }

    private RowMapper<Address> addressRowMapper() {
        return (rs, rowNum) -> Address.builder()
          .id(rs.getLong("id"))
          .shippingId(rs.getLong("shipping_id"))
          .country(rs.getString("country"))
          .region(rs.getString("region"))
          .zip(rs.getString("zip"))
          .street(rs.getString("street"))
          .createdBy(rs.getString("created_by"))
          .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
          .updatedBy(rs.getString("updated_by"))
          .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
          .version(rs.getInt("version"))
          .build();
    }

}
