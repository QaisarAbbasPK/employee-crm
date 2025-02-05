package com.qaisarabbas.employee.controller;

import com.qaisarabbas.employee.model.EmployeeDTO;
import com.qaisarabbas.employee.service.EmployeeService;
import com.qaisarabbas.employee.util.WebUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employee/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("employee") final EmployeeDTO employeeDTO) {
        return "employee/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("employee") @Valid final EmployeeDTO employeeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "employee/add";
        }
        employeeService.create(employeeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("employee.create.success"));
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("employee", employeeService.get(id));
        return "employee/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("employee") @Valid final EmployeeDTO employeeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "employee/edit";
        }
        employeeService.update(id, employeeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("employee.update.success"));
        return "redirect:/employees";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        employeeService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("employee.delete.success"));
        return "redirect:/employees";
    }

}
