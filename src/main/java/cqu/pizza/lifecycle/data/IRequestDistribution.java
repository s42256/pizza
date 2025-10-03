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
 * Supplies the next customer request according to some distribution.
 */
public interface IRequestDistribution {

    /**
     * Returns the next request.
     *
     * @return next request
     */
    Request next();
}
