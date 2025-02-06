package io.seon.hackaton.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long shippingId;
    private String country;
    private String region;
    private String zip;
    private String street;
    private String createdBy = "admin";
    private LocalDateTime createdAt;
    private String updatedBy = "admin";
    private LocalDateTime updatedAt;
    private int version = 1;
}

