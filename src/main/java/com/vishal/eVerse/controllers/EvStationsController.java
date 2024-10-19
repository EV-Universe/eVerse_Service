package com.vishal.eVerse.controllers;

import com.vishal.eVerse.models.evStations.EvStations;
import com.vishal.eVerse.service.EvStationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stations")
@RequiredArgsConstructor
public class EvStationsController {
    private final EvStationsService evStationsService;
    @GetMapping("/all")
    public ResponseEntity<List<EvStations>> getAllStations() {
        return evStationsService.getAllStations();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStation(
        @RequestBody
        EvStations evStations
    ){
        return evStationsService.addStation(evStations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvStations> getStationById(
        @PathVariable
        Integer id
    ){
        return evStationsService.getStationById(id);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<EvStations>> getStationsByCity(
        @PathVariable
        String city
    ){
        return evStationsService.getStationsByCity(city);
    }
}
