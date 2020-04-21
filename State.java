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
    private ArrayList<State> predecessors = new ArrayList<>();
    private ArrayList<Integer> weight = new ArrayList<>();
    private ArrayList<State> successors = new ArrayList<>();
    private boolean input;
    private boolean output;
    private int rank;
    private int stateNB; // 
    private int dFromInput; // the maximum distance bewteen the input and the state
    private State maxPrev; // the place of the predecessor state from where comes from the maximal distance with the input
    
    State(int i){ 
        input = false;
        output = false;
        rank = -1;
        stateNB = i;
        dFromInput = 0;
    }

    public State getSuccessor(int i) {
        return successors.get(i);
    }    
    public State setSuccessor(State state, int rank) {
        return successors.set(rank, state);
    }    
    public ArrayList<State> getSuccessors() {
        return successors;
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
    public Integer getWeightsOfSucc(State state){
        int rank = -1;
        for(State temp : this.successors){
            rank++;
            if(temp == state)
                return this.getWeight(rank);
        }
        System.out.println("Probleme dans la liste des successor de weight");
        return null;
    }    
    public Integer getWeightsOfPred(State state){
        int rank = -1;
        for(State temp : this.predecessors){
            rank++;
            if(temp == state)
                return this.getWeight(rank);
        }
        return null;
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
    public ArrayList<State> getPredecessors() {
        return predecessors;
    }    
    public String printPredecessors() {
        String succ = "";
        for(int i = 0; i<this.predecessors.size(); i++){
            succ = succ.concat(String.valueOf(this.predecessors.get(i).getStateNB()) + " ");
        }
        return succ;
    }    
    public int getPredecessorsLength() {
        return predecessors.size();
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
    public static State getInput(ArrayList<State> graph){
        for(State input : graph)
            if(input.isInput())
                return input;
        return null;
    }    
    public State modifInput(State newInput){
        return newInput;
    }

    public boolean isOutput() {
        return output;
    }
    public void setOutput(boolean isOutput) {
        this.output = isOutput;
    }
    public static State getOutput(ArrayList<State> graph){
        for(State output : graph)
            if(output.isOutput())
                return output;
        return null;
    } 
    
    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getStateNB() {
        return stateNB;
    }
    public void setStateNB(int stateNB) {
        this.stateNB = stateNB;
    } 
    
    public int getdFromInput() {
        return dFromInput;
    }
    public void setdFromInput(int dist) {
        this.dFromInput = dist;
    }
    
    public State getdPrev() {
        return maxPrev;
    }
    public void setdPrev(State prev) {
        this.maxPrev = prev;
    }
    
}
