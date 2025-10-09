package cqu.pizza.lifecycle.data;

/**
 *
 * @author sisak
 */
/**
 * Customer request for a pizza at a specific time.
 *
 * @param pizza requested pizza name
 * @param orderTime request time
 */
public record Request(String pizza, int orderTime) { }