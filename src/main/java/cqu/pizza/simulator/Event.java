/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.pizza.simulator;

/**
 *
 * @author sisak
 */
import cqu.pizza.lifecycle.Model;

/**
 * Base class for all events in the simulation.
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

    /** @return event time */
    public int getTime() {
        return time;
    }

    /**
     * Executes the event.
     *
     * @param m model reference
     * @param s scheduler to allow new events to be added
     */
    public abstract void process(Model m, ISchedule s);
}
