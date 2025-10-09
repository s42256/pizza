package cqu.pizza.lifecycle.events;

import cqu.pizza.lifecycle.Model;
import cqu.pizza.lifecycle.Order;
import cqu.pizza.lifecycle.data.Request;
import cqu.pizza.simulator.Event;
import cqu.pizza.simulator.ISchedule;

/**
 * Event representing the arrival of a customer request.
 * Creates the order (or refuses it), schedules the next arrival,
 * and starts processing for accepted orders.
 *
 * @author sisak
 */
public class OrderEvent extends Event {

    private final Request request;

    /**
     * Creates an order-arrival event from a request.
     *
     * @param request incoming request (its time is the event time)
     */
    public OrderEvent(Request request) {
        super(request.orderTime());
        this.request = request;
    }

     /**
     * Creates or refuses the order, schedules the next arrival,
     * and if accepted, schedules preparation at the request time.
     *
     * @param m model used to create/refuse orders
     * @param s scheduler for follow-up events
     */
    @Override
    public void process(Model m, ISchedule s) {
        // create the order or refuse
        Order o = m.order(request);

        // always schedule the next arrival
        Request next = m.nextRequest();
        s.schedule(new OrderEvent(next));

        // if refused, stop here
        if (o == null) return;

        // start preparation at the request time
        s.schedule(new PreparationEvent(getTime(), o));
    }
}
