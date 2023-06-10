package pro.sky.hw23.servises;

import org.springframework.stereotype.Service;
import pro.sky.hw23.domain.Employee;
import pro.sky.hw23.exeptions.DepartmentNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }



    public Map<Integer, List<Employee>> printEmployeeByDepartment() {
        return employeeService.getAll().stream()
                 .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    public double findSumSalaryEmployeesFromDepartment(int department) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartment() == department)
                .mapToDouble(Employee::getSalary)
                .sum();
    }
    public double findEmployeeWithMaxSalaryFromDepartment(int department) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartment() == department)
                .mapToDouble(Employee::getSalary)
                .max()
                .orElseThrow(DepartmentNotFoundException::new);
    }
    public double findEmployeeWithMinSalaryFromDepartment(int department) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartment() == department)
                .mapToDouble(Employee::getSalary)
                .min()
                .orElseThrow(DepartmentNotFoundException::new);
    }


    public List<Employee> printAllEmployeesFromDepartment(int department) {
        return employeeService.getAll().stream().
                filter(employee -> employee.getDepartment() == department).collect(Collectors.toList());
    }
}
