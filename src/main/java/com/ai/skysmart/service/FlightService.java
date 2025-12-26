package com.ai.skysmart.service;

import com.ai.skysmart.dto.FlightDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public interface FlightService {

    public FlightDto createFlight(FlightDto flight);
    public List<FlightDto> getAllFlight();
    public List<FlightDto>  getFlightOnBasisOriginAndDestination(String origin, String destination);
    //public List<FlightDto>  getFlightOnBasisOriginAndDestinationAndJourneyDate(String origin, String destination, LocalDate journeyDate);
    public List<FlightDto> getFlightByFlightNum(String num);
    public Page<FlightDto> getFlightOnBasisOriginAndDestinationAndJourneyDate(String origin, String destination, LocalDate journeyDate, int page, int size);

    public String cancelFlight(Long flightId);
    public FlightDto updateFlightInfo(FlightDto dto);

    Page<FlightDto> getFlightOnOriginDestJourneyDateSort(
            String origin,
            String destination,
            LocalDate journeyDate,
            int page,
            int size,
            String sortBy,
            String sortDir);
}

