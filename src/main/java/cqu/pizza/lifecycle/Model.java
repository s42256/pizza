/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.pizza.lifecycle;

import cqu.pizza.lifecycle.data.IRequestDistribution;
import cqu.pizza.lifecycle.data.Plan;
import cqu.pizza.lifecycle.data.Request;
import cqu.pizza.lifecycle.data.UnlimitedUniform;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static cqu.pizza.lifecycle.data.Plan.BOXING_TIME;
import static cqu.pizza.lifecycle.data.Plan.COOKING_TIME;
/**
 *
 * @author sisak
 */
/**
 * The application model. Tracks all orders and provides the
 * processing methods used by events (prepare, cook, box, finalise).
 */
public class Model {

    /** Distribution used to generate request arrivals. */
    private final IRequestDistribution requests;

    /** Menu information and fixed step times. */
    private final Plan plan;

    /** The report generated at the end of a run (Phase 4). */
    private Report report;

    /** All orders seen during the run. */
    private final List<Order> allOrders       = new ArrayList<>();
    /** Orders currently in progress. */
    private final List<Order> activeOrders    = new ArrayList<>();
    /** Completed orders. */
    private final List<Order> completedOrders = new ArrayList<>();
    /** Refused orders (not on the menu). */
    private final List<Order> refusedOrders   = new ArrayList<>();

    /** Oven queue placeholder for later phases. */
    private final Queue<Order> queue = new LinkedList<>();

    /** Next identifier to assign to an order. */
    private int nextIdentifier = 1;

    /**
     * Builds the model and initialises the request distribution.
     * The interval here matches the Phase 2/3 tests.
     */
    public Model() {
        this.plan = new Plan();
        this.requests = new UnlimitedUniform(3);
    }

    /**
     * Returns the next generated request.
     * @return next {@link Request}
     */
    public Request nextRequest() {
        return requests.next();
    }

    /**
     * Creates a new {@link Order} from a request and logs whether the
     * order is actioned or refused. If refused, the order is finished
     * at the request time and {@code null} is returned.
     *
     * @param r the incoming request
     * @return the new order, or {@code null} if refused
     */
    public Order order(Request r) {
        Order o = new Order(nextIdentifier++, r.orderTime(), r.pizza());
        allOrders.add(o);

        if (!plan.onMenu(r.pizza())) {
            System.out.printf("t = %4d: Order %d for %s refused%n",
                    r.orderTime(), o.getId(), r.pizza());
            o.setFinish(r.orderTime());
            refusedOrders.add(o);
            return null;
        }

        System.out.printf("t = %4d: Order %d for %s actioned%n",
                r.orderTime(), o.getId(), r.pizza());
        activeOrders.add(o);
        o.stepCompleted();
        return o;
    }

    // ---------------- Phase 3 step methods (no queue) ----------------

    /**
     * Helper for "minute/minutes" wording.
     * @param n number of minutes
     * @return "minute" if n==1, otherwise "minutes"
     */
    private static String minWord(int n) {
        return (n == 1) ? "minute" : "minutes";
    }

    /**
     * Performs the preparation step: prints a trace line and returns
     * the time when preparation completes.
     *
     * @param time current clock
     * @param o    order being prepared
     * @return completion time of preparation
     */
    public int prepare(int time, Order o) {
        int prep = plan.getPreparationTime(o.getPizza());
        System.out.printf("t = %4d: Order %d preparation will take %d %s%n",
                time, o.getId(), prep, minWord(prep));
        return time + prep;
    }

    /**
     * Performs the cooking step: prints a trace line and returns
     * the time when cooking completes.
     *
     * @param time current clock
     * @param o    order being cooked
     * @return completion time of cooking
     */
    public int cook(int time, Order o) {
        System.out.printf("t = %4d: Order %d cooking will take %d %s%n",
                time, o.getId(), COOKING_TIME, minWord(COOKING_TIME));
        return time + COOKING_TIME;
    }

    /**
     * Performs the boxing step: prints a trace line and returns
     * the time when boxing completes.
     *
     * @param time current clock
     * @param o    order being boxed
     * @return completion time of boxing
     */
    public int box(int time, Order o) {
        System.out.printf("t = %4d: Order %d boxing will take %d %s%n",
                time, o.getId(), BOXING_TIME, minWord(BOXING_TIME));
        return time + BOXING_TIME;
    }

    /**
     * Finalises the order: sets its finish time, moves it to the
     * completed list and prints the completion trace.
     *
     * @param time finish time
     * @param o    order to finalise
     */
    public void finalise(int time, Order o) {
        o.setFinish(time);
        activeOrders.remove(o);
        completedOrders.add(o);
        System.out.printf("t = %4d: Order %d completed%n", time, o.getId());
    }

    // ---------------- Phase 4 report methods ----------------

    /**
     * Builds and stores the statistics/status report for the current model state.
     * Called from {@code ReportEvent} at the simulation stop time.
     *
     * @param duration the total run duration (stop time)
     */
    public void report(int duration) {
        this.report = new Report(duration, completedOrders, refusedOrders, activeOrders);
    }

    /**
     * Returns the most recently generated report (may be {@code null}
     * if the simulation has not yet produced one).
     *
     * @return the report for the last run, or {@code null}
     */
    public Report getReport() {
        return report;
    }

    // Accessors used in later phases / report
    public List<Order> getAllOrders()       { return allOrders; }
    public List<Order> getActiveOrders()    { return activeOrders; }
    public List<Order> getCompletedOrders() { return completedOrders; }
    public List<Order> getRefusedOrders()   { return refusedOrders; }
    public Queue<Order> getQueue()          { return queue; }
    public Plan getPlan()                   { return plan; }
}