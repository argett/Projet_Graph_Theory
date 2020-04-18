/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication5;

import java.util.ArrayList;

/**
 * @author lilian
 */
public class State {    
    private ArrayList<State> predecessors = new ArrayList<>();
    private ArrayList<Integer> weight = new ArrayList<>();
    private ArrayList<State> successors = new ArrayList<>();
    private boolean input;
    private boolean output;
    private int stateNB;
    
    State(int i){ 
        input = false;
        output = false;
        stateNB = i;
    }

    public State getSuccessors(int i) {
        return successors.get(i);
    }
    
    public int getSuccessorsLength() {
        return successors.size();
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
    
    public int getWeight(int i) {
        return weight.get(i);
    }
    
     public ArrayList<Integer> getWeights() {
        return weight;
    }
    
    public int getWeightLength() {
        return weight.size();
    }

    public void addWeight(int i) {
        this.weight.add(i);
    }
    
    public String printWeight() {
        String succ = "";
        for(int i = 0; i<this.weight.size(); i++){
            succ = succ.concat(String.valueOf(this.weight.get(i)) + " ");
        }
        return succ;
    }
    
    public State getPredecessors(int i) {
        return predecessors.get(i);
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

    public int getStateNB() {
        return stateNB;
    }

    public void setStateNB(int stateNB) {
        this.stateNB = stateNB;
    } 
    
}
