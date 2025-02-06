package io.seon.hackaton.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.seon.hackaton.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findById(Integer id);

    @Query("SELECT u FROM User u " +
      "LEFT JOIN FETCH u.shippings " +
      "LEFT JOIN FETCH u.phones " +
      "WHERE u.id = :id")
    Optional<User> findByIdWithRelations(@Param("id") Integer id);
}
