package repo;

import model.Car;
import model.Creator;

import java.util.List;

public interface CarRepo extends CRUDRepo<Car, Long>{
    List<Car> findForCreator (Creator creator);
}
