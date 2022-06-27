package services;

import model.Car;

import java.util.List;

public interface CarService {

    List<Car> findAll();

    Car create(Car creator);

    void delete(Car creator);

    void deleteById(Long id);

    Car update(Car creator);

    void createForCreator(Car car, Long creatorId);

    List<Car> findForCreatorId(Long creatorId);

}
