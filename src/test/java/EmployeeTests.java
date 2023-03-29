import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import static org.hamcrest.Matchers.hasToString;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.hamcrest.object.HasToString.hasToString;


public class EmployeeTests {
    private static long generalStartTime;
    private long testStartTime;

    Employee employee1, employee2;   // System under tests
    List<Employee> employees, emptyEmployees;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Employee tests started");
        generalStartTime = System.nanoTime();
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("new Employee Test started");
        testStartTime = System.nanoTime();
        employee1 = new Employee(1,"John","Smith","USA",25);
        employee2 = new Employee(2,"Inav","Petrov","RU",23);
        employees = List.of(employee1, employee2);
        emptyEmployees = List.of();
    }

    @AfterAll
    public static void AfterAll() {
        System.out.println("Employee tests complete: "  + (System.nanoTime() - generalStartTime));
    }

    @AfterEach
    public void AfterEach() {
        List<Employee> employees = null;
        System.out.println("Employee Test complete: " + (System.nanoTime() - testStartTime));
    }

    @Test
    public void test1_EmployeeConstructor() {
        // arrange      (given)
        final long id = 2;
        final String firstName = "Inav", lastName = "Petrov", country = "RU";
        final int age = 23;
        final Employee expected = employee2;

        // act          (when)
        final Employee result = new Employee(id,firstName,lastName,country,age);

        // assert       (then)
        assertEquals(expected, result);
    }

    @Test
    public void test2_EmployeeToString() {
        final Employee obj = employee1;
        // String expected = "\nEmployee{id='1', firstName='John', lastName='Smith', country='USA', age='25'}";

        final String result = obj.toString();

        // assertEquals(expected, result);
        assertThat(obj, hasToString(result));    // used matchers Hamcrest
    }

    @Test
    public void test3_fromCSV() {
        String fileName = "staff.csv";
        List<Employee> expected = employees;

        List<Employee> result = Employee.fromCSV(fileName);

        assertEquals(expected, result);
    }

    @Test
    public void test4_fromCSV_fileNotFound() {
        String fileName = "csv.staff";
        List<Employee> expected = emptyEmployees;

        List<Employee> result = Employee.fromCSV(fileName);

        assertEquals(expected, result);
    }

//?    @ParameterizedTest
//    @ValueSource(strings = {"staff.csv", "new.staff"})
//    public void test5_fromCSV_fileNotFound(String fileName) {
//        List<Employee> result = Employee.fromCSV(fileName);
//
//        assertTrue(Objects.deepEquals(employees, result)
//                ||  Objects.deepEquals(emptyEmployees, result));
//    }

    @Test
    public void test6_fromXML()
            throws ParserConfigurationException, IOException, SAXException {
        String fileName = "staff.xml";
        List<Employee> expected = employees;

        List<Employee> result = Employee.fromXML(fileName);

        assertEquals(expected, result);
    }

    @Test
    public void test7_fromXML_fileNotFound()
            throws ParserConfigurationException, IOException, SAXException {
        String fileName = "xml.staff";
        List<Employee> expected = emptyEmployees;

        List<Employee> result = Employee.fromXML(fileName);

        assertEquals(expected, result);
    }
}
