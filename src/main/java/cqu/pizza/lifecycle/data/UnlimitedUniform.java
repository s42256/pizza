/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.pizza.lifecycle.data;

/**
 *
 * @author sisak
 */
public class UnlimitedUniform implements IRequestDistribution {

    private final int interval; // time between requests
    private int t;              // next request time
    private final Pizza[] pizzas;
    private int n;              // number of requests so far

    // requests at fixed intervals, cycling through pizzas
    public UnlimitedUniform(int interval) {
        this.interval = interval;
        this.pizzas = Plan.getPizzas();
        this.t = 0;
        this.n = 0;
    }

    @Override
    public Request next() {
        Request r = new Request(pizzas[n % pizzas.length].name(), t);
        // inject a request for a non-menu pizza to exercise refusal path
        if (n == 3) {
            r = new Request("LOT", t);
        }
        t += interval;
        n++;
        return r;
    }
}
