//src/main/java/com/example/electricitymanagement/validation/NoNullSubstring.java
package com.example.electricitymanagement.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoNullSubstringValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoNullSubstring {
 String message() default "Field must not contain the word 'null'";
 Class<?>[] groups() default {};
 Class<? extends Payload>[] payload() default {};
}
