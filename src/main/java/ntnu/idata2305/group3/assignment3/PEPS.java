package ntnu.idata2305.group3.assignment3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class PEPS {
  public static void main(String args[]) {

    //gets inputs for how many processes is wished for.
    System.out.println("Enter how many processes you want: ");
    Scanner input = new Scanner(System.in);
    int numbersNeeded = input.nextInt();

    //User input


    //gets inputs for arrival time and burst time.
    List<Process> arrivals = new ArrayList<>();
    for(int i = 0; i < numbersNeeded; i++) {
      System.out.println("Enter Arrival Time for process " + (i+1) + ": ");
      int arrivalTime = input.nextInt();
      System.out.println("Enter Burst Time for process "  + (i+1) + ": ");
      int burstTime = input.nextInt();
      System.out.println("Enter Priority for process "  + (i+1) + ": ");
      int priority = input.nextInt();
      arrivals.add(new Process(i + 1, arrivalTime, burstTime, priority));
    }


    // Sort processes based on arrival time
    arrivals.sort(Comparator.comparingInt(Process::getArrivalTime));

    // Ready queue sorted by priority (lower value means higher priority)
    PriorityQueue<Process> readyQueue = new PriorityQueue<>(
        Comparator.comparingInt(Process::getPriority)
            .thenComparingInt(Process::getArrivalTime)
            .thenComparingInt(Process::getId));

    int currentTime = 0;
    int processIndex = 0;
    List<Process> finishedProcesses = new ArrayList<>();

    System.out.println("\n--- Scheduling Simulation ---");

    while (processIndex < arrivals.size() || !readyQueue.isEmpty()) {
      // Add arriving processes to ready queue
      while (processIndex < arrivals.size() && arrivals.get(processIndex).getArrivalTime() <= currentTime) {
        Process process = arrivals.get(processIndex);
        readyQueue.offer(process);
        System.out.println("Time " + currentTime + ": Process " + process.getId() + " has arrived and is added to the ready queue.");
        processIndex++;
      }

      // If no process is in the queue, move time forward to the next arrival
      if (readyQueue.isEmpty()) {
        if (processIndex < arrivals.size()) {
          currentTime = arrivals.get(processIndex).getArrivalTime();
        }
        continue;
      }

      // Pick the highest priority process
      Process current = readyQueue.poll();
      System.out.println("Time " + currentTime + ": Process " + current.getId() + " is selected to run.");

      int finishTimeIfRunToCompletion = currentTime + current.getRemainingBurst();
      int nextArrivalTime = processIndex < arrivals.size() ? arrivals.get(processIndex).getArrivalTime() : Integer.MAX_VALUE;

      if (finishTimeIfRunToCompletion <= nextArrivalTime) {
        // Process completes execution
        currentTime = finishTimeIfRunToCompletion;
        current.setCompletionTime(currentTime);
        finishedProcesses.add(current);
        System.out.println("Time " + currentTime + ": Process " + current.getId() + " completes execution.");
      } else {
        // Preemption: A higher-priority process arrives before completion
        int timeRun = nextArrivalTime - currentTime;
        current.subtractBurst(timeRun);
        currentTime = nextArrivalTime;
        System.out.println("Time " + currentTime + ": Process " + current.getId() + " is preempted after running for " + timeRun + " time units. Remaining burst: " + current.getRemainingBurst());
        readyQueue.offer(current);
      }
    }

    int totalWaiting = 0;
    int totalTurnaround = 0;
    System.out.println("\n--- Process Completion Times ---");

    // Sort processes by ID before displaying completion times
    finishedProcesses.sort(Comparator.comparingInt(Process::getId));

    for (Process process : finishedProcesses) {
      int turnaroundTime = process.getCompletionTime() - process.getArrivalTime();
      int waitingTime = turnaroundTime - process.getBurstTime();
      totalTurnaround += turnaroundTime;
      totalWaiting += waitingTime;
      System.out.println("Process " + process.getId() + ": Turnaround Time = " + turnaroundTime + ", Waiting Time = " + waitingTime);
    }

    System.out.println("\nAverage Turnaround Time = " + (double) totalTurnaround / numbersNeeded);
    System.out.println("Average Waiting Time = " + (double) totalWaiting / numbersNeeded);
  }
}
