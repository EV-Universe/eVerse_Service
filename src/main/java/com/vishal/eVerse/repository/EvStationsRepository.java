package com.vishal.eVerse.repository;

import com.vishal.eVerse.models.evStations.EvStations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EvStationsRepository extends JpaRepository<EvStations, Integer> {

    @Query("SELECT e FROM EvStations e WHERE e.city = :city")
    List<EvStations> findAllByCity(String city);

    Optional<EvStations> findById(Integer id);
}
