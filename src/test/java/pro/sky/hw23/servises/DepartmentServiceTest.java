package pro.sky.hw23.servises;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.hw23.domain.Employee;
import pro.sky.hw23.exeptions.EmployeeNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;

    private List<Employee> employees;

    public static Stream<Arguments> findEmployeeWithMaxSalaryFromDepartmentTestParams() {
        return Stream.of(
                Arguments.of(1, 70_000),
                Arguments.of(2, 90_000),
                Arguments.of(3, 70_000)
        );
    }

    public static Stream<Arguments> findEmployeeWithMinSalaryFromDepartmentTestParams() {
        return Stream.of(
                Arguments.of(1, 40_000),
                Arguments.of(2, 50_000),
                Arguments.of(3, 70_000)
        );
    }
    public static Stream<Arguments> findSumSalaryEmployeesFromDepartmentTestParams() {
        return Stream.of(
                Arguments.of(1, 110_000),
                Arguments.of(2, 140_000),
                Arguments.of(3, 70_000)
        );
    }


    public static Stream<Arguments>  printAllEmployeesFromDepartmentTestParams() {
        return Stream.of(
                Arguments.of(1,
                        List.of(new Employee("Артём","Равишев",1,40_000),
                                new Employee("Сергей","Белый",1,70_000))),
                Arguments.of(2,
                        List.of(new Employee("Ангилина","Иванова",2,50_000),
                                new Employee("Алексей","Черников",2,90_000))),
                Arguments.of(3,
                       Collections.singletonList(new Employee("Анна", "Петровна", 3, 70_000))),

        Arguments.of(
                4,
                Collections.emptyList()
        ));
    }

    @BeforeEach
    public void beforeEach() {
        employees=List.of(
                new Employee("Артём","Равишев",1,40_000),
                new Employee("Ангилина","Иванова",2,50_000),
                new Employee("Алексей","Черников",2,90_000),
                new Employee("Анна","Петровна",3,70_000),
                new Employee("Сергей","Белый",1,70_000)
        );
        Mockito.when(employeeService.getAll()).thenReturn(employees);
    }

    @ParameterizedTest
    @MethodSource("findEmployeeWithMaxSalaryFromDepartmentTestParams")
    public void findEmployeeWithMaxSalaryFromDepartmentTest(int departmentId,double expected) {
         Assertions.assertThat(departmentService.findEmployeeWithMaxSalaryFromDepartment(departmentId))
                .isEqualTo(expected);
    }


    @ParameterizedTest
    @MethodSource("findEmployeeWithMinSalaryFromDepartmentTestParams")
    public void findEmployeeWithMinSalaryFromDepartmentTest(int departmentId,double expected) {
        Assertions.assertThat(departmentService.findEmployeeWithMinSalaryFromDepartment(departmentId))
                .isEqualTo(expected);
    }
    @ParameterizedTest
    @MethodSource("findSumSalaryEmployeesFromDepartmentTestParams")
    public void findSumSalaryEmployeesFromDepartmentTest(int departmentId,double expected) {
        Assertions.assertThat(departmentService.findSumSalaryEmployeesFromDepartment(departmentId))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("printAllEmployeesFromDepartmentTestParams")
    public void printAllEmployeesFromDepartmentTest(int departmentId,List<Employee> expected) {
        Assertions.assertThat(departmentService.printAllEmployeesFromDepartment(departmentId))
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void printEmployeeByDepartmentTest() {
        Map<Integer,List<Employee>> expected = Map.of(1,
                List.of(
                        new Employee("Артём","Равишев",1,40_000),
                        new Employee("Сергей","Белый",1,70_000)
                ),
                2,
                List.of(new Employee("Ангилина","Иванова",2,50_000),
                        new Employee("Алексей","Черников",2,90_000)
                ),
        3,
                Collections.singletonList(new Employee("Анна", "Петровна", 3, 70_000))
        );

        Assertions.assertThat(departmentService.printEmployeeByDepartment())
                .containsExactlyInAnyOrderEntriesOf(expected);
    }
}
