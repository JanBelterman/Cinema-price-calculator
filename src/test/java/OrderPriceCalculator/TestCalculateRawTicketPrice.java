package OrderPriceCalculator;

import domain.Movie;
import domain.MovieScreening;
import domain.MovieTicket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.OrderPriceCalculator;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestCalculateRawTicketPrice {

    private OrderPriceCalculator calculator;

    private Movie movie = new Movie("Avengers");
    private MovieScreening screening;

    @BeforeEach
    void setUp() {
        calculator = new OrderPriceCalculator();
        screening = new MovieScreening(movie, LocalDateTime.parse("2020-01-01T12:00:00"), 10);
    }

    @Test
    void testNormalPrice() {
        MovieTicket movieTicket = new MovieTicket(screening, false, 1, 1);
        assertEquals(10.0, calculator.calculateRawTicketPrice(movieTicket, false));
    }

    @Test
    void testPremiumNormalPrice() {
        MovieTicket movieTicket = new MovieTicket(screening, true, 1, 1);
        assertEquals(13.0, calculator.calculateRawTicketPrice(movieTicket, false));
    }

    @Test
    void testPremiumStudentPrice() {
        MovieTicket movieTicket = new MovieTicket(screening, true, 1, 1);
        assertEquals(12.0, calculator.calculateRawTicketPrice(movieTicket, true));
    }

}
