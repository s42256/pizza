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
 * Customer request for a pizza at a specific time.
 *
 * @param pizza requested pizza name
 * @param orderTime request time
 */
public record Request(String pizza, int orderTime) { }
