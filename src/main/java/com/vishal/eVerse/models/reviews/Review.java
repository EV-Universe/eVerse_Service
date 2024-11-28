package com.vishal.eVerse.models.reviews;

import com.vishal.eVerse.models.evStations.EvStations;
import com.vishal.eVerse.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    private Integer id;
    private Double stars;
    private String comment;

    // This is many-to-one relationship between the Review and User entities.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // This is many-to-one relationship between the Review and EvStations entities.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private EvStations station;
}
