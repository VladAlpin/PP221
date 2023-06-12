package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public User getUserByCar(String model, Integer series) {
        TypedQuery<User> query = sessionFactory
                .getCurrentSession()
                .createQuery("FROM User user LEFT JOIN FETCH user.car where user.car.model = :model  and user.car.series = :series")
                .setParameter("model", model)
                .setParameter("series", series);
        return query.getSingleResult();
    }
}
