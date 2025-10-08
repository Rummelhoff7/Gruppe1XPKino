package org.example.gruppe1xpkino.config;

import org.example.gruppe1xpkino.model.*;
import org.example.gruppe1xpkino.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DummyDataConfig {

    @Bean
    CommandLineRunner loadData(
            CustomerRepository customerRepo,
            MovieRepository movieRepo,
            ReservationRepository reservationRepo,
            SeatRepository seatRepo,
            ShowRepository showRepo,
            SweetsRepository sweetRepo,
            TheaterRepository theaterRepo,
            TicketRepository ticketRepo,
            EmployeeRepository employeeRepo

    ) {
        return args -> {

            // 🎟️ Create Customer
            Customer customer = new Customer();
            customer.setName("John Doe");
            customer.setAge(30);
            customer.setPhoneNumber("123456789");
            customerRepo.save(customer);
            Customer customer2 = new Customer();
            customer2.setName("Barn");
            customer2.setAge(10);
            customer2.setPhoneNumber("123456789");
            customerRepo.save(customer2);

            // 🎬 Create Movie
            Movie movie = new Movie();
            movie.setActors("Tom Hanks, Emma Watson");
            movie.setAgeLimit(AgeLimit.AGE_11);
            movie.setFeatureFilm(true);
            movie.setGenre(MovieGenre.ACTION);
            movie.setImg("interstellar.jpg");
            movie.setMovieTitle("The Great Adventure");
            movieRepo.save(movie);

            Movie movie2 = new Movie();
            movie2.setActors("sneglen, Sigurt");
            movie2.setAgeLimit(AgeLimit.AGE_7);
            movie2.setFeatureFilm(false);
            movie2.setGenre(MovieGenre.COMEDY);
            movie2.setImg("lord_of_the_rings.jpg");
            movie2.setMovieTitle("For older children");
            movieRepo.save(movie2);

            Movie movie3 = new Movie();
            movie3.setActors("sneglen, Sigurt");
            movie3.setAgeLimit(AgeLimit.ALL);
            movie3.setFeatureFilm(false);
            movie3.setGenre(MovieGenre.COMEDY);
            movie3.setImg("godfather.jpg");
            movie3.setMovieTitle("For children");
            movieRepo.save(movie3);

            // 🏟️ Create Theater
            Theater smalltheater = new Theater();
            smalltheater.setTheaterName("small");
            smalltheater.setNumberOfRows(20);
            smalltheater.setNumberOfSeats(12);
            theaterRepo.save(smalltheater);
            Theater bigttheater = new Theater();
            bigttheater.setTheaterName("big");
            bigttheater.setNumberOfRows(25);
            bigttheater.setNumberOfSeats(16);
            theaterRepo.save(bigttheater);


            // 🎭 Create Show
            Show show = new Show();
            show.setShowingTime(LocalDateTime.now().plusDays(1).withHour(15).withMinute(0).withSecond(0).withNano(0));
            show.setMovie(movie);
            show.setTheater(bigttheater);
            showRepo.save(show);
            Show show2 = new Show();
            show2.setShowingTime(LocalDateTime.now().plusDays(1).withHour(18).withMinute(0).withSecond(0).withNano(0));
            show2.setMovie(movie2);
            show2.setTheater(bigttheater);
            showRepo.save(show2);
            Show show3 = new Show();
            show3.setShowingTime(LocalDateTime.now().plusDays(1).withHour(21).withMinute(0).withSecond(0).withNano(0));
            show3.setMovie(movie3);
            show3.setTheater(bigttheater);
            showRepo.save(show3);

            Show show4 = new Show();
            show4.setShowingTime(LocalDateTime.now().plusDays(1).withHour(15).withMinute(0).withSecond(0).withNano(0));
            show4.setMovie(movie3);
            show4.setTheater(smalltheater);
            showRepo.save(show4);
            Show show5 = new Show();
            show5.setShowingTime(LocalDateTime.now().plusDays(1).withHour(18).withMinute(0).withSecond(0).withNano(0));
            show5.setMovie(movie2);
            show5.setTheater(smalltheater);
            showRepo.save(show5);
            Show show6 = new Show();
            show6.setShowingTime(LocalDateTime.now().plusDays(1).withHour(21).withMinute(0).withSecond(0).withNano(0));
            show6.setMovie(movie);
            show6.setTheater(smalltheater);
            showRepo.save(show6);

            // 💺 Create Seat
            Seat seat = new Seat();
            seat.setSeatNumber(5);
            seat.setSeatRow(2);
            seat.setTheater(smalltheater);
            seatRepo.save(seat);

            Seat seat2 = new Seat();
            seat2.setSeatNumber(5);
            seat2.setSeatRow(2);
            seat2.setTheater(bigttheater);
            seatRepo.save(seat2);

            // 🍭 Create Sweet
            Sweets sweet = new Sweets();
            sweet.setName("Popcorn");
            sweet.setPrice(5.99);
            sweetRepo.save(sweet);

            // 📅 Create Reservation
            Reservation reservation = new Reservation();
            reservation.setCustomer(customer);
            reservation.setShow(show);
            reservationRepo.save(reservation);

            Reservation reservation2 = new Reservation();
            reservation2.setCustomer(customer2);
            reservation2.setShow(show2);
            reservationRepo.save(reservation2);

            // 🎟️ Create Ticket
            Ticket ticket = new Ticket();
            ticket.setCustomer(customer);
            ticket.setPrice(12.50);
            ticket.setTheater(smalltheater);
            ticket.setReservation(reservation);
            ticket.setSeat(seat);
            ticketRepo.save(ticket);


            //  Create Sweets
            Sweets sweets = new Sweets();
            sweets.setName("ost");
            sweets.setPrice(20.00);
            sweetRepo.save(sweets);

            Employee employee = new Employee();
            employee.setName("123");
            employee.setUsername("123");
            employee.setPassword("123");
            employee.setRole(EmployeeRole.KINO_ADMINISTRATOR);
            employeeRepo.save(employee);

            Employee employee1 = new Employee();
            employee1.setName("1234");
            employee1.setUsername("1234");
            employee1.setPassword("1234");
            employee1.setRole(EmployeeRole.SALES_MANAGER);
            employeeRepo.save(employee1);

            Employee employee2 = new Employee();
            employee1.setName("12345");
            employee1.setUsername("12345");
            employee1.setPassword("12345");
            employee1.setRole(EmployeeRole.INSPECTOR);
            employeeRepo.save(employee1);


            System.out.println("✅ Dummy data loaded.");
        };
    }
}
