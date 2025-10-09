package cqu.pizza.simulator;

/**
 * Scheduling capability provided to events; implemented by the simulator.
 *
 * @author sisak
 */
public interface ISchedule {

    /**
     * Adds an event to the simulator's event queue.
     *
     * @param e event to schedule
     */
    void schedule(Event e);
}
