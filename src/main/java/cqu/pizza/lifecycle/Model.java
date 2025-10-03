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
/**
 *
 * @author sisak
 */
/**
 * Holds the simulation state (orders, menu, distribution, queue placeholder).
 */
public class Model {

    private final IRequestDistribution requests;
    private final Plan plan;
    private final Report report = new Report();

    private final List<Order> allOrders       = new ArrayList<>();
    private final List<Order> activeOrders    = new ArrayList<>();
    private final List<Order> completedOrders = new ArrayList<>();
    private final List<Order> refusedOrders   = new ArrayList<>();

    private final Queue<Order> queue = new LinkedList<>();
    private int nextIdentifier = 1;

    /** Constructs the model and sets up the request distribution. */
    public Model() {
        this.plan = new Plan();
        this.requests = new UnlimitedUniform(3); // request interval for Phase 2
    }

    /**
     * Asks the distribution for the next request.
     *
     * @return next request
     */
    public Request nextRequest() {
        return requests.next();
    }

    /**
     * Creates a new order from the given request.
     * Prints a trace line for either acceptance or refusal.
     *
     * @param r request to convert to an order
     * @return the created order if accepted; null if refused
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
        return o;
    }

    // Accessors used by later phases
    public List<Order> getAllOrders()       { return allOrders; }
    public List<Order> getActiveOrders()    { return activeOrders; }
    public List<Order> getCompletedOrders() { return completedOrders; }
    public List<Order> getRefusedOrders()   { return refusedOrders; }
    public Queue<Order> getQueue()          { return queue; }
    public Plan getPlan()                   { return plan; }
}