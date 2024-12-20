package com.vishal.eVerse.service;

import com.vishal.eVerse.models.evStations.EvStations;
import com.vishal.eVerse.repository.EvStationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvStationsService {
    private final EvStationsRepository evStationsRepository;
    public ResponseEntity<List<EvStations>> getAllStations() {

        return ResponseEntity.ok(evStationsRepository.findAll());
    }

    public ResponseEntity<EvStations> getStationById(Integer id) {
        if (evStationsRepository.findById(id).isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(evStationsRepository.findById(id).orElse(null));
    }

    public  ResponseEntity<?> addStation(EvStations evStations) {
        return ResponseEntity.ok(evStationsRepository.save(evStations));
    }

    public ResponseEntity<?> updateStation(EvStations evStations) {
        if (evStationsRepository.findById(evStations.getId()).isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(evStationsRepository.save(evStations));
    }

    public ResponseEntity<?> deleteStation(Integer id) {
        evStationsRepository.deleteById(id);
        return ResponseEntity.ok("Station deleted successfully");
    }

    public ResponseEntity<List<EvStations>> getStationsByCity(String city) {
        if (evStationsRepository.findAllByCity(city).isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(evStationsRepository.findAllByCity(city));
    }

}
