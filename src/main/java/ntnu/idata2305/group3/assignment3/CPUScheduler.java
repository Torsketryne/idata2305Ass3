package ntnu.idata2305.group3.assignment3;

import java.util.LinkedList;
import java.util.Queue;

public class CPUScheduler {

  private final Queue<IO> processQueue;

  public CPUScheduler() {
    this.processQueue = new LinkedList<>();
  }

  public void queueProcess(IO io) {
    this.processQueue.offer(io);
  }

  public IO getProcess() {
    return this.processQueue.poll();
  }
}
