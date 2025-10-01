package org.example.gruppe1xpkino.config;

import org.example.gruppe1xpkino.model.AgeLimit;
import org.example.gruppe1xpkino.model.Movie;
import org.example.gruppe1xpkino.model.MovieGenre;
import org.example.gruppe1xpkino.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MovieData implements CommandLineRunner {

    @Autowired
    MovieRepository movieRepository;

    @Override
    public void run(String... args) throws Exception {

        List<Movie> movies = List.of(
                new Movie( "one_battle_after_another.jpg", "One Battle after Antoher ", "Leonardo DiCaprio, Sean Penn", true, MovieGenre.DRAMA, AgeLimit.AGE_11, List.of()),
                new Movie("star_wars_ep4.jpg", "Star Wars: Episode IV – A New Hope", "Mark Hamill, Harrison Ford, Carrie Fisher", true, MovieGenre.SCIENCE_FICTION, AgeLimit.AGE_11, List.of()),
                new Movie( "godfather.jpg", "The Godfather", "Marlon Brando, Al Pacino", true, MovieGenre.DRAMA, AgeLimit.AGE_15, List.of()),
                new Movie("den_sidste_viking.jpg", "Den Sidste Viking", "Mads Mikkelsen, Nikolaj Lie Kaas", true, MovieGenre.COMEDY, AgeLimit.AGE_15, List.of()),
                new Movie("john_wick_4.jpg", "John Wick 4", "Keanu Reeves, Donnie Yen, Bill Skarsgård", true, MovieGenre.ACTION, AgeLimit.AGE_15, List.of()),
                new Movie("lord_of_the_rings.jpg", "Lord of the Rings: Fellowship of the Ring", "Elijah Wood, Ian McKellen, Viggo Mortensen", true, MovieGenre.FANTASY, AgeLimit.AGE_11, List.of()),
                new Movie("parasite.jpg", "Parasite", "Song Kang-ho, Lee Sun-kyun", true, MovieGenre.DRAMA, AgeLimit.AGE_15, List.of()),
                new Movie( "interstellar.jpg", "Interstellar", "Matthew McConaughey, Anne Hathaway", true, MovieGenre.SCIENCE_FICTION, AgeLimit.AGE_11, List.of())
        );

        movies.forEach(movieRepository::save);
    }
}
