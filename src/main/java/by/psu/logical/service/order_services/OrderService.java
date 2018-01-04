package by.psu.logical.service.order_services;

import by.psu.logical.model.order.Order;
import by.psu.logical.service.AbstractService;

public class OrderService extends AbstractService<Order> {
    public OrderService() {
        super(Order.class);
    }
}
