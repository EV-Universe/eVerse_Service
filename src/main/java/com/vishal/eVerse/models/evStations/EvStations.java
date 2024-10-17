package com.vishal.eVerse.models.evStations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvStations {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private  Double latitude;
    private Double longitude;
    private  Integer curSlotAvailable;
    private  String waitingTime;
    private  String address;
    private  String city;
    private  String state;
    private  String zip;

}
