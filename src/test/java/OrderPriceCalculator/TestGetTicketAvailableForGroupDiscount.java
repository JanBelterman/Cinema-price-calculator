package OrderPriceCalculator;

import domain.Movie;
import domain.MovieScreening;
import domain.MovieTicket;
import domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.OrderPriceCalculator;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestGetTicketAvailableForGroupDiscount {

    private OrderPriceCalculator calculator = new OrderPriceCalculator();

    private Order order;
    private Movie movie = new Movie("The Avengers");
    private MovieScreening screening = new MovieScreening(movie, LocalDateTime.parse("2020-01-01T12:00:00"), 10);

    @BeforeEach
    void setUp() {
        order = new Order(1, false);
    }

    @Test
    void noTickets() {
        ArrayList<MovieTicket> tickets = calculator.getTicketsAvailableForGroupDiscount(order);
        assertEquals(0, tickets.size());
    }

    @Test
    void isWeekDayNormalTicket() {
        order.setStudentOrder(false);
        screening.setDateAndTime(LocalDateTime.parse("2020-01-01T12:00:00"));
        order.addSeatReservation(new MovieTicket(screening, false, 1, 1));
        ArrayList<MovieTicket> tickets = calculator.getTicketsAvailableForGroupDiscount(order);
        assertEquals(0, tickets.size());
    }

    @Test
    void isWeekDayStudentTicket() {
        order.setStudentOrder(true);
        screening.setDateAndTime(LocalDateTime.parse("2020-01-01T12:00:00"));
        order.addSeatReservation(new MovieTicket(screening, false, 1, 1));
        ArrayList<MovieTicket> tickets = calculator.getTicketsAvailableForGroupDiscount(order);
        assertEquals(0, tickets.size());
    }

    @Test
    void isWeekendNormalTicket() {
        order.setStudentOrder(false);
        screening.setDateAndTime(LocalDateTime.parse("2020-01-03T12:00:00"));
        order.addSeatReservation(new MovieTicket(screening, false, 1, 1));
        ArrayList<MovieTicket> tickets = calculator.getTicketsAvailableForGroupDiscount(order);
        assertEquals(1, tickets.size());
    }

    @Test
    void isWeekendStudentTicket() {
        order.setStudentOrder(true);
        screening.setDateAndTime(LocalDateTime.parse("2020-01-03T12:00:00"));
        order.addSeatReservation(new MovieTicket(screening, false, 1, 1));
        ArrayList<MovieTicket> tickets = calculator.getTicketsAvailableForGroupDiscount(order);
        assertEquals(0, tickets.size());
    }

}
