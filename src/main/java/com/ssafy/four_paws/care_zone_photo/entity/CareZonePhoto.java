package com.ssafy.four_paws.care_zone_photo.entity;

import com.ssafy.four_paws.care_zone.entity.CareZone;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "care_zone_photo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareZonePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(nullable = false)
    private String photoUrl;
    @Column
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_zone_id", nullable = false)
    private CareZone careZone;
}
