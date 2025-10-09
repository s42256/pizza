package cqu.pizza.lifecycle.events;

import cqu.pizza.lifecycle.Model;
import cqu.pizza.lifecycle.Order;
import cqu.pizza.simulator.Event;
import cqu.pizza.simulator.ISchedule;

/**
 * Event for joining the single-oven queue. If the oven is free, this starts
 * cooking immediately; otherwise the order waits its turn.
 *
 * @author sisak
 */
public class QueuingEvent extends Event {

    private final Order order;

    /**
     * Creates a queuing event for the given order at the specified time.
     *
     * @param time  time at which the order is ready to queue
     * @param order order that is joining the queue
     */
    public QueuingEvent(int time, Order order) {
        super(time);
        this.order = order;
    }

    /**
     * Adds the order to the queue and, if it can start cooking immediately,
     * schedules a {@link CookingEvent} at the current time.
     *
     * @param m model providing queue logic
     * @param s scheduler used to queue successor events
     */
    @Override
    public void process(Model m, ISchedule s) {
        int start = m.queue(getTime(), order);

        // Count the queuing step (Step 3 in Phase 6).
        order.stepCompleted();

        if (start != 0) {
            s.schedule(new CookingEvent(start, order));
        }
    }
}
