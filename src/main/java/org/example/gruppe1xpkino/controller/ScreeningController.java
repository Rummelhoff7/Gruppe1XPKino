package org.example.gruppe1xpkino.controller;

import org.example.gruppe1xpkino.model.Movie;
import org.example.gruppe1xpkino.model.Show;
import org.example.gruppe1xpkino.model.ShowDTO;
import org.example.gruppe1xpkino.model.Theater;
import org.example.gruppe1xpkino.repository.MovieRepository;
import org.example.gruppe1xpkino.repository.ShowRepository;
import org.example.gruppe1xpkino.repository.TheaterRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    @PostMapping("/saveShow")
    public Show saveShow(@RequestBody Map<String, Object> payload) {
        Integer theaterId = (Integer) payload.get("theaterId");
        Integer movieId = (Integer) payload.get("movieId");
        String showingTimeStr = (String) payload.get("showingTime");
        String startDateStr = (String) payload.get("startDate");
        String endDateStr = (String) payload.get("endDate");

        if (showingTimeStr == null || showingTimeStr.isEmpty()) {
            throw new RuntimeException("showingTime is missing in request");
        }

        LocalDateTime showingTime = LocalDateTime.parse(showingTimeStr);
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

    @GetMapping("/current_shows")
    public List<ShowDTO> getCurrentShows() {
        return showRepository.findAll()
                .stream()
                .map(show -> new ShowDTO(
                        show.getId(),
                        show.getMovie().getMovieTitle(),
                        show.getShowingTime(),
                        show.getTheater().getTheaterName(),
                        show.getMovie().getAgeLimit()
                ))
                .toList();
    }

    // -------------------- New: Get a single show by ID --------------------
    @GetMapping("/show/{id}")
    public ShowDTO getShowById(@PathVariable int id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        return new ShowDTO(
                show.getId(),
                show.getMovie().getMovieTitle(),
                show.getShowingTime(),
                show.getTheater().getTheaterName(),
                show.getMovie().getAgeLimit()
        );
    }

    // -------------------- New: Update theater, showing time, start/end dates --------------------
    @PutMapping("/update_show")
    public Show updateShow(@RequestBody Map<String, String> payload) {
        int showId = Integer.parseInt(payload.get("showId"));
        String theaterName = payload.get("theaterName");
        String showingTimeStr = payload.get("showingTime");
        String startDateStr = payload.get("startDate");
        String endDateStr = payload.get("endDate");

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        // Update theater
        Theater theater = theaterRepository.findAll().stream()
                .filter(t -> t.getTheaterName().equalsIgnoreCase(theaterName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Theater not found"));
        show.setTheater(theater);

        // Update dates and time
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        LocalTime time = LocalTime.parse(showingTimeStr);
        show.setShowingStartDate(startDate);
        show.setShowingEndDate(endDate);
        show.setShowingTime(LocalDateTime.of(startDate, time));

        return showRepository.save(show);
    }
}
