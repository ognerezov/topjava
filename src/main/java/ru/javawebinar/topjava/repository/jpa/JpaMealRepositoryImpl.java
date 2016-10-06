package ru.javawebinar.topjava.repository.jpa;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);

        if(meal.isNew()){
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        } else{
            if(meal.getUser()==null)  meal.setUser(ref);
            else if(meal.getUser().getId()!=userId) throw new NotFoundException("Wrong user");
          return  em.merge(meal);
        }
    }
    @Transactional
    @Override
    public boolean delete(int id, int userId) {
        Meal meal=get(id,userId);
        if(meal==null) return false;
        if(meal.getUser().getId()!=userId) throw new NotFoundException("Wrong user");
        return em.createNamedQuery(Meal.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal=em.find(Meal.class, id);
        if(meal.getUser().getId()!=userId) throw new NotFoundException("Wrong user");
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        Query<Meal> query = (Query<Meal>) em.createQuery("SELECT u FROM Meal u WHERE u.user.id=:id ORDER BY u.dateTime DESC");
        query.setParameter("id", userId);

        return query.getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId){
        Query<Meal> query = (Query<Meal>) em.createQuery("SELECT u FROM Meal u WHERE u.user.id=:id AND u.dateTime BETWEEN :d1 AND :d2 ORDER BY u.dateTime DESC");
        query.setParameter("id", userId);
        query.setParameter("d1", startDate);
        query.setParameter("d2", endDate);
        return query.getResultList();
    }
}