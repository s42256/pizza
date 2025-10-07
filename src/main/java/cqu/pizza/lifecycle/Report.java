/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.pizza.lifecycle;

import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.List;

/**
 *
 * @author sisak
 */
/**
 * Immutable value object holding the report text rendered for the GUI.
 * The constructor builds the full text once using a StringBuilder.
 *
 * The order format is: &lt;id,start,finish,pizza,step&gt;
 */
public class Report {

    /** Final text shown in the GUI report area. */
    private final String text;

    /**
     * Builds the full report content.
     *
     * @param duration total simulation time (stop time)
     * @param completed list of completed orders
     * @param refused list of refused orders
     * @param active list of currently active orders
     */
    public Report(int duration,
                  List<Order> completed,
                  List<Order> refused,
                  List<Order> active) {

        StringBuilder sb = new StringBuilder();

        sb.append("""
                Statistics
                ==========
                """);
        sb.append(String.format("Duration of run = %d%n", duration));
        sb.append(String.format("Orders completed = %d%n", completed.size()));
        sb.append(String.format("Orders refused = %d%n", refused.size()));
        sb.append(String.format("Orders active = %d%n%n", active.size()));

        // Completed
        sb.append("""
                Completed orders
                ================
                """);
        for (Order o : completed) {
            sb.append(o.toString()).append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());

        // Refused
        sb.append("""
                Refused orders
                ==============
                """);
        for (Order o : refused) {
            sb.append(o.toString()).append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());

        // Active
        sb.append("""
                Active orders
                =============
                """);
        for (Order o : active) {
            sb.append(o.toString()).append(System.lineSeparator());
        }

        this.text = sb.toString();
    }

    /**
     * Returns the completed report text.
     * @return report text
     */
    public String getText() {
        return text;
    }

    /**
     * Saves the report text to the specified filename using a Formatter.
     * Uses try-with-resources and wraps any I/O errors in a ReportException.
     *
     * @param filename target file name or path
     * @throws ReportException if the file cannot be opened or written
     */
    public void save(String filename) throws ReportException {
        try (Formatter out = new Formatter(filename)) {
            out.format("%s", text);
        } catch (SecurityException e) {
            throw new ReportException("Error accessing " + filename);
        } catch (FileNotFoundException e) {
            throw new ReportException("Error opening " + filename);
        } catch (FormatterClosedException e) {
            throw new ReportException("Error writing to " + filename);
        }
    }
}
