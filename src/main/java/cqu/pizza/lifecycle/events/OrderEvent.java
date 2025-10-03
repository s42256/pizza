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
public class OrderEvent extends Event {

    private final Request request;

    public OrderEvent(Request request) {
        super(request.orderTime());
        this.request = request;
    }

    @Override
    public void process(Model m, ISchedule s) {
        // create/action the current order (may be refused)
        m.order(request);

        // schedule the next request arrival
        Request next = m.nextRequest();
        s.schedule(new OrderEvent(next));
    }
}
