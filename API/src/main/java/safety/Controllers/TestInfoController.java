package safety.Controllers;

import safety.Entity.SurveysEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestInfoController {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    @RequestMapping("/api/tests/info")
    public List<SurveysEntity> getTestInfo(){
        try (Session session = getSession()) {
            org.hibernate.Query query = session.createQuery("from " + "SurveysEntity");
            return (List<SurveysEntity>) query.list();
        }
    }
}