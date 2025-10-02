/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.pizza.lifecycle.data;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sisak
 */
public class Plan {

    static Pizza[] pizzas = {
        new Pizza("P&P", 5),
        new Pizza("P&O", 4)
    };
    private final Map<String, Pizza> menu = new HashMap<>();

    public Plan() {
        for (Pizza p : pizzas) {
            menu.put(p.name(), p);
        }
    }

    // package-private static getter per spec
    static Pizza[] getPizzas() {
        return pizzas;
    }

    public boolean onMenu(String pizzaName) {
        return menu.containsKey(pizzaName);
    }

    public int getPreparationTime(String pizzaName) {
        Pizza p = menu.get(pizzaName);
        return (p == null) ? -1 : p.preparationTime();
    }
}
}
