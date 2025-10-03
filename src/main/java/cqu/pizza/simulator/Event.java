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

public abstract class Event {

    private final int time;

    public Event(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public abstract void process(Model m, ISchedule s);
}
