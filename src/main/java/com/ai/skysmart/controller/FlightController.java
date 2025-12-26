package com.ai.skysmart.controller;

import com.ai.skysmart.dto.CreateFlightRequest;
import com.ai.skysmart.dto.FlightDto;
import com.ai.skysmart.service.FlightService;
import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private ModelMapper modelMapper;

    // CREATE FLIGHT
    @PostMapping("/create")
    public ResponseEntity<FlightDto> createFlight(@Valid @RequestBody CreateFlightRequest request) {
        FlightDto dto = modelMapper.map(request, FlightDto.class);
        FlightDto saved = flightService.createFlight(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // GET ALL FLIGHTS
    @GetMapping("/")
    public ResponseEntity<List<FlightDto>> getAllFlight() {
        List<FlightDto> flights = flightService.getAllFlight();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    // GET BY FLIGHT NUMBER (returns all flights with that number)
    @GetMapping("/number/{flightNumber}")
    public ResponseEntity<List<FlightDto>> getByFlightNum(@PathVariable String flightNumber) {
        List<FlightDto> flights = flightService.getFlightByFlightNum(flightNumber);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    // GET BY ORIGIN & DESTINATION
    @GetMapping("/search")
    public ResponseEntity<List<FlightDto>> getByFlightDestinationAndOrigin(
            @RequestParam String origin,
            @RequestParam String destination) {

        List<FlightDto> flights = flightService.getFlightOnBasisOriginAndDestination(origin, destination);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
//    @GetMapping("/searchbasedondateorigindestination")
//    public ResponseEntity<List<FlightDto>> getByFlightDestinationAndOriginAndDte(
//            @RequestParam String origin,
//            @RequestParam String destination,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate journeyDate,
//            ) {
//
//        List<FlightDto> flights = flightService.getFlightOnBasisOriginAndDestinationAndJourneyDate(origin, destination, journeyDate);
//
//        return new ResponseEntity<>(flights, HttpStatus.OK);
//    }
    @GetMapping("/searchbasedondateorigindestination")
    public ResponseEntity<Page<FlightDto>> getByFlightDestinationAndOriginAndDte(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate journeyDate,
            @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "5") int size) {

        //With Pagination
        Page<FlightDto> flights = flightService
                .getFlightOnBasisOriginAndDestinationAndJourneyDate(origin, destination,
                        journeyDate, page, size);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    //Using soring , Pagintion
    @GetMapping("/searchdateorigindestSoring")
    public ResponseEntity<Page<FlightDto>> getByFlightDestinationAndOriginAndDteSoring(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate journeyDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "price") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<FlightDto> flights = flightService
                .getFlightOnOriginDestJourneyDateSort(origin, destination, journeyDate, page, size,
                        sortBy,sortDir);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    // DELETE BY FLIGHT ID
    @DeleteMapping("/{flightId}")
    public ResponseEntity<String> cancelFlight(@PathVariable Long flightId) {
        String result = flightService.cancelFlight(flightId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // UPDATE BY FLIGHT ID
    @PutMapping("/{flightId}")
    public ResponseEntity<FlightDto> updateFlight(
            @PathVariable Long flightId,
            @Valid @RequestBody FlightDto dto) {

        dto.setId(flightId);// ensure the DTO uses the correct flight ID
        dto.setActive(true);
        FlightDto updated = flightService.updateFlightInfo(dto);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
