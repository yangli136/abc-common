package org.abcframework.common.exception;

import java.util.UUID;

@SuppressWarnings("serial")
public class AbcApplicationException extends RuntimeException {

  private final String id = UUID.randomUUID().toString();
  protected final String code;

  public AbcApplicationException(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public AbcApplicationException(String message, Throwable cause) {
    super(message, cause);
    this.code = this.id;
  }

  public AbcApplicationException(String code, String message) {
    super(message);
    this.code = code;
  }

  public AbcApplicationException(String message) {
    super(message);
    this.code = this.id;
  }

  public String getCode() {
    return code;
  }

  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return "AbcApplicationException [id=" + id + ", code=" + code + "]";
  }
}
