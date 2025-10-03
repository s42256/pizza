/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.pizza.lifecycle.events;

import cqu.pizza.lifecycle.Model;
import cqu.pizza.simulator.Event;
import cqu.pizza.simulator.ISchedule;

/**
 *
 * @author sisak
 */
/**
 * Final event that will later generate the summary report.
 * In Phase 2 it prints a placeholder message.
 */
public class ReportEvent extends Event {

    /**
     * Creates a report event for the given time.
     *
     * @param time scheduled report time
     */
    public ReportEvent(int time) {
        super(time);
    }

    /**
     * Prints the placeholder message for Phase 2.
     *
     * @param m model reference
     * @param s scheduler reference
     */
    @Override
    public void process(Model m, ISchedule s) {
        System.out.printf("Report to be generated at time %d - under development%n", getTime());
    }
}
