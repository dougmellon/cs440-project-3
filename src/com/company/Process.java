package com.company;

public class Process {

    int id;
    int numProcesses;
    int arrivalTime;
    int burstTime;
    int quantum;

    public Process() {}

    public Process(int id, int numProcesses, int arrivalTime, int burstTime, int quantum) {
        this.id = id;
        this.numProcesses = numProcesses;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.quantum = quantum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumProcesses() {
        return numProcesses;
    }

    public void setNumProcesses(int numProcesses) {
        this.numProcesses = numProcesses;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }


    public String toString() {
        return "P" + this.id + " selected for " + this.burstTime + " units";
    }
}
