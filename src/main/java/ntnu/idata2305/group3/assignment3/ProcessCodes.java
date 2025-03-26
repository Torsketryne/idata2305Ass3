package ntnu.idata2305.group3.assignment3;

import java.util.HashMap;

public class ProcessCodes {

  private final HashMap<Integer, Process> mapOfProcesses;

  public ProcessCodes() {
    this.mapOfProcesses = new HashMap<>();
  }

  public void putProcess(int code, Process process) {
    if (code > 0) {
      this.mapOfProcesses.put(code, process);
    }
  }

  public Process getProcess(int code) {
    return this.mapOfProcesses.get(code);
  }
}
