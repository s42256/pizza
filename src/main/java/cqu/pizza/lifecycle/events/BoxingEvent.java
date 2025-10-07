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

/** Boxing step. Also rotates the oven queue to the next order. */
public class BoxingEvent extends Event {

    private final Order order;

    public BoxingEvent(int time, Order order) {
        super(time);
        this.order = order;
    }

    @Override
    public void process(Model m, ISchedule s) {
        // The cooked order leaves the oven.
        m.remove(getTime());

        // Start boxing this cooked order.
        int done = m.box(getTime(), order);
        order.stepCompleted();                // step 5: boxing
        s.schedule(new FinalisationEvent(done, order));

        // If someone is waiting, start their cooking now at current time.
        Order next = m.peek();
        if (next != null) {
            s.schedule(new CookingEvent(getTime(), next));
        }
    }
}
