package cqu.pizza.lifecycle.events;

import cqu.pizza.lifecycle.Model;
import cqu.pizza.lifecycle.Order;
import cqu.pizza.simulator.Event;
import cqu.pizza.simulator.ISchedule;
/**
 *
 * @author sisak
 */
/** Box the pizza after cooking. */
public class BoxingEvent extends Event {

    private final Order order;
    /**
     * Creates a boxing event for the given order.
     *
     * @param time  scheduled time
     * @param order order to be boxed
     */
    public BoxingEvent(int time, Order order) {
        super(time);
        this.order = order;
    }
    /**
     * Runs the boxing step and schedules {@link FinalisationEvent}.
     *
     * @param m model providing step behaviour
     * @param s scheduler used to queue the next event
     */
    @Override
    public void process(Model m, ISchedule s) {
        int done = m.box(getTime(), order);
        s.schedule(new FinalisationEvent(done, order));
        order.stepCompleted();
    }
}
