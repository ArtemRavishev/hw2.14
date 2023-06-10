package pro.sky.hw23.controllers;

import org.springframework.web.bind.annotation.*;
import pro.sky.hw23.domain.Employee;
import pro.sky.hw23.servises.DepartmentService;
import pro.sky.hw23.domain.Employee;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/{id}/employees")
    public List<Employee> printAllEmployeesFromDepartment(@PathVariable int id) {
        return departmentService.printAllEmployeesFromDepartment(id);
    }

    @GetMapping("/{id}/salary/sum")
    public double findSumSalaryEmployeesFromDepartment(@PathVariable int id) {
        return departmentService.findSumSalaryEmployeesFromDepartment(id);
    }

    @GetMapping("/{id}/salary/max")
    public double findEmployeeWithMaxSalaryFromDepartment(@PathVariable int id) {
        return departmentService.findEmployeeWithMaxSalaryFromDepartment(id);

    }
    @GetMapping("/{id}/salary/min")
    public double findEmployeeWithMinSalaryFromDepartment(@PathVariable int id) {
        return departmentService.findEmployeeWithMinSalaryFromDepartment(id);
    }


    @GetMapping("/employees")
    public Map<Integer, List<Employee>> findEmployees()  {
        return departmentService.printEmployeeByDepartment();
    }
}
