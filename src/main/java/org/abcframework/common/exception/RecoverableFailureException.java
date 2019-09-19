package org.abcframework.common.exception;

@SuppressWarnings("serial")
public class RecoverableFailureException extends AbcApplicationException {

  public RecoverableFailureException(String code, String message, Throwable cause) {
    super(code, message, cause);
  }

  public RecoverableFailureException(String message, Throwable cause) {
    super(message, cause);
  }

  public RecoverableFailureException(String code, String message) {
    super(code, message);
  }

  public RecoverableFailureException(String message) {
    super(message);
  }
}
