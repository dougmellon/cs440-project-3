package com.company;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Random;

public class Main {

    static long seed;
    static int numProcesses;
    static int arrivalTime;
    static int maxBurstTime;
    static int quantum;
    static int latency;

    static HashMap<Integer, Process> processes = new HashMap<>();

    static Random randNum;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); // create the new scanner

        // take in the seed value
        System.out.print("Enter a seed value: ");
        seed = scanner.nextLong();

        // take in the number of processes
        System.out.print("Enter number of processes (2, 100): ");
        do {
            numProcesses = scanner.nextInt();

            if (numProcesses < 2 || numProcesses > 100) {
                System.out.println("--- Error: Enter a valid number between 2 and 100 (inclusive)");
                System.out.print("Enter number of processes (2, 100): ");
            }

        } while (numProcesses < 2 || numProcesses > 100);

        // take in the arrival time
        System.out.print("Enter last possible arrival time (0, 99): ");
        do {
            arrivalTime = scanner.nextInt();

            if (arrivalTime < 0 || arrivalTime > 99) {
                System.out.println("--- Error: Enter a valid number between 0 and 99 (inclusive)");
                System.out.print("Enter last possible arrival time (0, 99): ");
            }

        } while (arrivalTime < 0 || arrivalTime > 99);

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
        for (int i = 0; i < numProcesses; i++) {
            randNum = new Random();
            randNum.setSeed(seed); // get random value with randNum.nextInt();


            Process process = new Process(
                    numProcesses,
                    arrivalTime,
                    maxBurstTime,
                    quantum,
                    latency
            );

            processes.put(i, process);

        }

        // output created processes
        System.out.printf("\n%s Processes created.", processes.size());
        System.out.printf("\n%-5s %-10s %s%n", "P", "Arrival", "Burst");

        processes.forEach((key, value) -> {
            System.out.println();
        });

    }
}
