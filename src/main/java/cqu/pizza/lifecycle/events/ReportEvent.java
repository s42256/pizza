package cqu.pizza.lifecycle.events;

import cqu.pizza.lifecycle.Model;
import cqu.pizza.simulator.Event;
import cqu.pizza.simulator.ISchedule;

/**
 * Event that triggers the building of the summary report at the stop time.
 *
 * @author sisak
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
     * Invokes the model to build the report for display in the GUI.
     *
     * @param m model reference
     * @param s scheduler reference (unused)
     */
    @Override
    public void process(Model m, ISchedule s) {
        m.report(getTime());
    }
}
