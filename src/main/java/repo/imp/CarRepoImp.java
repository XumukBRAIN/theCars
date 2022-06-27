package repo.imp;

import model.Car;
import model.Creator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repo.CarRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarRepoImp implements CarRepo {

    private final SessionFactory sessionFactory;

    public CarRepoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void delete(Car entity) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        }catch (IllegalArgumentException e){
            assert transaction != null;
            transaction.rollback();
            System.err.println("Entity is not persistent");
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            int updated = session.createQuery("delete from Car c where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            if (updated != 1) {
                throw new IllegalArgumentException();
            }
            transaction.commit();
        } catch (IllegalArgumentException e) {
            assert transaction != null;
            transaction.rollback();
            System.err.println("Entity is not present in the table");
            throw new RuntimeException();
        }
    }

    @Override
    public boolean existById(Long id) {
        try(Session session = sessionFactory.openSession()){
            return session.get(Car.class, id) != null;
        }
    }

    @Override
    public List<Car> findAll() {
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("select c from Car c", Car.class).
                    getResultList();
        }
    }

    @Override
    public Optional<Car> findById(Long id) {
        try(Session session = sessionFactory.openSession()){
            return Optional.of(session.get(Car.class, id));
        }
    }

    @Override
    public Car getByID(Long id) {
        try(Session session = sessionFactory.openSession()){
            return session.load(Car.class, id);
        }
    }

    @Override
    public Car save(Car entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
            return entity;
        } catch (Exception e){
            assert transaction != null;
            transaction.rollback();
            throw new RuntimeException();
        }
    }

    @Override
    public List<Car> findForCreator(Creator creator) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select from Car c join c.creators c where c.id = :creatorId", Car.class)
                    .setParameter("authorId", creator.getId())
                    .getResultList();
        } catch (Exception e){
            return new ArrayList<>();
        }
    }
}
