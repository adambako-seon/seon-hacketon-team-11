package io.seon.hackaton.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.seon.hackaton.entity.Shipping;
import io.seon.hackaton.entity.User;


@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {
    List<Shipping> findByUser(User user);
}