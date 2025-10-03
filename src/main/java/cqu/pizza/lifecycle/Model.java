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
public class Model {

    // request generator (distribution)
    private final IRequestDistribution requests;
    // pizza menu / timings
    private final Plan plan;
    // future use
    private final Report report = new Report();

    // tracking
    private final List<Order> allOrders       = new ArrayList<>();
    private final List<Order> activeOrders    = new ArrayList<>();
    private final List<Order> completedOrders = new ArrayList<>();
    private final List<Order> refusedOrders   = new ArrayList<>();

    // oven queue (not used in phase 2)
    private final Queue<Order> queue = new LinkedList<>();

    private int nextIdentifier = 1;

    public Model() {
        this.plan = new Plan();
        this.requests = new UnlimitedUniform(3); // interval = 3
    }

    public Request nextRequest() {
        return requests.next();
    }

    /**
     * Creates an order for the given request.
     * Prints to console whether it was actioned or refused.
     * Returns the Order if accepted; null if refused.
     */
    public Order order(Request r) {
        Order o = new Order(nextIdentifier++, r.orderTime(), r.pizza());
        allOrders.add(o);

        if (!plan.onMenu(r.pizza())) {
            // refused
            System.out.printf("t = %4d: Order %d for %s refused%n",
                    r.orderTime(), o.getId(), r.pizza());
            o.setFinish(r.orderTime());
            refusedOrders.add(o);
            return null;
        }

        // accepted
        System.out.printf("t = %4d: Order %d for %s actioned%n",
                r.orderTime(), o.getId(), r.pizza());
        activeOrders.add(o);
        return o;
    }

    // getters for future phases if needed
    public List<Order> getAllOrders()       { return allOrders; }
    public List<Order> getActiveOrders()    { return activeOrders; }
    public List<Order> getCompletedOrders() { return completedOrders; }
    public List<Order> getRefusedOrders()   { return refusedOrders; }
    public Queue<Order> getQueue()          { return queue; }
    public Plan getPlan()                   { return plan; }
}