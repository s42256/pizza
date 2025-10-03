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
public class Simulator implements ISchedule {

    private final List<Event> events = new ArrayList<>();
    private final Model model;
    private int clock = 0;

    public Simulator(Model model) {
        this.model = model;
    }

    public void initialize(Event firstEvent, Event reportEvent) {
        schedule(firstEvent);
        schedule(reportEvent);
    }

   
    public void run(int stopTime) {
        if ((events == null) || events.isEmpty())
            return;
        Event e = events.remove(0);
        clock = e.getTime();
        // events queue will never become empty as after the first event is
        // added, every arrival event will generate a new arrival event
        // (which may be greater than the stop time)
        while (clock <= stopTime) {
            e.process(model, this); // pass simulator (as ISchedule) to allow scheduling
            e = events.remove(0);
            clock = e.getTime();
        }
    }

    @Override
    public void schedule(Event e) {
        // insert into 'events' keeping ascending time order,
        // placing new event BEFORE any with the same time
        int i = 0;
        while (i < events.size() && e.getTime() > events.get(i).getTime()) {
            i++;
        }
        // if times are equal, we do not advance, so 'e' goes before existing equal-time event
        events.add(i, e);
    }
}
