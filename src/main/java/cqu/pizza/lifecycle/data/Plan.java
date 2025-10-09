package cqu.pizza.lifecycle.data;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sisak
 */

public class Plan {

    /** Cooking time used for all pizzas in this phase. */
    public static final int COOKING_TIME = 5;

    /** Boxing time used for all pizzas in this phase. */
    public static final int BOXING_TIME  = 1;

    /**
     * Menu as an array of {@link Pizza} records.
     * Package-private and static
     */
    static Pizza[] pizzas = {
        new Pizza("P&P", 5),
        new Pizza("P&O", 4)
    };

    /** Map for quick lookup by pizza name. */
    private final Map<String, Pizza> menu = new HashMap<>();

    /** Builds the lookup map from {@link #pizzas}. */
    public Plan() {
        for (Pizza p : pizzas) {
            menu.put(p.name(), p);
        }
    }

    /**
     * Returns the backing array of pizzas (package-private).
     * @return the menu array
     */
    static Pizza[] getPizzas() {
        return pizzas;
    }

    /**
     * Indicates whether a pizza is on the menu.
     * @param pizzaName the name to check
     * @return {@code true} if present, {@code false} otherwise
     */
    public boolean onMenu(String pizzaName) {
        return menu.containsKey(pizzaName);
    }

    /**
     * Returns the preparation time for a given pizza.
     * @param pizzaName menu name
     * @return minutes to prepare, or {@code -1} if not on the menu
     */
    public int getPreparationTime(String pizzaName) {
        Pizza p = menu.get(pizzaName);
        return (p == null) ? -1 : p.preparationTime();
    }
}