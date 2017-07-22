import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Random;

public class EmployeeBL {
    private SessionFactory factory;

    public EmployeeBL() {
        Configuration configuration = new Configuration();
        this.factory = configuration.configure().buildSessionFactory();
    }

    public void insertRange(List<Employee> employeeList) {
        Session session = factory.openSession();
        session.setProperty("hibernate.jdbc.batch_size", 30);
        session.setProperty("hibernate.cache.use_second_level_cache", false);

        Transaction tx = session.beginTransaction();

        int i = 0;

        for (Employee employee : employeeList) {

            session.save(employee);
            i++;

            if (i % 30 == 0) { // same as the JDBC batch size
                //flush a batch of inserts and release memory:
                session.flush();
                session.clear();
            }
        }

        tx.commit();
        session.close();
    }

    public void updateAll() {

        Session session = factory.openSession();
        session.setProperty("hibernate.jdbc.batch_size", 30);
        session.setProperty("hibernate.cache.use_second_level_cache", false);

        ScrollableResults employeeResult = session.createQuery("from Employee")
                .setCacheMode(CacheMode.IGNORE)
                .scroll(ScrollMode.FORWARD_ONLY);

        Transaction tx = session.beginTransaction();

        int count = 0;

        while (employeeResult.next()) {

            count++;

            int rnd = new Random().nextInt(10000);
            String firstName = String.valueOf(rnd);
            String lastName = String.valueOf(rnd);
            int salary = rnd;

            Employee employee = (Employee) employeeResult.get(0);

            if (count % 30 == 0) {
                //flush a batch of updates and release memory:
                session.flush();
                session.clear();
            }

            tx.commit();
            session.close();
        }

    }
}
