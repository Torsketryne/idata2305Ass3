package ntnu.idata2305.group3.assignment3;

public class CPU extends Thread{

  private Task currentTask;
  private ProcessCodes processCodes;
  private boolean running;

  public CPU(ProcessCodes processCodes) {
    this.currentTask = null;
    this.processCodes = processCodes;
    this.running = true;
  }

  @Override
  public void run() {
    while (running) {
      try {
        synchronized (this) {
          if (currentTask == null) {
            System.out.println("CPU idling...");
            this.wait();
          }
        }
        if (currentTask != null) {
          System.out.println("CPU executing task with priority: " + this.currentTask.getPriority());
          int code = this.currentTask.execute();
          this.processCodes.getProcess(code).execute();
          System.out.println("Task completed");

          synchronized (this) {
            this.currentTask = null;
          }
        }

      } catch (InterruptedException e) {
        System.out.println("CPU interrupted!");
      }
    }
  }

  public synchronized void setCurrentTask(Task process) {
    this.currentTask = process;
    System.out.println("New task set for CPU. Interrupting...");
    this.interrupt();
  }

  public synchronized Task getCurrentTask() {
    return this.currentTask;
  }

  public synchronized int shutdown() {
    this.running = false;
    this.interrupt();
    return -1;
  }
}
