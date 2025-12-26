//package com.ai.skysmart.controller;
//
//import com.ai.skysmart.dto.CreateFlightRequest;
//import com.ai.skysmart.dto.FlightDto;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.ResponseEntity;
//
//import java.time.LocalDateTime;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class FlightControllerIntegrationTest {
//    @LocalServerPort
//    private int port;
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//
//    @Test
//    public void createAndGetFlight() {
//        String url = "http://localhost:" + port + "/api/flights";
//        CreateFlightRequest req = CreateFlightRequest.builder()
//                .flightNumber("AI-101")
//                .carrier("Air India")
//                .origin("DEL")
//                .destination("BOM")
//                .departureTime(LocalDateTime.now().plusDays(1))
//                .arrivalTime(LocalDateTime.now().plusDays(1).plusHours(2))
//                .totalSeats(180)
//                .availableSeats(180)
//                .price(4999.0)
//                .description("Test flight")
//                .build();
//
//
//        ResponseEntity<FlightDto> created = restTemplate.postForEntity(url, req, FlightDto.class);
//        assertThat(created.getStatusCode().is2xxSuccessful()).isTrue();
//        FlightDto body = created.getBody();
//        assertThat(body).isNotNull();
//        assertThat(body.getFlightNumber()).isEqualTo("AI-101");
//
//
//        Long id = body.getId();
//        FlightDto fetched = restTemplate.getForObject(url + "/" + id, FlightDto.class);
//        assertThat(fetched).isNotNull();
//        assertThat(fetched.getFlightNumber()).isEqualTo("AI-101");
//    }
//}
