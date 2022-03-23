package com.company;

import java.util.*;

public class Main {

    static long seed;
    static int numProcesses;
    static int maxArrivalTime;
    static int maxBurstTime;
    static int quantum;
    static int latency;
    static int clock = 0;
    static int contextSwitch = 1;

    static List<Process> processes = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); // create the new scanner

        // take in the seed value
        System.out.print("Enter a seed value: ");
        seed = scanner.nextLong();

        // take in the number of processes
        System.out.print("Enter number of processes (2, 10): ");
        do {
            numProcesses = scanner.nextInt();

            if (numProcesses < 2 || numProcesses > 10) {
                System.out.println("--- Error: Enter a valid number between 2 and 10 (inclusive)");
                System.out.print("Enter number of processes (2, 10): ");
            }

        } while (numProcesses < 2 || numProcesses > 10);

        // take in the arrival time
        System.out.print("Enter last possible arrival time (0, 99): ");
        do {
            maxArrivalTime = scanner.nextInt();

            if (maxArrivalTime < 0 || maxArrivalTime > 99) {
                System.out.println("--- Error: Enter a valid number between 0 and 99 (inclusive)");
                System.out.print("Enter last possible arrival time (0, 99): ");
            }

        } while (maxArrivalTime < 0 || maxArrivalTime > 99);

        // take in the max burst time
        System.out.print("Enter max burst time (1, 100): ");
        do {
            maxBurstTime = scanner.nextInt();

            if (maxBurstTime < 1 || maxBurstTime > 100) {
                System.out.println("--- Error: Enter a valid number between 1 and 100 (inclusive)");
                System.out.print("Enter max burst time (1, 100): ");
            }

        } while (maxBurstTime < 1 || maxBurstTime > 100);

        // take in the quantum size
        System.out.print("Enter quantum size (1, 100): ");
        do {
            quantum = scanner.nextInt();

            if (quantum < 1 || quantum > 100) {
                System.out.println("--- Error: Enter a valid number between 1 and 100 (inclusive)");
                System.out.print("Enter quantum size (1, 100): ");
            }

        } while (quantum < 1 || quantum > 100);

        // take in the latency
        System.out.print("Enter latency (0, 10): ");
        do {
            latency = scanner.nextInt();

            if (latency < 0 || latency > 10) {
                System.out.println("--- Error: Enter a valid number between 0 and 10 (inclusive)");
                System.out.print("Enter latency (0, 10): ");
            }

        } while (latency < 1 || latency > 10);

        // create processes and add them to a hashmap
        for (int i = 1; i < numProcesses + 1; i++) {

            Random randNum = new Random();

            int arrivalTime = randNum.nextInt(maxArrivalTime - 1) + 1;
            int burstTime = randNum.nextInt(maxBurstTime - 1) + 1;


            Process process = new Process(
                    i,
                    numProcesses,
                    arrivalTime,
                    burstTime,
                    quantum
            );

            processes.add(process);

        }

        // output created processes
        System.out.printf("\n%s Processes created.", processes.size());
        System.out.printf("\n%-5s %-10s %s%n", "P", "Arrival", "Burst");

        List<Process> rrProcesses = processes;
        List<Process> sjfProcesses =  processes;
        List<Process> randProcesses = processes;
        List<Process> fcfsProcesses = processes;

        processes.forEach(process -> {
            System.out.printf("%-5s %-10s %s%n",
                    process.getId(),
                    process.getArrivalTime(),
                    process.getBurstTime());
        });


        // ----------------- fcfs -----------------
        clock = 0;
        contextSwitch = 1;

        System.out.println("\nFCFS:");

        fcfsProcesses.sort(Comparator.comparing(Process::getArrivalTime));

        for (int i = 0; i < fcfsProcesses.size(); i++) {
            System.out.println("@t=" + clock + ", " + "P" + fcfsProcesses.get(i).getId() + ", selected for " + fcfsProcesses.get(i).getBurstTime() + " units");

            clock += fcfsProcesses.get(i).getBurstTime();

            if (i + 1 != fcfsProcesses.size()) {
                System.out.println("@t=" + clock + ", context switch " + contextSwitch + " occurs");
                contextSwitch++;
                clock += latency;
            }
        }

        System.out.println("@t=" + clock + ", all processes complete");
        System.out.println("Completed in " + clock + " cycles.");

        // ----------------- sjf -----------------
        clock = 0;
        contextSwitch = 1;

        System.out.println("\nSJF:");

        // sort the rest by their burst time
        sjfProcesses.sort(Comparator.comparing(Process::getBurstTime));

        for (int i = 0; i < sjfProcesses.size(); i++) {
            System.out.println("@t=" + clock + ", " + "P" + sjfProcesses.get(i).getId() + ", selected for " + sjfProcesses.get(i).getBurstTime() + " units");

            clock += sjfProcesses.get(i).getBurstTime();

            if (i + 1 != sjfProcesses.size()) {
                System.out.println("@t=" + clock + ", context switch " + contextSwitch + " occurs");
                contextSwitch++;
                clock += latency;
            }
        }

        System.out.println("@t=" + clock + ", all processes complete");
        System.out.println("Completed in " + clock + " cycles.");

        // ----------------- random -----------------
        clock = 0;
        contextSwitch = 1;

        System.out.println("\nRandom:");

        randProcesses.sort(Comparator.comparing(Process::getArrivalTime));

        Collections.shuffle(randProcesses, new Random(seed)); // randomly shuffle the list of processes

        for (int i = 0; i < randProcesses.size(); i++) {
            System.out.println("@t=" + clock + ", " + "P" + randProcesses.get(i).getId() + ", selected for " + randProcesses.get(i).getBurstTime() + "units");

            clock += randProcesses.get(i).getBurstTime();

            if (i + 1 != randProcesses.size()) {
                System.out.println("@t=" + clock + ", context switch " + contextSwitch + " occurs");
                contextSwitch++;
                clock += latency;
            }
        }

        System.out.println("@t=" + clock + ", all processes complete");
        System.out.println("Completed in " + clock + " cycles.");

        // ----------------- RR -----------------
        clock = 0;
        contextSwitch = 1;

        System.out.println("\nRR (q=" + quantum + ") :");


        rrProcesses.sort(Comparator.comparing(Process::getId)); // sort the processes by id

        int listSize = rrProcesses.size();


        while (listSize > 0) {
            for (Process process : rrProcesses) {
                if (process.getBurstTime() < quantum && process.getBurstTime() != 0) {
                    System.out.println("@t=" + ", P" + process.getId() + " selected for " + process.getBurstTime() + " units");
                    process.setBurstTime(0);
                    listSize--;
                } else if (process.getBurstTime() > quantum && process.getBurstTime() != 0) {
                    System.out.println("@t=" + ", P" + process.getId() + " selected for " + quantum + " units");
                    process.setBurstTime(process.getBurstTime() - quantum);
                }
            }
        }
    }
}
