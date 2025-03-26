package ntnu.idata2305.group3.assignment3;

public class Task {

  private int priority;
  private Process process;

  public Task(int priority, Process process) {
    if (priority < 0) {
      throw new IllegalArgumentException("priority must be a positive number");
    }
    if (process == null) {
      throw new IllegalArgumentException("A null is not allowed as a process");
    }
    this.priority = priority;
    this.process = process;
  }

  public int execute() {
    return process.execute();
  }

  public void setPriority(int priority) {
    if (priority < 0) {
      throw new IllegalArgumentException("priority must be a positive number");
    }
    this.priority = priority;
  }

  public int getPriority() {
    return this.priority;
  }
}
