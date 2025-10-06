/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.pizza.lifecycle.events;

import cqu.pizza.lifecycle.Model;
import cqu.pizza.lifecycle.Order;
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

    public OrderEvent(Request request) {
        super(request.orderTime());
        this.request = request;
    }

    @Override
    public void process(Model m, ISchedule s) {
        // create the order or refuse
        Order o = m.order(request);

        // always schedule the next arrival
        Request next = m.nextRequest();
        s.schedule(new OrderEvent(next));

        // if refused, stop here
        if (o == null) return;

        // infinite capacity: start preparation immediately at request time
        s.schedule(new PreparationEvent(getTime(), o));
    }
}
