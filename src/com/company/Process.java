package com.company;

public class Process {

    int numProcesses;
    int arrivalTime;
    int maxBurstTime;
    int quantum;
    int latency;

    public Process() {}

    public Process(int numProcesses, int arrivalTime, int maxBurstTime, int quantum, int latency) {
        this.numProcesses = numProcesses;
        this.arrivalTime = arrivalTime;
        this.maxBurstTime = maxBurstTime;
        this.quantum = quantum;
        this.latency = latency;
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

    public int getMaxBurstTime() {
        return maxBurstTime;
    }

    public void setMaxBurstTime(int maxBurstTime) {
        this.maxBurstTime = maxBurstTime;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }
}
