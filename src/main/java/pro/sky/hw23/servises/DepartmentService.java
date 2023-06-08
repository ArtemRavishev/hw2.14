package pro.sky.hw23.servises;

import org.springframework.stereotype.Service;
import pro.sky.hw23.domain.Employee;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void changeDepartment(Employee employee, int newDepartmentr) {
        employeeService.getAll().stream().filter(value -> Objects.equals(employee, value)).findFirst().ifPresent(value -> value.setDepartment(newDepartmentr));
    }

    public Map<Integer, List<Employee>> printEmployeeByDepartment() {
        return employeeService.getAll().stream().collect(Collectors.groupingBy(Employee::getDepartment));
    }

    public void indexSalariesForDepartment(double index, int department) {
        employeeService.getAll().stream().filter(employee -> employee.getDepartment() == department).forEach(employee -> employee.setSalary((int) (employee.getSalary() + employee.getSalary() * index / 100)));
    }

    public double averageSalaryForDepartment(int department) {
        return employeeService.getAll().stream().
                filter(employee -> employee.getDepartment() == department).mapToInt(Employee::getSalary).average().orElse(0);

    }

    public Employee findEmployeeWithMinSalaryFromDepartment(int department) {
        return employeeService.getAll().stream().filter(employee -> employee.getDepartment() == department).min(Comparator.comparing(Employee::getSalary)).orElse(null);
    }

    public Employee findEmployeeWithMaxSalaryFromDepartment(int department) {
        return employeeService.getAll().stream().filter(employee -> employee.getDepartment() == department).max(Comparator.comparing(Employee::getSalary)).orElse(null);
    }

    public double totalSalariesForDepartment(int department) {
        return employeeService.getAll().stream().
                filter(employee -> employee.getDepartment() == department).mapToInt(Employee::getSalary).sum();
    }

    public List<Employee> printAllEmployeesFromDepartment(int department) {
        return employeeService.getAll().stream().
                filter(employee -> employee.getDepartment() == department).collect(Collectors.toList());
    }
}
