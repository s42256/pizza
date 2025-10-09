package cqu.pizza.lifecycle.events;

import cqu.pizza.lifecycle.Model;
import cqu.pizza.lifecycle.Order;
import cqu.pizza.simulator.Event;
import cqu.pizza.simulator.ISchedule;

/**
 * Event for the preparation step. When preparation finishes, the
 * order proceeds to queuing for the oven (single-oven model).
 *
 * @author sisak
 */
public class PreparationEvent extends Event {

    private final Order order;

    /**
     * Creates a preparation event for the given order.
     *
     * @param time  scheduled time
     * @param order order to be prepared
     */
    public PreparationEvent(int time, Order order) {
        super(time);
        this.order = order;
    }

    /**
     * Runs the preparation step and schedules {@link CookingEvent}.
     *
     * @param m model providing step behaviour
     * @param s scheduler used to queue the next event
     */
    @Override
    public void process(Model m, ISchedule s) {
        int done = m.prepare(getTime(), order);
        order.stepCompleted();
        s.schedule(new QueuingEvent(done, order));
    }
}
