package ntnu.idata2305.group3.assignment3;

import java.util.ArrayList;
import java.util.List;

public class IO extends Thread{

  private final List<Task> tasks;
  private final CPUScheduler scheduler;

  public IO(CPUScheduler scheduler) {
    this.tasks = new ArrayList<>();
    this.scheduler = scheduler;
  }

  @Override
  public void run() {
    while (true);
  }

  public synchronized void work() {
    try {
      sleep(5000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public synchronized void addTask(Task task) {
    if (task == null) {
      throw new IllegalArgumentException("Null is not allowed as a process");
    }
    this.tasks.add(task);
  }

  public boolean pushTask(int index) {
    boolean success = false;
    if (index > 0 || index < this.tasks.size()) {
      this.scheduler.queueProcess(this.tasks.get(index));
      success = true;
    }
    return success;
  }
}
