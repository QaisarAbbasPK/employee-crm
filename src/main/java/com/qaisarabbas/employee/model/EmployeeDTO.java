package com.qaisarabbas.employee.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmployeeDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @Size(max = 255)
    private String lastName;

    @NotNull
    @Size(max = 255)
    @EmployeeEmailUnique
    private String email;

    @NotNull
    @Size(max = 255)
    private String profession;

    @NotNull
    private LocalDate dataOfBirth;

    @NotNull
    @Size(max = 255)
    private String phoneNumber;

}
