package com.vishal.eVerse.repository;

import com.vishal.eVerse.models.evStations.EvStations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EvStationsRepository extends JpaRepository<EvStations, Integer> {
    List<EvStations> findAllStationsByUserId(Integer id);
    Optional<EvStations> findByStationId(String stationId);
}
