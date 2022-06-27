package repo.imp;

import model.Creator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repo.CreatorRepo;

import java.util.List;
import java.util.Optional;

public class CreatorRepoImp implements CreatorRepo {
    private final SessionFactory sessionFactory;

    public CreatorRepoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public long count() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Long res = session
                    .createQuery("select count(c) from Creator c", Long.class)
                    .getSingleResult();
            transaction.commit();
            return res;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Creator entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (IllegalArgumentException e) {
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
            int updated = session.createQuery("delete Creator c where id = :id")
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
        try (Session session = sessionFactory.openSession()) {
            return session.get(Creator.class, id) != null;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


    @Override
    public List<Creator> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("select c from Creator c", Creator.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<Creator> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.of(session.get(Creator.class, id));
        }
    }

    @Override
    public Creator getByID(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.load(Creator.class, id);
        }
    }


    @Override
    public Creator save(Creator entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            assert transaction != null;
            transaction.rollback();
            throw new RuntimeException();
        }
    }
}
