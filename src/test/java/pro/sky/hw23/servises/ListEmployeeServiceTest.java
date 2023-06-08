package pro.sky.hw23.servises;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.sky.hw23.domain.Employee;
import pro.sky.hw23.exeptions.*;

import java.util.stream.Stream;

public class ListEmployeeServiceTest {

    private final ListEmployeeService listEmployeeService = new ListEmployeeService(new ValidatorService());

    public static Stream<Arguments> addWithIncorrectFirstNameTestParams() {
        return Stream.of(
                Arguments.of("Артём1"),
                Arguments.of("Артём!"),
                Arguments.of("Артём@")
        );
    }
    public static Stream<Arguments> addWithIncorrectLastNameTestParams() {
        return Stream.of(
                Arguments.of("Равишев1"),
                Arguments.of("Равишев!"),
                Arguments.of("Равишев@")
        );
    }

    @BeforeEach
    public void beforeEach() {
        listEmployeeService.addEmployee("Артём", "Равишев", 1, 40_000);
        listEmployeeService.addEmployee("Илья", "Ивано", 1, 80_000);
        listEmployeeService.addEmployee("Андрей", "Пирязев", 1, 40_000);
    }

    @AfterEach
    public void afterEach() {
        listEmployeeService.getAll().forEach(employee ->listEmployeeService.removeEmployee(employee.getFirstName(), employee.getLastName(), employee.getDepartment(), employee.getSalary()));
    }
    @Test
    public void addTest(){
        int beforeCount = listEmployeeService.getAll().size();
        Employee expected = new Employee("Artem", "Ravishev", 1, 40_000);

        Assertions.assertThat(listEmployeeService.addEmployee("Artem", "Ravishev", 1, 40_000))
                .isEqualTo(expected)
                .isIn(listEmployeeService.getAll());
        Assertions.assertThat(listEmployeeService.getAll()).hasSize(beforeCount + 1);
        Assertions.assertThat(listEmployeeService.findEmployee("Artem","Ravishev", 1, 40_000)).isEqualTo(expected);
    }
    @ParameterizedTest
    @MethodSource("addWithIncorrectFirstNameTestParams")
    public void addWithIncorrectFirstNameTest(String incorrectFirstName){
        Assertions.assertThatExceptionOfType(IncorrectNameExeption.class)
                .isThrownBy(()->listEmployeeService.addEmployee(incorrectFirstName,"Ravishev", 1, 40_000));
    }
    @ParameterizedTest
    @MethodSource("addWithIncorrectLastNameTestParams")
    public void addWithIncorrectLastNameTest(String incorrectLastName){
        Assertions.assertThatExceptionOfType(IncorrectSurnameExeption.class)
                .isThrownBy(()->listEmployeeService.addEmployee("Artem",incorrectLastName, 1, 40_000));
    }
    @Test
    public void addWhenAlreadyExistsTest(){
        Assertions.assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(()->listEmployeeService.addEmployee("Артём", "Равишев", 1, 40_000));
    }
    @Test
    public void addWhenStorageIsFullTest(){
        Stream.iterate(1, i ->  i + 1)
                .limit(7)
                .map(number -> new Employee
                        ("Артём"+((char)('a'+number)),
                         "Равишев"+((char)('a'+number)),
                        number,
                        40_000 + number
                        )
                )
                        .forEach(employee ->
                                listEmployeeService.addEmployee(
                                employee.getFirstName(),
                                employee.getLastName(),
                                employee.getDepartment(),
                                employee.getSalary()));

        Assertions.assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(()->listEmployeeService.addEmployee("Артём", "Равишев", 1, 40_000));
    }
    @Test
    public void removeTest(){
        int beforeCount = listEmployeeService.getAll().size();
        Employee expected = new Employee("Артём", "Равишев", 1, 40_000);

        Assertions.assertThat(listEmployeeService.removeEmployee("Артём", "Равишев", 1, 40_000))
                .isEqualTo(expected)
                .isNotIn(listEmployeeService.getAll());
        Assertions.assertThat(listEmployeeService.getAll()).hasSize(beforeCount - 1);
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class).isThrownBy(() -> listEmployeeService.findEmployee("Артём", "Равишев", 1, 40_000));
    }
    @Test
    public void removeWhenNotFoundTest(){

        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> listEmployeeService.removeEmployee("Антон", "Шантон", 1, 40_000));
    }
    @Test
    public void findTest(){
        int beforeCount = listEmployeeService.getAll().size();
        Employee expected = new Employee("Артём", "Равишев", 1, 40_000);

        Assertions.assertThat(listEmployeeService.findEmployee("Артём", "Равишев", 1, 40_000))
                .isEqualTo(expected)
                .isIn(listEmployeeService.getAll());
        Assertions.assertThat(listEmployeeService.getAll()).hasSize(beforeCount);
    }
    @Test
    public void findWhenNotFoundTest(){

        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> listEmployeeService.findEmployee("Антон", "Шантон", 1, 40_000));
    }
    @Test
    public void getAllTest(){

        Assertions.assertThat(listEmployeeService.getAll())
                .hasSize(3)
                .containsExactlyInAnyOrder(
                        new Employee("Артём", "Равишев", 1, 40_000),
                        new Employee("Илья", "Ивано", 1, 80_000),
                        new Employee("Андрей", "Пирязев", 1, 40_000)
                );
    }

}

