package OrderPriceCalculator;

import domain.Movie;
import domain.MovieScreening;
import domain.MovieTicket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.OrderPriceCalculator;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class TestWeekdayFunction {

    private OrderPriceCalculator calculator;

    private Movie movie = new Movie("The Avengers");
    private MovieScreening screening;

    @BeforeEach
    void setUp() {
        calculator = new OrderPriceCalculator();
        screening = new MovieScreening(
                movie,
                LocalDateTime.parse("2020-01-01T12:00:00"),
                10
        );
    }

    @ParameterizedTest()
    @MethodSource("inputs")
    void isWeekDayTicket(String dateString, boolean result) {
        screening.setDateAndTime(LocalDateTime.parse(dateString + "T12:00:00"));
        MovieTicket ticket = new MovieTicket(screening, false, 1, 1);
        assertEquals(result, calculator.isWeekDayTicket(ticket));
    }

    static Stream<Arguments> inputs() {
        return Stream.of(
                arguments("2020-01-01", true),
                arguments("2020-01-03", false),
                arguments("2020-01-05", false),
                arguments("2020-01-06", true)
        );
    }

}
