package pro.sky.hw23.servises;

import org.springframework.stereotype.Service;
import pro.sky.hw23.domain.Employee;
import pro.sky.hw23.exeptions.EmployeeAlreadyAddedException;
import pro.sky.hw23.exeptions.EmployeeNotFoundException;
import pro.sky.hw23.exeptions.EmployeeStorageIsFullException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class ArrayEmployeeService implements EmployeeService {
    private static final int CAPACITY = 10;

    Employee[] stuff = new Employee[CAPACITY];

    private int curetntSize = 0;
    private String lastName;


    @Override
    public Employee addEmployee(String firstName, String lastName,int department, int salary) {
        if (curetntSize >= CAPACITY) {
            throw new EmployeeStorageIsFullException();
        }
        Employee temp = new Employee(firstName,lastName, department, salary);
        for (Employee emp : stuff) {
            if (Objects.equals(emp, temp))
                throw new EmployeeAlreadyAddedException();
        }
        stuff[curetntSize] = temp;
        curetntSize++;
        return temp;
    }


    @Override
    public Employee removeEmployee(String firstName, String lastName,int department, int salary) {
        Employee temp = new Employee(firstName, lastName,department,salary);
        int i;
        for (i = 0; i < curetntSize; ++i){
            if (stuff[i].equals(temp))
                break;
        }
        if (i == curetntSize) {
            throw new  EmployeeNotFoundException();
        }
        for (int j = i; j < curetntSize - 1; ++i) {
            stuff[j] = stuff[j - 1];
        }
        curetntSize--;
        return temp;
    }

    @Override
    public Employee findEmployee(String firstName, String lastName,int department, int salary) {
        Employee temp = new Employee(firstName, lastName,department,salary);
        for (var emp : stuff) {
            if (Objects.equals(emp, temp)) return  emp;
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public List<Employee>getAll(){return new ArrayList<>(List.of(stuff));}
}
