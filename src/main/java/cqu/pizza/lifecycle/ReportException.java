/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.pizza.lifecycle;

/**
 *
 * @author sisak
 */
public class ReportException extends RuntimeException {

    /**
     * Creates a new ReportException with a message.
     * @param errorMessage description of the problem
     */
    public ReportException(String errorMessage) {
        super(errorMessage);
    }
}
