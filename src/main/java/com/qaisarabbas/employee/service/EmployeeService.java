package com.qaisarabbas.employee.service;

import com.qaisarabbas.employee.domain.Employee;
import com.qaisarabbas.employee.model.EmployeeDTO;
import com.qaisarabbas.employee.repos.EmployeeRepository;
import com.qaisarabbas.employee.util.NotFoundException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public List<EmployeeDTO> findAll() {
        final List<Employee> employees = employeeRepository.findAll(Sort.by("id"));
        return employees.stream()
                .map(employee -> mapToDTO(employee, new EmployeeDTO()))
                .toList();
    }

    public EmployeeDTO get(final Long id) {
        return employeeRepository.findById(id)
                .map(employee -> mapToDTO(employee, new EmployeeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EmployeeDTO employeeDTO) {
        final Employee employee = new Employee();
        mapToEntity(employeeDTO, employee);
        return employeeRepository.save(employee).getId();
    }

    public void update(final Long id, final EmployeeDTO employeeDTO) {
        final Employee employee = employeeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(employeeDTO, employee);
        employeeRepository.save(employee);
    }

    public void delete(final Long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeDTO mapToDTO(final Employee employee, final EmployeeDTO employeeDTO) {
        employeeDTO.setId(employee.getId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setProfession(employee.getProfession());
        employeeDTO.setDataOfBirth(employee.getDataOfBirth());
        employeeDTO.setPhoneNumber(employee.getPhoneNumber());
        return employeeDTO;
    }

    private Employee mapToEntity(final EmployeeDTO employeeDTO, final Employee employee) {
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setProfession(employeeDTO.getProfession());
        employee.setDataOfBirth(employeeDTO.getDataOfBirth());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        return employee;
    }

    public boolean emailExists(final String email) {
        return employeeRepository.existsByEmailIgnoreCase(email);
    }

}
