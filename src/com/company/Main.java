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


        processes.forEach(process -> {
            System.out.printf("%-5s %-10s %s%n",
                    process.getId(),
                    process.getArrivalTime(),
                    process.getBurstTime());
        });

        // ----------------- random -----------------
        clock = 0;
        contextSwitch = 1;

        System.out.println("\nRandom:");

        List<Process> randProcesses = processes;

        randProcesses.sort(Comparator.comparing(Process::getArrivalTime));
        System.out.println("@t=" + clock + ", " + randProcesses.get(0).toString());
        clock += randProcesses.get(0).getBurstTime();
        System.out.println("@t=" + clock + ", context switch " + contextSwitch + " occurs");
        clock += 2;
        randProcesses.remove(0);

        Collections.shuffle(randProcesses, new Random(seed)); // randomly shuffle the list of processes

        for (int i = 0; i < randProcesses.size(); i++) {
            System.out.println("@t=" + clock + ", " + randProcesses.get(i).toString());

            clock += randProcesses.get(i).getBurstTime();

            if (i + 1 != randProcesses.size()) {
                System.out.println("@t=" + clock + ", context switch " + contextSwitch + " occurs");
                contextSwitch++;
                clock += latency;
            }
        }

        System.out.println("@t=" + clock + ", all processes complete");
        System.out.println("Completed in " + clock + " cycles.");


        // ----------------- fcfs -----------------
        clock = 0;
        contextSwitch = 1;

        System.out.println("\nFCFS:");

        processes.sort(Comparator.comparing(Process::getArrivalTime));

        for (int i = 0; i < processes.size(); i++) {
            System.out.println("@t=" + clock + ", " + processes.get(i).toString());

            clock += processes.get(i).getBurstTime();

            if (i + 1 != processes.size()) {
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

        List<Process> sjfProcesses =  processes;

        // print out the first process then remove it
        sjfProcesses.sort(Comparator.comparing(Process::getArrivalTime));
        System.out.println("@t=" + clock + ", " + processes.get(0).toString());
        clock += processes.get(0).getBurstTime();
        System.out.println("@t=" + clock + ", context switch " + contextSwitch + " occurs");
        clock += 2;
        sjfProcesses.remove(0);

        // sort the rest by their burst time
        sjfProcesses.sort(Comparator.comparing(Process::getBurstTime));

        for (int i = 0; i <sjfProcesses.size(); i++) {
            System.out.println("@t=" + clock + ", " + sjfProcesses.get(i).toString());

            clock += sjfProcesses.get(i).getBurstTime();

            if (i + 1 != sjfProcesses.size()) {
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

        List<Process> rrProcesses = processes;

        rrProcesses.sort(Comparator.comparing(Process::getId)); // sort the processes by id

        int processCounter = 1;

        Iterator<Process> iter = rrProcesses.iterator();

//        while (iter.hasNext()) {
//            for (Process process : rrProcesses) {
//                if (process.getBurstTime() <= quantum) {
//                    System.out.println("@t=" + clock + ", selected for " + process.getBurstTime() + " units");
//                    clock += process.getBurstTime();
//                    iter.remove();
//                } else {
//                    System.out.println("@t=" + clock + ", selected for " + quantum + " units");
//                    clock += quantum;
//                    process.setBurstTime(process.burstTime - quantum);
//                }
//
//                if (processCounter != rrProcesses.size()) {
//                    System.out.println("@t=" + clock + ", context switch " + contextSwitch + " occurs");
//                    contextSwitch++;
//                    clock += latency;
//                }
//            }
//        }

        while (iter.hasNext()) {
            Process process = iter.next();
            if (process.getBurstTime() <= quantum) {
                System.out.println("@t=" + clock + ", selected for " + process.getBurstTime() + " units");
                clock += process.getBurstTime();
                iter.remove();
            } else {
                System.out.println("@t=" + clock + ", selected for " + quantum + " units");
                clock += quantum;
                process.setBurstTime(process.burstTime - quantum);
            }

            if (processCounter != rrProcesses.size()) {
                System.out.println("@t=" + clock + ", context switch " + contextSwitch + " occurs");
                contextSwitch++;
                clock += latency;
            }
        }
    }
}
