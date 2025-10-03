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
public class ReportEvent extends Event {

    public ReportEvent(int time) {
        super(time);
    }

    @Override
    public void process(Model m, ISchedule s) {
        System.out.printf("Report to be generated at time %d - under development%n", getTime());
    }
}
