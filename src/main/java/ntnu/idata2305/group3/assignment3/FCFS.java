package ntnu.idata2305.group3.assignment3;

import java.util.Scanner;

public class FCFS {
    public static void main(String args[]) {

        //gets inputs for how many processes is wished for.
        System.out.println("Enter How many processes you want: ");
        Scanner input = new Scanner(System.in);
        int numbersNeeded = input.nextInt();

        //values needed for algo.
        int processID[] = new int[numbersNeeded];
        int arrivalTime[] = new int[numbersNeeded];
        int burstTime[] = new int[numbersNeeded];
        int completionTime[] = new int[numbersNeeded];
        int turnaroundTime[] = new int[numbersNeeded];
        int waitingTime[] = new int[numbersNeeded];
        int currentTime = 0;

        //gets inputs for arrival time and burst time.
        for(int i = 0; i < numbersNeeded; i++) {
            System.out.println("Enter Arrival Time for process " + (i+1) + ": ");
            arrivalTime[i] = input.nextInt();
            System.out.println("Enter Burst Time for process "  + (i+1) + ": ");
            burstTime[i] = input.nextInt();
            processID[i] = i+1;
        }

        // sorts by arrival time and swaps processes if needed.
        for(int i = 0; i < numbersNeeded - 1; i++) {
            for(int j = i + 1; j < numbersNeeded; j++) {
                if(arrivalTime[i] > arrivalTime[j]) {

                    // swaps the arrival times.
                    int temp = arrivalTime[i];
                    arrivalTime[i] = arrivalTime[j];
                    arrivalTime[j] = temp;

                    // swaps the burst times.
                    temp = burstTime[i];
                    burstTime[i] = burstTime[j];
                    burstTime[j] = temp;

                    // swaps the id's.
                    temp = processID[i];
                    processID[i] = processID[j];
                    processID[j] = temp;
                }
            }
        }

        // calculates completion time, turnaround time and waiting time.
        for(int i = 0; i < numbersNeeded; i++) {
            if(currentTime < arrivalTime[i]) {
                currentTime = arrivalTime[i];
            }
            completionTime[i] = currentTime + burstTime[i];
            turnaroundTime[i] = completionTime[i] - arrivalTime[i];
            waitingTime[i] = turnaroundTime[i] - burstTime[i];
            currentTime = completionTime[i];
        }

        // calculates average turnaround time and waiting time.
        double avgTurnaroundTime = 0;
        double avgWaitingTime = 0;

        for(int i = 0; i < numbersNeeded; i++) {
            avgTurnaroundTime += turnaroundTime[i];
            avgWaitingTime += waitingTime[i];
        }

        avgWaitingTime /= numbersNeeded;
        avgTurnaroundTime /= numbersNeeded;

        // prints out all relevant values.
        for(int i = 0; i < numbersNeeded; i++) {
            System.out.println("Process ID: " + processID[i] + " Arrival Time: " + arrivalTime[i] + " Burst Time: " + burstTime[i] +
                    " Completion Time: " + completionTime[i] + " Turnaround Time: " + turnaroundTime[i] + " Waiting Time: " + waitingTime[i] );
            System.out.println("Average Turnaround Time: " + avgTurnaroundTime + " Average Waiting Time: " + avgWaitingTime);
        }
    }
}


