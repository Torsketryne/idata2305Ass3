package ntnu.idata2305.group3.assignment3;

public class Process{

  private int id;
  private int arrivalTime;
  private int burstTime;
  private int remainingBurst;
  private int priority;

  public Process(int id, int arrivalTime, int burstTime, int priority) {
    this.id = id;
    this.arrivalTime = arrivalTime;
    this.burstTime = burstTime;
    this.priority = priority;
    this.remainingBurst = this.burstTime;
  }

  public int getId() {
    return this.id;
  }

  public int getArrivalTime() {
    return this.arrivalTime;
  }

  public int getRemainingBurst() {
    return this.remainingBurst;
  }

  public int getBurstTime() {
    return this.burstTime;
  }

  public int getPriority() {
    return this.priority;
  }

  public void setArrivalTime(int arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public void setRemainingBurst(int remainingBurst) {
    this.remainingBurst = remainingBurst;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public void subtractBurst(int subValue) {
    if (subValue > this.remainingBurst) {
      this.remainingBurst = 0;
    } else {
      this.remainingBurst -= subValue;
    }
  }
}
