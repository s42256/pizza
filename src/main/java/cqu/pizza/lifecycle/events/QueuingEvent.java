/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.pizza.lifecycle.events;

import cqu.pizza.lifecycle.Model;
import cqu.pizza.lifecycle.Order;
import cqu.pizza.simulator.Event;
import cqu.pizza.simulator.ISchedule;
/**
 *
 * @author sisak
 */

public class QueuingEvent extends Event {

    private final Order order;

    public QueuingEvent(int time, Order order) {
        super(time);
        this.order = order;
    }

    @Override
    public void process(Model m, ISchedule s) {
        // Add to queue; if start != 0 we begin cooking right now.
        int start = m.queue(getTime(), order);

        // IMPORTANT: count the queuing step (Step 3 in Phase 6)
        order.stepCompleted();

        if (start != 0) {
            s.schedule(new CookingEvent(start, order));
        }
    }
}
