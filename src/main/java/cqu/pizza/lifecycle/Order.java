/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.pizza.lifecycle;

/**
 *
 * @author sisak
 */
public class Order {

    private final int id;
    private final int start;
    private int finish = -1;       // -1 = not finished/refused yet
    private final String pizza;
    private int step = 0;          // increment as steps complete later

    public Order(int id, int start, String pizza) {
        this.id = id;
        this.start = start;
        this.pizza = pizza;
    }

    public int getId()     { return id; }
    public int getStart()  { return start; }
    public int getFinish() { return finish; }
    public String getPizza(){ return pizza; }
    public int getStep()   { return step; }

    public void setFinish(int t) { this.finish = t; }
    public void stepCompleted()  { this.step++; }

    // matches sample format: <id,start,finish,pizza,step>
    @Override
    public String toString() {
        return String.format("<%d,%d,%d,%s,%d>", id, start, finish, pizza, step);
    }
}