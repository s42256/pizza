/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.pizza.simulator;

import cqu.pizza.lifecycle.Model;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author sisak
 */
/**
 * Manages the event queue and runs the simulation loop.
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
     * @param firstEvent first arrival event at time 0
     * @param reportEvent report event at stop time
     */
    public void initialize(Event firstEvent, Event reportEvent) {
        schedule(firstEvent);
        schedule(reportEvent);
    }

    /**
     * Runs the simulation until the stop time.
     * (Method reproduced exactly as required.)
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
            e.process(model, this);// the this argument means that we are
            // passing a reference to this simulator
            // object to the event’s process method –
            // so it will be able to schedule events
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
