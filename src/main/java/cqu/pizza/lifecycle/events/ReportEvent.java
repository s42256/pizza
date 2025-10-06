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
 * Final event that triggers building the summary report.
 * In Phase 4 this calls the model to construct the report for the GUI.
 */
public class ReportEvent extends Event {

    /**
     * Creates a report event for the given time.
     *
     * @param time scheduled report time (simulation stop time)
     */
    public ReportEvent(int time) {
        super(time);
    }

    /**
     * Generates the final report via the model.
     *
     * @param m model reference
     * @param s scheduler reference (unused here)
     */
    @Override
    public void process(Model m, ISchedule s) {
        // Build the report for display in the GUI
        m.report(getTime());
        // No follow-up events are scheduled from here.
    }
}
