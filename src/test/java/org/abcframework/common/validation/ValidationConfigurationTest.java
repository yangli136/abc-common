package org.abcframework.common.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ValidatableService.class, TestApplicationContext.class})
public class ValidationConfigurationTest {
  @Autowired private ValidatableService service;

  @Test
  public void testValidInput() {
    assertThrows(
        ConstraintViolationException.class,
        () -> {
          final ValidatableObject object = new ValidatableObject(12, "123");
          service.processValidatableObject(object);
        });
  }

  @Test
  public void testValidList() {
    assertThrows(
        ConstraintViolationException.class,
        () -> {
          final ValidatableObject object1 = new ValidatableObject(1, "1.2.3.4");
          final ValidatableObject object2 = new ValidatableObject(1, "1.2");
          final ValidatableObject object3 = new ValidatableObject(12, "1.2.3.4");
          final ValidatableObject object4 = new ValidatableObject(20, "1.2.3.");
          final List<ValidatableObject> objects = new ArrayList<>();
          objects.add(object1);
          objects.add(object2);
          objects.add(object3);
          objects.add(object4);
          service.processValidatableList(objects);
        });
  }

  @Test
  public void testInvalidInput() {
    assertThrows(
        ConstraintViolationException.class,
        () -> {
          service.processValidatableInput(null);
        });
  }
}
