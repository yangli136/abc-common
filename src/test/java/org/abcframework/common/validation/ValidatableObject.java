package org.abcframework.common.validation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class ValidatableObject {

  @Min(1)
  @Max(10)
  private final int numberBetween1n10;

  @Pattern(regexp = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$")
  private final String ipAddress;

  public ValidatableObject(int numberBetween1n10, String ipAddress) {
    this.numberBetween1n10 = numberBetween1n10;
    this.ipAddress = ipAddress;
  }

  @Override
  public String toString() {
    return "ValidatableObject [numberBetween1n10="
        + numberBetween1n10
        + ", ipAddress="
        + ipAddress
        + "]";
  }
}
