package ntnu.idata2305.group3.assignment3;

import java.util.Scanner;

public class FCFS {
    public static void main(String args[]) {

        System.out.println("Enter How many processes you want: ");
        Scanner input = new Scanner(System.in);
        int numbersNeeded = input.nextInt();

        int processID[] = new int[numbersNeeded];
        int arrivalTime[] = new int[numbersNeeded];
        int burstTime[] = new int[numbersNeeded];
        int waitingTime[] = new int[numbersNeeded];
        int turnaroundTime[] = new int[numbersNeeded];

        for(int i = 0; i < numbersNeeded; i++) {
            System.out.println("Enter Arrival Time for process " + (i+1) + ": ");
            arrivalTime[i] = input.nextInt();
            System.out.println("Enter Burst Time for process "  + (i+1) + ": ");
            burstTime[i] = input.nextInt();
            processID[i] = i+1;
        }


        for(int i = 0; i < numbersNeeded; i++) {
            System.out.println("Process ID: " + processID[i] + " Arrival Time: " + arrivalTime[i] + " Burst Time: " + burstTime[i] +
                    " Waiting Time: " + waitingTime[i] + " Turnaround Time: " + turnaroundTime[i]);
        }
    }
}


