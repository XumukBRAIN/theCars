package repo;

import java.util.List;
import java.util.Optional;

public interface CRUDRepo<E, K> {


    void delete(E entity);

    void deleteById(K id);

    boolean existById(K id);

    List<E> findAll();

    Optional<E> findById(Long id);

    E getByID(K id);

    E save(E entity);

}
