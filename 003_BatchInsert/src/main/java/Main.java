import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {


        List<Employee> employeeList = new ArrayList<>();

        System.out.println("*** Generating List : " + new Date().toString());

        for (int i = 0; i < 1000 * 100; i++) {
            int rnd = new Random().nextInt(10000);
            String firstName = String.valueOf(rnd);
            String lastName = String.valueOf(rnd);
            int salary = rnd;
            Employee employee = new Employee(firstName, lastName, salary);

            employeeList.add(employee);
        }

        System.out.println("*** List Generated : " + new Date().toString());

        System.out.println("*** Start Insert : " + new Date().toString());
        EmployeeBL employeeBL = new EmployeeBL();
        employeeBL.insertRange(employeeList);

        System.out.println("*** Insert Finished : " + new Date().toString());

        System.out.println("*** Start Update : " + new Date().toString());

        employeeBL=new EmployeeBL();
        employeeBL.updateAll();

        System.out.println("*** Update Finished : " + new Date().toString());

    }
}
