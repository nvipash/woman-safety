package safety.Controllers;

import safety.Entity.LocationsEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationsController {
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

    @GetMapping(value = "/api/locations")
    public List<LocationsEntity> getLocations() {
        try (Session session = getSession()) {
            org.hibernate.Query query = session.createQuery("from " + "LocationsEntity");
            return (List<LocationsEntity>) query.list();
        }
    }
}