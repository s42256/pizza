package cqu.pizza.lifecycle;

/**
 *
 * @author sisak
 */
public class Order {

    private final int id;
    private final int start;
    private int finish = -1;
    private final String pizza;
    private int step = 0;

    /**
     * Creates a new order.
     *
     * @param id unique order identifier
     * @param start time the order was created
     * @param pizza pizza name
     */
    public Order(int id, int start, String pizza) {
        this.id = id;
        this.start = start;
        this.pizza = pizza;
    }

    /** @return order identifier */
    public int getId() { return id; }

    /** @return order start time */
    public int getStart() { return start; }

    /** @return order finish time, or -1 if not finished */
    public int getFinish() { return finish; }

    /** @return pizza name */
    public String getPizza() { return pizza; }

    /** @return current step index */
    public int getStep() { return step; }

    /**
     * Marks the order as finished.
     *
     * @param t finish time
     */
    public void setFinish(int t) { this.finish = t; }

    /** Increments the current step. */
    public void stepCompleted() { this.step++; }

    /**
     * Returns a compact representation used in reports.
     * Format: &lt;id,start,finish,pizza,step&gt;
     *
     * @return formatted string
     */
    @Override
    public String toString() {
        return String.format("<%d,%d,%d,%s,%d>", id, start, finish, pizza, step);
    }
}