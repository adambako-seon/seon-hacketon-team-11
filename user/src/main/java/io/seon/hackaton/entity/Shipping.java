package io.seon.hackaton.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "shipping")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String createdBy = "admin";
    private LocalDateTime createdAt;
    private String updatedBy = "admin";
    private LocalDateTime updatedAt;
    private int version = 1;

    @Transient // This ensures it's not persisted directly in the database
    private List<Address> addresses;
}
