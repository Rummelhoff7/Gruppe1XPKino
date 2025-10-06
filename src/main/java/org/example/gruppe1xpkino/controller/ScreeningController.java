package org.example.gruppe1xpkino.controller;

import org.example.gruppe1xpkino.model.Movie;
import org.example.gruppe1xpkino.model.Show;
import org.example.gruppe1xpkino.model.Theater;
import org.example.gruppe1xpkino.repository.MovieRepository;
import org.example.gruppe1xpkino.repository.ShowRepository;
import org.example.gruppe1xpkino.repository.TheaterRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ScreeningController {

    private final TheaterRepository theaterRepository;
    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;

    public ScreeningController(TheaterRepository theaterRepository,
                               MovieRepository movieRepository,
                               ShowRepository showRepository) {
        this.theaterRepository = theaterRepository;
        this.movieRepository = movieRepository;
        this.showRepository = showRepository;
    }

    @GetMapping("/screening_movies")
    public java.util.List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    @PostMapping("/saveShow")
    public Show saveShow(@RequestBody Map<String, Object> payload) {
        Integer theaterId = (Integer) payload.get("theaterId");
        Integer movieId = (Integer) payload.get("movieId");
        String timeStr = (String) payload.get("time");
        String startDateStr = (String) payload.get("startDate");
        String endDateStr = (String) payload.get("endDate");

        LocalTime showingTime = LocalTime.parse(timeStr);
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new RuntimeException("Theater not found"));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Show show = new Show();
        show.setTheater(theater);
        show.setMovie(movie);
        show.setShowingStartDate(startDate);
        show.setShowingEndDate(endDate);
        show.setShowingTime(showingTime);

        return showRepository.save(show);
    }
}
