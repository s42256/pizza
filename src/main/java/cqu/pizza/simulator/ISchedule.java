/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.pizza.simulator;

/**
 *
 * @author sisak
 */
/**
 * Scheduling capability provided to events.
 */
public interface ISchedule {

    /**
     * Adds an event to the simulator's event queue.
     *
     * @param e event to schedule
     */
    void schedule(Event e);
}
