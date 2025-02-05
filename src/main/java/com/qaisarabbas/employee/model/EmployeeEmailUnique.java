package com.qaisarabbas.employee.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.qaisarabbas.employee.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the email value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = EmployeeEmailUnique.EmployeeEmailUniqueValidator.class
)
public @interface EmployeeEmailUnique {

    String message() default "{Exists.employee.email}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class EmployeeEmailUniqueValidator implements ConstraintValidator<EmployeeEmailUnique, String> {

        private final EmployeeService employeeService;
        private final HttpServletRequest request;

        public EmployeeEmailUniqueValidator(final EmployeeService employeeService,
                final HttpServletRequest request) {
            this.employeeService = employeeService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equalsIgnoreCase(employeeService.get(Long.parseLong(currentId)).getEmail())) {
                // value hasn't changed
                return true;
            }
            return !employeeService.emailExists(value);
        }

    }

}
