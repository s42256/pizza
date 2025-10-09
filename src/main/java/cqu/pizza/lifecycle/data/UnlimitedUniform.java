package cqu.pizza.lifecycle.data;

/**
 *
 * @author sisak
 */
/**
 * Generates {@link Request}s at a fixed interval, cycling over menu items.
 * Every fourth request is for {@code "LOT"} to exercise the refusal path.
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
     * Returns the next request. The sequence alternates between menu items,
     * and every 4th request (index 3) is for a non-menu pizza {@code "LOT"}.
     *
     * @return next {@link Request}
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
