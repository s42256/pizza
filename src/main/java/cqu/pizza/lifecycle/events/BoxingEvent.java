package cqu.pizza.lifecycle.events;

import cqu.pizza.lifecycle.Model;
import cqu.pizza.lifecycle.Order;
import cqu.pizza.simulator.Event;
import cqu.pizza.simulator.ISchedule;

/**
 * Event for the boxing step after cooking. Removes the cooked order from the oven,
 * performs boxing, schedules finalisation, and (if the queue is non-empty) starts
 * cooking the next order immediately.
 *
 * @author sisak
 */
public class BoxingEvent extends Event {

    private final Order order;

    /**
     * Creates a boxing event for the given order at the specified time.
     *
     * @param time  scheduled time for boxing to begin
     * @param order order being boxed
     */
    public BoxingEvent(int time, Order order) {
        super(time);
        this.order = order;
    }

    /**
     * Executes the boxing step, schedules the finalisation for this order, and
     * if another order is waiting at the head of the queue, schedules its cooking
     * to start at the current time.
     *
     * @param m model providing step behaviour
     * @param s scheduler used to queue successor events
     */
    @Override
    public void process(Model m, ISchedule s) {
        // Remove the cooked order from the oven.
        m.remove(getTime());

        // Box the cooked order.
        int done = m.box(getTime(), order);
        order.stepCompleted();
        s.schedule(new FinalisationEvent(done, order));

        // If another order is waiting, start its cooking now.
        Order next = m.peek();
        if (next != null) {
            s.schedule(new CookingEvent(getTime(), next));
        }
    }
}
