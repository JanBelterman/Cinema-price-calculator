package domain;

public class MovieTicket {
    private MovieScreening movieScreening;
    private boolean isPremiumTicket;

    private int seatRow;
    private int seatNr;

    public MovieTicket(
            MovieScreening movieScreening,
            boolean isPremiumTicket,
            int seatRow,
            int seatNr) {
        this.movieScreening = movieScreening;
        this.isPremiumTicket = isPremiumTicket;
        this.seatRow = seatRow;
        this.seatNr = seatNr;
    }

    public boolean isPremiumTicket() {
        return isPremiumTicket;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public int getSeatNr() {
        return seatNr;
    }

    public double getPrice() {
        return this.movieScreening.getPricePerSeat();
    }

    public MovieScreening getMovieScreening() {
        return this.movieScreening;
    }

    @Override
    public String toString() {
        return movieScreening.toString() + " - row " + seatRow + ", seat " + seatNr +
                (isPremiumTicket ? " (Premium)" : "");
    }
}
