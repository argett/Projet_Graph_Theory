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
    private int stateNB;
    
    State(int i){ 
        input = false;
        output = false;
        weight = -1;
        stateNB = i;
    }

    public ArrayList<State> getSuccessors() {
        return successors;
    }

    public void addSuccessors(State next) {
        this.successors.add(next);
    }
    
    public String printSuccessors() {
        String succ = "";
        for(int i = 0; i<this.successors.size(); i++){
            succ = succ.concat(String.valueOf(this.successors.get(i).getStateNB()) + " ");
        }
        return succ;
    }
    
    public ArrayList<State> getPredecessors() {
        for(int i = 0; i<this.predecessors.size(); i++){
            System.out.println(this.predecessors.get(i));
        }
        return predecessors;
    }
    
    public String printPredecessors() {
        String succ = "";
        for(int i = 0; i<this.predecessors.size(); i++){
            succ = succ.concat(String.valueOf(this.predecessors.get(i).getStateNB()) + " ");
        }
        return succ;
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

    public int getStateNB() {
        return stateNB;
    }

    public void setStateNB(int stateNB) {
        this.stateNB = stateNB;
    }   
}
