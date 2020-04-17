/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication5;

import java.util.ArrayList;

/**
 *
 * @author lilian
 */
public class State {    
    private ArrayList<State> successors = new ArrayList<>();
    private ArrayList<State> predecessors = new ArrayList<>();
    private boolean input;
    private boolean output;
    private int weight;
    
    State(){ 
        successors = null;
        predecessors = null;
        input = false;
        output = false;
        weight = 0;
    }

    public ArrayList<State> getSuccessors() {
        return successors;
    }

    public void addSuccessors(State next) {
        this.successors.add(next);
    }

    public ArrayList<State> getPredecessors() {
        return predecessors;
    }
    
    public void addPredecessors(State prev) {
        this.predecessors.add(prev);
    }

    public boolean isInput() {
        return input;
    }

    public void setInput(boolean isInput) {
        this.input = isInput;
    }

    public boolean isOutput() {
        return output;
    }

    public void setOutput(boolean isOutput) {
        this.output = isOutput;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
