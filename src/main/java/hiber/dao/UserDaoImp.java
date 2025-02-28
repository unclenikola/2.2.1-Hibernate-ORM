package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

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
    @Transactional
    public User getUserByCarModelAndSeries(String model, int series) {
        System.out.println("Поиск пользователей по модели: " + model + ", серии: " + series);
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT u FROM User u JOIN FETCH u.car c WHERE c.model = :model AND c.series = :series";
        try {
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("model", model);
            query.setParameter("series", series);
            List<User> users = query.getResultList();
            if (users.isEmpty()) {
                System.out.println("Пользователи не найдены.");
                return null;
            } else {
                System.out.println("Найдено " + users.size() + " пользователей с указанными параметрами:");
                for (User user : users) {
                    System.out.println("  - " + user.getFirstName() + " " + user.getLastName() + " (ID: " + user.getId() + ")");
                }
                return null;
//                return users.get(0);  //возвращаем первого пользователя
            }
        } catch (Exception e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
