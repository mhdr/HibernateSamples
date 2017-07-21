import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

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
}
