package ntnu.idata2305.group3.assignment3;

import java.util.PriorityQueue;
import java.util.Queue;

public class CPUScheduler implements Runnable{

  private final Queue<Task> taskQueue;
  private final CPU cpu;

  public CPUScheduler(CPU cpu) {
    this.taskQueue = new PriorityQueue<>();
    this.cpu = cpu;
  }

  @Override
  public void run() {
    while (true) {
      if (!this.taskQueue.isEmpty()
          && (cpu.getCurrentTask() == null
          || this.taskQueue.element().getPriority() > this.cpu.getCurrentTask().getPriority())) {
        this.cpu.setCurrentTask(this.taskQueue.poll());
      }
    }
  }

  public synchronized void queueProcess(Task task) {
    this.taskQueue.offer(task);
  }
}
