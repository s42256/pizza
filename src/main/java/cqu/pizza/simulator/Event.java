package cqu.pizza.simulator;

import cqu.pizza.lifecycle.Model;

/**
 * Base class for all simulation events.
 * Stores the scheduled time and defines the processing contract.
 *
 * @author sisak
 */
public abstract class Event {

    private final int time;

    /**
     * Creates an event scheduled for a specific time.
     *
     * @param time event time
     */
    public Event(int time) {
        this.time = time;
    }

    /**
     * Returns the event time.
     *
     * @return scheduled time for this event
     */
    public int getTime() {
        return time;
    }

    /**
     * Executes the event and optionally schedules successors through {@code s}.
     *
     * @param m model reference
     * @param s scheduler to allow new events to be added
     */
    public abstract void process(Model m, ISchedule s);
}
