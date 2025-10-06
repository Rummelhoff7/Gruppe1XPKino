package org.example.gruppe1xpkino.config;

import org.example.gruppe1xpkino.model.Sweets;
import org.example.gruppe1xpkino.repository.SweetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class SweetsData implements CommandLineRunner {

    @Autowired
    SweetsRepository sweetsRepository;

    @Override
    public void run(String... args) throws Exception {

        List<Sweets> sweets = List.of(
                new Sweets("Small Popcorn", 40.00, "small_popcorn.png"),
                new Sweets("Medium Popcorn", 50.00, "medium_popcorn.png"),
                new Sweets("Large Popcorn", 60.00, "large_popcorn.png"),
                new Sweets("M&Ms", 30.00, "mms.jpg"),
                new Sweets("Small Soda", 30.00, "small_soda.jpg"),
                new Sweets("Medium Soda", 40.00, "medium_soda.jpg"),
                new Sweets("Large Soda", 50.00, "large_soda.jpg")
        );

        sweets.forEach(sweetsRepository::save);
    }
}
