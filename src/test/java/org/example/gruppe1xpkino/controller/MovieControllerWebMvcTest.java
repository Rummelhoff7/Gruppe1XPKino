package org.example.gruppe1xpkino.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.gruppe1xpkino.model.Movie;
import org.example.gruppe1xpkino.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;



import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/* se MovieControllerUnitTest først
Denne her test tjekker kun web layer og request mapping.. og json

 */


@ExtendWith(MockitoExtension.class)
class MovieControllerWebMvcTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieController movieController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    void getAllMovies() throws Exception {
        when(movieRepository.findAll()).thenReturn(List.of(new Movie(), new Movie()));

        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void addMovie_andReturnMovie() throws Exception {
        Movie movie = new Movie();
        movie.setMovieTitle("MovieTest");

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        mockMvc.perform(post("/api/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieTitle").value("MovieTest"));

    }
}