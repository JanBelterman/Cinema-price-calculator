package utils;

import domain.Order;

public interface IOrderPriceCalculator {

    double calculateOrderPrice(Order order);

}
