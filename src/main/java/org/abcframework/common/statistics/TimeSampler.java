package org.abcframework.common.statistics;

public class TimeSampler {

  private long start;
  private long end;

  public void setStart(long start) {
    this.start = start;
  }

  public void setEnd(long end) {
    this.end = end;
  }

  public double calculateEclapsedTime() {
    return this.end - this.start;
  }

  @Override
  public String toString() {
    return "TimeSample [start=" + start + ", end=" + end + "]";
  }
}
