package cqu.pizza.lifecycle.events;

import cqu.pizza.lifecycle.Model;
import cqu.pizza.lifecycle.Order;
import cqu.pizza.simulator.Event;
import cqu.pizza.simulator.ISchedule;
/**
 *
 * @author sisak
 */
/** Cook immediately when preparation is done (infinite ovens). */
public class CookingEvent extends Event {

    private final Order order;
    /**
     * Creates a cooking event for the given order.
     *
     * @param time  scheduled time
     * @param order order to be cooked
     */
    public CookingEvent(int time, Order order) {
        super(time);
        this.order = order;
    }
    /**
     * Runs the cooking step and schedules {@link BoxingEvent}.
     *
     * @param m model providing step behaviour
     * @param s scheduler used to queue the next event
     */
    @Override
    public void process(Model m, ISchedule s) {
        int done = m.cook(getTime(), order);
        s.schedule(new BoxingEvent(done, order));
        order.stepCompleted();
    }
}
