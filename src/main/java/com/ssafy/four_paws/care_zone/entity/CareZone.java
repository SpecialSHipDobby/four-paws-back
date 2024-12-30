package com.ssafy.four_paws.care_zone.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "care_zone")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private int latitude;

    @Column(nullable = false)
    private int longitude;

    @Column
    private String contactNumber;
    @Column
    private String operatingHours;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;
}
