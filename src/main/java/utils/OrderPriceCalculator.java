package utils;

import domain.MovieTicket;
import domain.Order;

import java.util.ArrayList;

public class OrderPriceCalculator implements IOrderPriceCalculator {

    public double calculateOrderPrice(Order order) {
        double rawPrice = calculateRawOrderPrice(order);
        double discount = calculateOrderDiscount(order);
        return rawPrice - discount;
    }

    // Helper methods

    public double calculateRawOrderPrice(Order order) {
        double price = 0.0;

        for (MovieTicket ticket : order.getTickets()) {
            price += calculateRawTicketPrice(ticket, order.isStudentOrder());
        }

        return price;
    }

    public double calculateOrderDiscount(Order order) {
        double freeTicketDiscount = calculateSecondFreeDiscount(order);
        double groupDiscount = calculateGroupDiscount(order);
        return freeTicketDiscount + groupDiscount;
    }

    public double calculateSecondFreeDiscount(Order order) {
        int freeTicketCount = countFreeTickets(order);
        ArrayList<MovieTicket> ticketsAvailableForSecondFreeDiscount = getTicketsAvailableForSecondFreeDiscount(order);
        return calculatePriceOfCheapestTickets(ticketsAvailableForSecondFreeDiscount, freeTicketCount, order.isStudentOrder());
    }

    public double calculateGroupDiscount(Order order) {
        if (order.isStudentOrder()) {
            return 0;
        } else {
            ArrayList<MovieTicket> groupDiscountAvailableTickets = getTicketsAvailableForGroupDiscount(order);
            if (groupDiscountAvailableTickets.size() < 6) {
                return 0;
            } else {
                return calculatePercentageDiscount(groupDiscountAvailableTickets, order.isStudentOrder());
            }
        }
    }

    public double calculatePercentageDiscount(ArrayList<MovieTicket> tickets, boolean isStudentOrder) {
        double price = 0.0;
        for (MovieTicket ticket : tickets) {
            price += calculateRawTicketPrice(ticket, isStudentOrder);
        }
        return price * 0.90;
    }

    public int countFreeTickets(Order order) {
        if (order.isStudentOrder()) {
            return order.getTickets().size() / 2;
        } else {
            int weekdayTicketCount = 0;
            for (MovieTicket ticket : order.getTickets()) {
                if (isWeekDayTicket(ticket)) {
                    weekdayTicketCount += 1;
                }
            }
            return weekdayTicketCount;
        }
    }

    public ArrayList<MovieTicket> getTicketsAvailableForSecondFreeDiscount(Order order) {
        if (order.isStudentOrder()) {
            return order.getTickets();
        } else {
            ArrayList<MovieTicket> ticketsToBeDiscounted = new ArrayList<>();
            for (MovieTicket ticket : order.getTickets()) {
                if (isWeekDayTicket(ticket)) {
                    ticketsToBeDiscounted.add(ticket);
                }
            }
            return ticketsToBeDiscounted;
        }
    }

    public double calculatePriceOfCheapestTickets(ArrayList<MovieTicket> tickets, int freeTicketCount, boolean isStudentOrder) {
        double discount = 0.0;
        for (int i = 1; i < freeTicketCount; i++) {
            MovieTicket cheapestTicket = getCheapestTicket(tickets, isStudentOrder);
            discount += calculateRawTicketPrice(cheapestTicket, isStudentOrder);
            tickets.remove(cheapestTicket);
        }
        return discount;
    }

    public MovieTicket getCheapestTicket(ArrayList<MovieTicket> tickets, boolean isStudentOrder) {
        MovieTicket cheapestTicket = tickets.get(0);
        for (int i = 1; i < tickets.size(); i++) {
            if (calculateRawTicketPrice(tickets.get(i), isStudentOrder) < calculateRawTicketPrice(cheapestTicket, isStudentOrder)) {
                cheapestTicket = tickets.get(i);
            }
        }
        return cheapestTicket;
    }

    public ArrayList<MovieTicket> getTicketsAvailableForGroupDiscount(Order order) {
        ArrayList<MovieTicket> tickets = new ArrayList<>();
        for (MovieTicket ticket : order.getTickets()) {
            if (!isWeekDayTicket(ticket) && !order.isStudentOrder()) {
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    public boolean isWeekDayTicket(MovieTicket ticket) {
        return ticket.getMovieScreening().getDateAndTime().getDayOfWeek().getValue() <= 3;
    }

    public double calculateRawTicketPrice(MovieTicket ticket, boolean isStudentTicket) {
        double price = 0.0;
        if (ticket.isPremiumTicket()) {
            price += ticket.getPrice() + (isStudentTicket ? 2 : 3);
        } else {
            price += ticket.getPrice();
        }
        return price;
    }

}
