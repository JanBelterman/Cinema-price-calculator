package domain;

import export.BaseExporter;
import export.JSONExporter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.*;

import java.util.ArrayList;

public class Order {
    private int orderNr;

    private ArrayList<MovieTicket> tickets;
    private boolean isStudentOrder;

    private IOrderPriceCalculator priceCalculator;
    private BaseExporter exporter;

    public Order() {
        this.exporter = new JSONExporter();
        this.priceCalculator = new OrderPriceCalculator();
    }

    public Order(int orderNr, boolean isStudentOrder) {
        this.orderNr = orderNr;
        this.isStudentOrder = isStudentOrder;
        tickets = new ArrayList<>();
        this.priceCalculator = new OrderPriceCalculator();
    }

    public int getOrderNr() {
        return orderNr;
    }

    public ArrayList<MovieTicket> getTickets() {
        return tickets;
    }

    public boolean isStudentOrder() {
        return isStudentOrder;
    }

    public void setStudentOrder(boolean studentOrder) {
        isStudentOrder = studentOrder;
    }

    public void addSeatReservation(MovieTicket ticket) {
        tickets.add(ticket);
    }

    public double calculatePrice() {
        return priceCalculator.calculateOrderPrice(this);
    }

    public void export() {
        JSONObject orderJSON = new JSONObject();
        orderJSON.put("OrderNr", orderNr);
        orderJSON.put("IsStudentOrder", isStudentOrder);
        JSONArray ticketsJSON = new JSONArray();
        for (MovieTicket ticket : tickets) {
            JSONObject ticketJSON = new JSONObject();
            ticketJSON.put("isPremiumTicket", ticket.isPremiumTicket());
            ticketJSON.put("rowNr", ticket.getSeatRow());
            ticketJSON.put("seatNr", ticket.getSeatNr());
            ticketsJSON.add(ticketJSON);
        }
        orderJSON.put("Tickets", ticketsJSON);
        exporter.export("Order_" + orderNr, orderJSON.toJSONString());
    }

}
