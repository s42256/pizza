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


/** Final housekeeping: mark finished and move to completed list. */
public class FinalisationEvent extends Event {

    private final Order order;

    public FinalisationEvent(int time, Order order) {
        super(time);
        this.order = order;
    }

    @Override
    public void process(Model m, ISchedule s) {
        m.finalise(getTime(), order);
        order.stepCompleted(); 
    }
}
