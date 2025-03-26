package ntnu.idata2305.group3.assignment3.schedulerunners;

import ntnu.idata2305.group3.assignment3.CPU;
import ntnu.idata2305.group3.assignment3.CPUScheduler;
import ntnu.idata2305.group3.assignment3.IO;
import ntnu.idata2305.group3.assignment3.ProcessCodes;
import ntnu.idata2305.group3.assignment3.Task;

public class PEPSchedulingRunner {
  public static void main(String[] args) {
    ProcessCodes processCodes = new ProcessCodes();

    CPU cpu = new CPU(processCodes);

    CPUScheduler scheduler = new CPUScheduler(cpu);
    Thread schedulerThread = new Thread(scheduler);


    IO io0 = new IO(scheduler);
    IO io1 = new IO(scheduler);
    IO io2 = new IO(scheduler);
    IO io3 = new IO(scheduler);
    IO io4 = new IO(scheduler);

    cpu.start();
    schedulerThread.start();


    processCodes.putProcess(0, cpu::shutdown);
    processCodes.putProcess(1, () -> {
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      return -1;
    });
    processCodes.putProcess(2, () -> {
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      return -1;
    });
    processCodes.putProcess(3, () -> {
      try {
        Thread.sleep(1500);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      return 5;
    });
    processCodes.putProcess(4, () -> {
      try {
        Thread.sleep(2500);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      return 6;
    });
    processCodes.putProcess(5, () -> {
      io3.work();
      return -1;
    });
    processCodes.putProcess(6, () -> {
      io4.work();
      return -1;
    });

    Task task0 = new Task(7, processCodes.getProcess(0));
    Task task1 = new Task(8, processCodes.getProcess(1));
    Task task2 = new Task(6, processCodes.getProcess(2));
    Task task3 = new Task(5, processCodes.getProcess(3));
    Task task4 = new Task(4, processCodes.getProcess(4));

    io0.addTask(task0);
    io1.addTask(task1);
    io2.addTask(task2);
    io3.addTask(task3);
    io4.addTask(task4);

    io0.start();
    io1.start();
    io2.start();
    io3.start();
    io4.start();
  }
}
