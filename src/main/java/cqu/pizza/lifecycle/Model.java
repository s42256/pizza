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
 * The application model. Tracks all orders and provides the
 * processing methods used by events (prepare, queue, cook, box, finalise),
 * and creates the statistics report.
 *
 * @author sisak
 */
public class Model {

    /** Distribution used to generate request arrivals. */
    private final IRequestDistribution requests;

    /** Menu information and fixed step times. */
    private final Plan plan;

    /** Report built at the end of a run. */
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
     * Uses an interval of 3 time units for arrivals.
     */
    public Model() {
        this.plan = new Plan();
        this.requests = new UnlimitedUniform(3);
    }

    /**
     * Returns the next generated request.
     *
     * @return next {@link Request}
     */
    public Request nextRequest() {
        return requests.next();
    }

    /**
     * Creates a new order from a request and logs whether it is actioned or refused.
     * If refused, the order finishes at the request time and {@code null} is returned.
     *
     * @param r incoming request
     * @return created {@link Order}, or {@code null} if refused
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

    /**
     * Returns the correct singular/plural word for minutes.
     *
     * @param n number of minutes
     * @return "minute" if {@code n == 1}, otherwise "minutes"
     */
    private static String minWord(int n) { return (n == 1) ? "minute" : "minutes"; }

    /**
     * Performs the preparation step: prints a trace line and returns
     * the time when preparation completes.
     *
     * @param time current clock time
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
     * Performs the cooking step: prints a trace line and returns the
     * time when cooking completes.
     *
     * @param time current clock time
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
     * @param time current clock time
     * @param o    order being boxed
     * @return completion time of boxing
     */
    public int box(int time, Order o) {
        System.out.printf("t = %4d: Order %d boxing will take %d %s%n",
                time, o.getId(), BOXING_TIME, minWord(BOXING_TIME));
        return time + BOXING_TIME;
    }

    /**
     * Finalises an order: sets its finish time, moves it to the
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

    /**
     * Adds an order to the oven queue and prints how many are ahead.
     * If the queue was empty, the order starts cooking immediately and
     * this method returns {@code time}. Otherwise it returns {@code 0}.
     *
     * @param time current clock time
     * @param o    order joining the queue
     * @return {@code time} if cooking can start now; {@code 0} otherwise
     */
    public int queue(int time, Order o) {
        int ahead = queue.size();
        System.out.printf("t = %4d: Order %d has %d orders ahead of it in the queue%n",
                time, o.getId(), ahead);

        if (ahead == 0) {
            queue.add(o);
            return time;
        } else {
            queue.add(o);
            return 0;
        }
    }

    /**
     * Returns the order at the head of the queue (currently in oven),
     * or {@code null} if the queue is empty.
     *
     * @return head order, or {@code null} if none
     */
    public Order peek() {
        return queue.peek();
    }

    /**
     * Removes the order at the head of the queue (it has finished cooking)
     * and prints the "removed from oven" trace.
     *
     * @param time current clock time
     * @return the removed order, or {@code null} if the queue was empty
     */
    public Order remove(int time) {
        Order o = queue.poll();
        if (o != null) {
            System.out.printf("t = %4d: Order %d removed from oven%n", time, o.getId());
        }
        return o;
    }

    /**
     * Builds the summary report for the current model state and prints the
     * "Simulation stopped" line at the given duration.
     *
     * @param duration total run duration (stop time)
     */
    public void report(int duration) {
        System.out.printf("t = %4d: Simulation stopped%n", duration);
        this.report = new Report(duration, completedOrders, refusedOrders, activeOrders);
    }

    /**
     * Returns the most recently generated report, or {@code null} if none exists.
     *
     * @return current {@link Report} or {@code null}
     */
    public Report getReport() { return report; }

    /**
     * Returns all orders observed during the run.
     *
     * @return list of all orders
     */
    public List<Order> getAllOrders()       { return allOrders; }

    /**
     * Returns the active (in-progress) orders.
     *
     * @return list of active orders
     */
    public List<Order> getActiveOrders()    { return activeOrders; }

    /**
     * Returns the completed orders.
     *
     * @return list of completed orders
     */
    public List<Order> getCompletedOrders() { return completedOrders; }

    /**
     * Returns the refused orders.
     *
     * @return list of refused orders
     */
    public List<Order> getRefusedOrders()   { return refusedOrders; }

    /**
     * Returns the oven queue reference.
     *
     * @return oven queue
     */
    public Queue<Order> getQueue()          { return queue; }

    /**
     * Returns the plan used for menu and timings.
     *
     * @return {@link Plan}
     */
    public Plan getPlan()                   { return plan; }
}
