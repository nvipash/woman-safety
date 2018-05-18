package safety.Controllers;

import safety.Entity.QuestionsEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class QuestionsController {
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

    @RequestMapping("/api/tests/questions")
    public QuestionsEntity getQuestions(@RequestParam(value = "id") int id) {
        final Session session = getSession();
        try {
            org.hibernate.Query query = session.createQuery("from " + "QuestionsEntity where idQuestion = :code");
            query.setParameter("code", id);
            return (QuestionsEntity) query.list().get(0);
        } finally {
            session.close();
        }
    }
}