package io.seon.hackaton.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import io.seon.hackaton.entity.Address;
import io.seon.hackaton.entity.Phone;
import io.seon.hackaton.entity.Shipping;
import io.seon.hackaton.entity.User;


@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ShippingRepository shippingRepository;
    private final AddressRepository addressRepository;
    private final PhoneRepository phoneRepository;

    public UserRepository(JdbcTemplate jdbcTemplate, ShippingRepository shippingRepository, AddressRepository addressRepository, PhoneRepository phoneRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.shippingRepository = shippingRepository;
        this.addressRepository = addressRepository;
        this.phoneRepository = phoneRepository;
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        Optional<User> userOptional = jdbcTemplate.query(sql, new Object[]{id}, userRowMapper()).stream().findFirst();

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Load related shipping records
            List<Shipping> shippings = shippingRepository.findByUserId(user.getId());
            for (Shipping shipping : shippings) {
                // Load related addresses for each shipping
                List<Address> addresses = addressRepository.findByShippingId(shipping.getId());
                shipping.setAddresses(addresses);
            }
            user.setShippings(shippings);

            // Load related phone numbers
            List<Phone> phones = phoneRepository.findByUserId(user.getId());
            user.setPhones(phones);

            return Optional.of(user);
        }

        return Optional.empty();
    }


    public void save(User user) {
        String sql = "INSERT INTO users (username, full_name, place_of_birth, date_of_birth, created_by, updated_by, version) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getFullName(), user.getPlaceOfBirth(), user.getDateOfBirth(), user.getCreatedBy(), user.getUpdatedBy(), user.getVersion());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> User.builder()
          .id(rs.getLong("id"))
          .username(rs.getString("username"))
          .fullName(rs.getString("full_name"))
          .placeOfBirth(rs.getString("place_of_birth"))
          .dateOfBirth(rs.getDate("date_of_birth").toLocalDate())
          .createdBy(rs.getString("created_by"))
          .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
          .updatedBy(rs.getString("updated_by"))
          .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
          .version(rs.getInt("version"))
          .build();
    }
}