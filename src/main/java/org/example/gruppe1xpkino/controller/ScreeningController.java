package org.example.gruppe1xpkino.controller;

import org.example.gruppe1xpkino.model.Theater;
import org.example.gruppe1xpkino.repository.TheaterRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class ScreeningController {

    private final TheaterRepository theaterRepository;

    public ScreeningController(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    @PostMapping("/selection")
    public Theater saveSelection(@RequestBody Map<String, String> payload) {
        String theaterName = payload.get("theater");
        Theater theater = theaterRepository.findByTheaterName(theaterName);
        if (theater == null) {
            throw new RuntimeException("Theater not found: " + theaterName);
        }
        return theater;
    }

    @GetMapping("/selection")
    public List<Theater> getSelections() {
        return theaterRepository.findAll();
    }
}
