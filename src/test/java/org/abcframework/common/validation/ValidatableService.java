package org.abcframework.common.validation;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ValidatableService {

  public void processValidatableObject(@Valid ValidatableObject object) {
    // do nohting.
  }

  public void processValidatableInput(@NotNull Object object) {
    // do nohting.
  }

  public void processValidatableList(@Valid List<ValidatableObject> objects) {
    // do nohting.
  }
}
