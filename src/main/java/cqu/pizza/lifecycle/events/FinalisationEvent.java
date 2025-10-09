package cqu.pizza.lifecycle.events;

import cqu.pizza.lifecycle.Model;
import cqu.pizza.lifecycle.Order;
import cqu.pizza.simulator.Event;
import cqu.pizza.simulator.ISchedule;

/**
 * Event that finalises an order by setting its finish time and
 * moving it to the completed list.
 *
 * @author sisak
 */
public class FinalisationEvent extends Event {

    private final Order order;

    /**
     * Creates a finalisation event for the given order and time.
     *
     * @param time  scheduled time
     * @param order order to finalise
     */
    public FinalisationEvent(int time, Order order) {
        super(time);
        this.order = order;
    }

    /**
     * Applies the model's finalisation logic and advances the order step.
     *
     * @param m model providing the finalisation behaviour
     * @param s scheduler (unused here; no follow-up event)
     */
    @Override
    public void process(Model m, ISchedule s) {
        m.finalise(getTime(), order);
        order.stepCompleted();
        // no successor
    }
}
