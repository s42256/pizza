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
/** Cooking step (one oven). */
public class CookingEvent extends Event {

    private final Order order;

    public CookingEvent(int time, Order order) {
        super(time);
        this.order = order;
    }

    @Override
    public void process(Model m, ISchedule s) {
        int done = m.cook(getTime(), order);
        order.stepCompleted();                // step 4: cooking
        s.schedule(new BoxingEvent(done, order));
    }
}
