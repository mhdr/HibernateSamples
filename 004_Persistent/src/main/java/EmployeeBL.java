import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.internal.CriteriaUpdateImpl;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaUpdate;
import javax.transaction.Transaction;
import java.util.Iterator;
import java.util.List;

public class EmployeeBL {

    private EntityManagerFactory factory;

    public EmployeeBL() {
        factory = Persistence.createEntityManagerFactory("ir.mhdr.jpaDemo");
    }

    public void close() {
        factory.close();
    }

    /* Method to CREATE an employee in the database */
    public void addEmployee(String fname, String lname, int salary) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = null;

        try {
            tx = entityManager.getTransaction();
            tx.begin();

            Employee employee = new Employee(fname, lname, salary);
            entityManager.persist(employee);

            tx.commit();
        } catch (Exception ex) {

            if (tx != null) tx.rollback();
            ex.printStackTrace();

        } finally {

            entityManager.close();
        }
    }


    /* Method to  READ all the employees */
    public void listEmployees() {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = null;

        try {
            tx = entityManager.getTransaction();
            tx.begin();

            List employees = entityManager.createQuery("from Employee").getResultList();
            for (Iterator iterator =
                 employees.iterator(); iterator.hasNext(); ) {
                Employee employee = (Employee) iterator.next();
                System.out.print("First Name: " + employee.getFirstName());
                System.out.print("  Last Name: " + employee.getLastName());
                System.out.println("  Salary: " + employee.getSalary());
            }

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        } finally {

            entityManager.close();

        }
    }


    /* Method to UPDATE salary for an employee */
    public void updateEmployee(Integer EmployeeID, int salary) {

        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = null;

        try {
            tx = entityManager.getTransaction();
            tx.begin();

            TypedQuery<Employee> query = entityManager.createQuery("select emp from Employee emp where id=:id", Employee.class);
            query.setParameter("id", EmployeeID);
            Employee employee = query.getSingleResult();

            employee.setSalary(salary);

            entityManager.persist(employee);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        } finally {
            entityManager.close();
        }
    }


    /* Method to DELETE an employee from the records */
    public void deleteEmployee(Integer EmployeeID) {

        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = null;

        try {

            tx = entityManager.getTransaction();
            tx.begin();

            Employee employee = entityManager.find(Employee.class, EmployeeID);
            entityManager.remove(employee);

            tx.commit();

        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}
