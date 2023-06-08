package pro.sky.hw23.servises;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pro.sky.hw23.servises.ValidatorService;
import pro.sky.hw23.domain.Employee;
import pro.sky.hw23.exeptions.EmployeeAlreadyAddedException;
import pro.sky.hw23.exeptions.EmployeeNotFoundException;
import pro.sky.hw23.exeptions.EmployeeStorageIsFullException;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class ListEmployeeService implements EmployeeService{

    private static final int CAPACITY=10;

    List<Employee> stuff = new ArrayList<>();

    private final ValidatorService validatorService;

    public ListEmployeeService(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    @Override
    public Employee addEmployee(String firstName,
                                String lastName,
                                int department,
                                int salary) {
        Employee temp = new Employee(validatorService.validatorName(firstName),
                validatorService.validatorSurname(lastName),
                department,
                salary);
        if (stuff.size() >= CAPACITY) {
            throw new EmployeeStorageIsFullException();
        }
        if (stuff.contains(temp)) {
            throw new EmployeeAlreadyAddedException();
        }
        stuff.add(temp);
        return temp;
    }

    @Override
    public Employee removeEmployee(String firstName, String lastName,int department, int salary ) {
        int index = stuff.indexOf(new Employee(firstName, lastName,department,salary));
        if (index == -1) {
            throw new EmployeeNotFoundException();
        }
        return stuff.remove(index);
    }

    @Override
    public Employee findEmployee(String firstName, String lastName,int department, int salary) {
        int index = stuff.indexOf(new Employee(firstName, lastName,department,salary));
        if (index == -1) {
            throw new EmployeeNotFoundException();
        }
        return stuff.get(index);
    }
    public List<Employee>getAll(){return new ArrayList<>(stuff);}
}
