/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.pizza.lifecycle.data;

/**
 *
 * @author sisak
 */
/**
 * Generates requests at a fixed interval, cycling over menu items.
 */
public class UnlimitedUniform implements IRequestDistribution {

    private final int interval;
    private int t;
    private final Pizza[] pizzas;
    private int n;

    /**
     * Creates a generator with the given interval.
     *
     * @param interval time between consecutive requests
     */
    public UnlimitedUniform(int interval) {
        this.interval = interval;
        this.pizzas = Plan.getPizzas();
        this.t = 0;
        this.n = 0;
    }

    /**
     * Returns the next request. Every fourth request is for "LOT"
     * to exercise the refusal path in Phase 2.
     *
     * @return next request
     */
    @Override
    public Request next() {
        Request r = new Request(pizzas[n % pizzas.length].name(), t);
        if (n == 3) { // one unavailable pizza request
            r = new Request("LOT", t);
        }
        t += interval;
        n++;
        return r;
    }
}
