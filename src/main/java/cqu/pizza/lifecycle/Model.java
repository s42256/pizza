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
 * processing methods used by events (prepare, queue, cook, box, finalise),
 * plus report creation.
 */

public class Model {

    /** Distribution used to generate request arrivals. */
    private final IRequestDistribution requests;

    /** Menu information and fixed step times. */
    private final Plan plan;

    /** Report built at the end of a run (Phase 4+). */
    private Report report;

    /** All orders seen during the run. */
    private final List<Order> allOrders       = new ArrayList<>();
    /** Orders currently in progress. */
    private final List<Order> activeOrders    = new ArrayList<>();
    /** Completed orders. */
    private final List<Order> completedOrders = new ArrayList<>();
    /** Refused orders (not on the menu). */
    private final List<Order> refusedOrders   = new ArrayList<>();

    /** Oven queue (head == in oven). */
    private final Queue<Order> queue = new LinkedList<>();

    /** Next identifier to assign to an order. */
    private int nextIdentifier = 1;

    /**
     * Builds the model and initialises the request distribution.
     * The interval here matches the unit’s test data (3).
     */
    public Model() {
        this.plan = new Plan();
        this.requests = new UnlimitedUniform(3);
    }

    /** Returns the next generated request. */
    public Request nextRequest() {
        return requests.next();
    }

    /**
     * Creates a new order from a request and logs whether it is actioned or refused.
     * If refused, the order finishes at the request time and null is returned.
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

    // ---------------- Phase 3 step methods (no queue timing logic here) ----------------

    private static String minWord(int n) { return (n == 1) ? "minute" : "minutes"; }

    /** Preparation trace + completion time. */
    public int prepare(int time, Order o) {
        int prep = plan.getPreparationTime(o.getPizza());
        System.out.printf("t = %4d: Order %d preparation will take %d %s%n",
                time, o.getId(), prep, minWord(prep));
        return time + prep;
    }

    /** Cooking trace + completion time. */
    public int cook(int time, Order o) {
        System.out.printf("t = %4d: Order %d cooking will take %d %s%n",
                time, o.getId(), COOKING_TIME, minWord(COOKING_TIME));
        return time + COOKING_TIME;
    }

    /** Boxing trace + completion time. */
    public int box(int time, Order o) {
        System.out.printf("t = %4d: Order %d boxing will take %d %s%n",
                time, o.getId(), BOXING_TIME, minWord(BOXING_TIME));
        return time + BOXING_TIME;
    }

    /** Finalise trace + moves order to completed and sets finish time. */
    public void finalise(int time, Order o) {
        o.setFinish(time);
        activeOrders.remove(o);
        completedOrders.add(o);
        System.out.printf("t = %4d: Order %d completed%n", time, o.getId());
    }

    // ---------------- Phase 6: one-oven queue methods ----------------

    /**
     * Adds an order to the oven queue and prints how many are ahead.
     * If the queue was empty, returns the same time (start cooking now).
     * If not empty, returns 0 (must wait).
     */
    public int queue(int time, Order o) {
        int ahead = queue.size();
        System.out.printf("t = %4d: Order %d has %d orders ahead of it in the queue%n",
                time, o.getId(), ahead);

        if (ahead == 0) {
            // starts cooking immediately
            queue.add(o);
            return time;
        } else {
            // joins the end of the queue
            queue.add(o);
            return 0;
        }
    }

    /** Peeks at the order at the head of the queue (in oven), or null if none. */
    public Order peek() {
        return queue.peek();
    }

    /**
     * Removes the order at the head of the queue (it has finished cooking)
     * and prints the “removed from oven” trace.
     */
    public Order remove(int time) {
        Order o = queue.poll();
        if (o != null) {
            System.out.printf("t = %4d: Order %d removed from oven%n", time, o.getId());
        }
        return o;
    }

    // ---------------- Reporting (Phase 4/5) ----------------

    /** Builds the report and also prints the “Simulation stopped” line. */
    public void report(int duration) {
        System.out.printf("t = %4d: Simulation stopped%n", duration);
        this.report = new Report(duration, completedOrders, refusedOrders, activeOrders);
    }

    public Report getReport() { return report; }

    // Accessors used elsewhere / for completeness
    public List<Order> getAllOrders()       { return allOrders; }
    public List<Order> getActiveOrders()    { return activeOrders; }
    public List<Order> getCompletedOrders() { return completedOrders; }
    public List<Order> getRefusedOrders()   { return refusedOrders; }
    public Queue<Order> getQueue()          { return queue; }
    public Plan getPlan()                   { return plan; }
}