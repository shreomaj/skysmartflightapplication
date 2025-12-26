package com.ai.skysmart.service.impl;

import com.ai.skysmart.dto.FlightDto;
import com.ai.skysmart.entity.Flight;
import com.ai.skysmart.repository.FlightRepository;
import com.ai.skysmart.service.FlightService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ModelMapper modelMapper;

    private double applyDynamicPricing(Flight flight) {

        double price = flight.getPrice();
        int remainingSeats = flight.getAvailableSeats();
        long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), flight.getJourneyDate());

        // 1. Last-minute surge
        if (daysLeft <= 3) {
            price *= 1.50; // +50%
        }

        // 2. Low seats → increase (scarcity pricing)
        if (remainingSeats < 20) {
            price *= 1.25;
        }

        // 3. Early bird discount
        if (daysLeft > 30) {
            price *= 0.90; // 10% off
        }

        return price;
    }

    @Override
    public FlightDto createFlight(FlightDto flightDto) {
        Flight flight = modelMapper.map(flightDto, Flight.class);
        if (flight.getActive() == null) {
            flight.setActive(true);
        }
        Flight savedFlight = flightRepository.save(flight);
        FlightDto dto = modelMapper.map(savedFlight, FlightDto.class);
        //Dynamic pricing applied here
        dto.setPrice(applyDynamicPricing(flight));

        return dto;
    }

    @Override
    public List<FlightDto> getAllFlight() {
        List<Flight> flights = flightRepository.findAll();
        return flights.stream()
                .map(flight -> modelMapper.map(flight, FlightDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightDto> getFlightOnBasisOriginAndDestination(String origin, String destination) {
        List<Flight> flights = flightRepository.findByOriginAndDestination(origin, destination);
        return flights.stream()
                .map(flight -> modelMapper.map(flight, FlightDto.class))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<FlightDto> getFlightOnBasisOriginAndDestinationAndJourneyDate(String origin, String destination, LocalDate journeyDate) {
//        List<Flight> flights = flightRepository.findByOriginAndDestinationAndJourneyDate(origin, destination, journeyDate);
//        return flights.stream()
//                .map(flight -> modelMapper.map(flight, FlightDto.class))
//                .collect(Collectors.toList());
//    }

    @Override
    public List<FlightDto> getFlightByFlightNum(String num) {
        List<Flight> flights = flightRepository.findByFlightNumber(num);
        if (flights.isEmpty()) {
            return Collections.emptyList(); // no flights found
        }
        return flights.stream()
                .map(flight -> modelMapper.map(flight, FlightDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<FlightDto> getFlightOnBasisOriginAndDestinationAndJourneyDate(
            String origin, String destination, LocalDate journeyDate, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Flight> flights = flightRepository
                .findByOriginAndDestinationAndJourneyDate(origin, destination, journeyDate,
                        pageable);

        return flights.map(flight -> modelMapper.map(flight, FlightDto.class));
    }


    @Override
    public String cancelFlight(Long flightId) {
        Optional<Flight> flightOpt = flightRepository.findById(flightId);
        if (flightOpt.isEmpty()) {
            return "Flight not found";
        }
        flightRepository.delete(flightOpt.get());
        return "Flight deleted successfully";
    }

    @Override
    public FlightDto updateFlightInfo(FlightDto dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Flight ID is required for update");
        }

        Optional<Flight> flightOpt = flightRepository.findById(dto.getId());
        if (flightOpt.isEmpty()) {
            throw new RuntimeException("Flight not found");
        }

        Flight existing = flightOpt.get();

        // Update only necessary fields
        existing.setCarrier(dto.getCarrier());
        existing.setOrigin(dto.getOrigin());
        existing.setDestination(dto.getDestination());
        existing.setDepartureTime(dto.getDepartureTime());
        existing.setArrivalTime(dto.getArrivalTime());
        existing.setTotalSeats(dto.getTotalSeats());
        existing.setAvailableSeats(dto.getAvailableSeats());
        existing.setPrice(dto.getPrice());
        existing.setActive(dto.getActive());

        Flight saved = flightRepository.save(existing);
        return modelMapper.map(saved, FlightDto.class);
    }

    @Override
    public Page<FlightDto> getFlightOnOriginDestJourneyDateSort(String origin, String destination,
                                                                LocalDate journeyDate, int page,
                                                                int size, String sortBy,
                                                                String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Flight> flights = flightRepository
                .findByOriginAndDestinationAndJourneyDate(origin, destination, journeyDate,
                        pageable);

        return flights.map(flight -> modelMapper.map(flight, FlightDto.class));

    }
}
