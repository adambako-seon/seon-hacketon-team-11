package io.seon.hackaton.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, updatable = false)
    private String createdBy = "admin";

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private String updatedBy = "admin";

    @Version
    private int version;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "shipping")
    private Set<Address> addresses;

}