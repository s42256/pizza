/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.pizza.lifecycle.events;

import cqu.pizza.lifecycle.Model;
import cqu.pizza.lifecycle.data.Request;
import cqu.pizza.simulator.Event;
import cqu.pizza.simulator.ISchedule;

/**
 *
 * @author sisak
 */
/**
 * Event representing the arrival of a customer request.
 */
public class OrderEvent extends Event {

    private final Request request;

    /**
     * Creates an order-arrival event.
     *
     * @param request incoming request
     */
    public OrderEvent(Request request) {
        super(request.orderTime());
        this.request = request;
    }

    /**
     * Converts the request to an order and schedules the next arrival.
     *
     * @param m model reference
     * @param s scheduler reference
     */
    @Override
    public void process(Model m, ISchedule s) {
        m.order(request);
        Request next = m.nextRequest();
        s.schedule(new OrderEvent(next));
    }
}
