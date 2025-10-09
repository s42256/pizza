package cqu.pizza.simulator;

import cqu.pizza.lifecycle.Model;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the ordered event queue and runs the simulation loop.
 *
 * @author sisak
 */
public class Simulator implements ISchedule {

    private final List<Event> events = new ArrayList<>();
    private final Model model;
    private int clock = 0;

    /**
     * Constructs a simulator for the given model.
     *
     * @param model simulation model
     */
    public Simulator(Model model) {
        this.model = model;
    }

    /**
     * Seeds the event queue with the first arrival and the final report.
     *
     * @param firstEvent  first arrival event (e.g., at time 0)
     * @param reportEvent report event at the stop time
     */
    public void initialize(Event firstEvent, Event reportEvent) {
        schedule(firstEvent);
        schedule(reportEvent);
    }

     /**
     * Runs the simulation until the stop time.
     * (This method mirrors the provided algorithm.)
     *
     * @param stopTime last time to process
     */
    public void run(int stopTime) {
        if ((events == null)|| events.isEmpty() )
            return;
        Event e = events.remove(0);
        clock = e.getTime();
        // events queue will never become empty as after the first event is
        // added, every arrival event will generate a new arrival event
        // (which may be greater than the stop time)
        while (clock <= stopTime) {
            e.process(model, this); // allow event to schedule successors
            e = events.remove(0);
            clock = e.getTime();
        }
    }

    /**
     * Inserts an event into the queue, keeping ascending time order.
     * If times are equal, the new event is inserted before the existing one.
     *
     * @param e event to insert
     */
    @Override
    public void schedule(Event e) {
        int i = 0;
        while (i < events.size() && e.getTime() > events.get(i).getTime()) {
            i++;
        }
        events.add(i, e);
    }
}
