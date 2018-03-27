package safety.Controllers;

import safety.Entity.InstructionsEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultsController {

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

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    @RequestMapping("/api/tests/instruction")
    public  InstructionsEntity getInstruction(@RequestParam(value="count") int count){
        final Session session = getSession();
        try {
            org.hibernate.Query query = session.createQuery("from " + "InstructionsEntity where rangeStart<=:code and rangeEnd>=:code");
            query.setParameter("code", count);
            InstructionsEntity  instruction = (InstructionsEntity)query.list().get(0);
            return instruction;
        }finally {
            session.close();
        }
    }
}
