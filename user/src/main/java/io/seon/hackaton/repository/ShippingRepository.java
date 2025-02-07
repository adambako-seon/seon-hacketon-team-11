package io.seon.hackaton.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.seon.hackaton.entity.Shipping;



@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {}