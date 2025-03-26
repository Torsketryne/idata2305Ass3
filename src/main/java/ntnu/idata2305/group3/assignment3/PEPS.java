package ntnu.idata2305.group3.assignment3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;

public class PEPS {
  public static void main(String args[]) {

    //gets inputs for how many processes is wished for.
    System.out.println("Enter how many processes you want: ");
    Scanner input = new Scanner(System.in);
    int numbersNeeded = input.nextInt();

    //User input
    PriorityQueue<Process> queue = new PriorityQueue<>(Comparator
        .comparingInt(Process::getArrivalTime)
        .thenComparingInt(Process::getPriority)
        .thenComparingInt(Process::getId));

    int arrivalTime;
    int burstTime;
    int priority;
    int processID;

    //gets inputs for arrival time and burst time.
    for(int i = 0; i < numbersNeeded; i++) {
      System.out.println("Enter Arrival Time for process " + (i+1) + ": ");
      arrivalTime = input.nextInt();
      System.out.println("Enter Burst Time for process "  + (i+1) + ": ");
      burstTime = input.nextInt();
      System.out.println("Enter Priority for process "  + (i+1) + ": ");
      priority = input.nextInt();
      processID = i + 1;
      queue.offer(new Process(processID, arrivalTime, burstTime, priority));
    }


    int completionTime = 0;
    int processDelay = 0;


    List<Integer> turnaboutTime = new ArrayList<>();
    List<Integer> waitingTime = new ArrayList<>();
    List<Process> interruptedProcesses = new ArrayList<>();
    Map<Integer, Integer> pauseStart = new HashMap<>();



    System.out.println("Scheduling: ");
    System.out.println("Starting at time: " + completionTime + "\n");

    if (queue.isEmpty()) {
      throw new RuntimeException("Queue of processes should not be empty here");
    }

    Process currentProcess = queue.poll();
    completionTime = currentProcess.getArrivalTime();
    System.out.println("Time moved to " + completionTime + ", starting with process: " + currentProcess.getId());
    if (queue.isEmpty()) {
      System.out.println("There was only 1 process\n");
      completionTime = currentProcess.getArrivalTime() + currentProcess.getBurstTime();
      waitingTime.add(currentProcess.getBurstTime());
      turnaboutTime.add(completionTime - currentProcess.getArrivalTime());
    } else {

      Process nextProcess = queue.poll();
      while (!queue.isEmpty()) {

        if (currentProcess.getRemainingBurst() + completionTime < nextProcess.getArrivalTime()) {
          System.out.println("Process " + currentProcess.getId() + " were completed at " + completionTime + currentProcess.getRemainingBurst() + ", before the next task");
          if (pauseStart.containsKey(currentProcess.getId())) {
            int waitedTime = completionTime - pauseStart.get(currentProcess.getId());
            waitingTime.add(waitedTime);
            System.out.println("Process has waited: " + waitedTime);
          } else {
            System.out.println("Process has not waited");
          }
          completionTime += currentProcess.getRemainingBurst();
          int turnedTime = completionTime - currentProcess.getArrivalTime();
          turnaboutTime.add(turnedTime);
          System.out.println("Process' turnabout time is: " + turnedTime + "\n");


        } else if (currentProcess.getPriority() > nextProcess.getPriority()) {
          System.out.println("Next process came before current process were finished");
          System.out.println("Next process has higher priority so current process becomes paused");
          completionTime = nextProcess.getArrivalTime() + processDelay;
          if (!pauseStart.containsKey(currentProcess.getId())) {
            pauseStart.put(currentProcess.getId(), nextProcess.getArrivalTime());
          }
          int turnedSoFar = nextProcess.getArrivalTime() - currentProcess.getArrivalTime();
          turnaboutTime.add(turnedSoFar);
          System.out.println(turnedSoFar + " have passed so far for process " + currentProcess.getId());
          currentProcess.subtractBurst(currentProcess.getBurstTime() - turnedSoFar);
          System.out.println(currentProcess.getRemainingBurst() + " remains for " + currentProcess.getId() + " to do");
          interruptedProcesses.addLast(currentProcess);
          System.out.println("Process " + nextProcess.getId() + " is put on wait list at time: " + pauseStart.get(currentProcess.getId()) + "\n");

        } else {
          System.out.println("Next process came before current process were finished");
          System.out.println("Current process has higher or equal priority so next process becomes paused");
          if (!pauseStart.containsKey(nextProcess.getId())) {
            pauseStart.put(nextProcess.getId(), (currentProcess.getRemainingBurst() + completionTime) - nextProcess.getArrivalTime());
          }
          interruptedProcesses.addLast(nextProcess);
          System.out.println("Process " + nextProcess.getId() + " is put on wait list at time: " + completionTime + "\n");

          System.out.println("Process " + currentProcess.getId() + " were completed at " + completionTime + currentProcess.getRemainingBurst() + ", before the next task");
          if (pauseStart.containsKey(currentProcess.getId())) {
            int waitedTime = completionTime - pauseStart.get(currentProcess.getId());
            waitingTime.add(waitedTime);
            System.out.println("Process has waited: " + waitedTime);
          } else {
            System.out.println("Process has not waited");
          }
          completionTime += currentProcess.getRemainingBurst();
          int turnedTime = completionTime - currentProcess.getArrivalTime();
          turnaboutTime.add(turnedTime);
          System.out.println("Process' turnabout time is: " + turnedTime + "\n");
        }

        if (interruptedProcesses.isEmpty()) {
          currentProcess = nextProcess;
          nextProcess = queue.poll();
        } else {
          currentProcess = interruptedProcesses.removeLast();

        }
      }
    }

    int totalWaitingTime = waitingTime.stream().mapToInt(Integer::intValue).sum();
    int totalTurnaroundTime = turnaboutTime.stream().mapToInt(Integer::intValue).sum();

    System.out.println("Completion time: " + completionTime);
    System.out.println("\nAverage Waiting Time = " + (double) totalWaitingTime / waitingTime.size());
    System.out.println("Average Turnaround Time = " + (double) totalTurnaroundTime / turnaboutTime.size());
  }
}
