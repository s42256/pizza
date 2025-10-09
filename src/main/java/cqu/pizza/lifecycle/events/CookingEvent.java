package cqu.pizza.lifecycle.events;

import cqu.pizza.lifecycle.Model;
import cqu.pizza.lifecycle.Order;
import cqu.pizza.simulator.Event;
import cqu.pizza.simulator.ISchedule;

/**
 * Event for the cooking step. In the single-oven model this runs when the
 * order reaches the oven. On completion, a {@link BoxingEvent} is scheduled.
 *
 * @author sisak
 */
public class CookingEvent extends Event {

    private final Order order;

    /**
     * Creates a cooking event for the given order
     *
     * @param time  scheduled start time of cooking
     * @param order order to be cooked
     */
    public CookingEvent(int time, Order order) {
        super(time);
        this.order = order;
    }

    /**
     * Executes the cooking step and schedules the boxing step for the completion time.
     *
     * @param m model providing step behaviour
     * @param s scheduler used to queue successor events
     */
    @Override
    public void process(Model m, ISchedule s) {
        int done = m.cook(getTime(), order);
        order.stepCompleted();
        s.schedule(new BoxingEvent(done, order));
    }
}
