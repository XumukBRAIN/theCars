package services;

import model.Car;
import model.Creator;
import repo.CarRepo;
import repo.CreatorRepo;

import java.util.List;

public class CarServiceImp implements CarService{

    private final CarRepo carRepo;
    private final CreatorRepo creatorRepo;

    public CarServiceImp(CarRepo carRepo, CreatorRepo creatorRepo) {
        this.carRepo = carRepo;
        this.creatorRepo = creatorRepo;
    }

    @Override
    public List<Car> findAll() {
        return carRepo.findAll();
    }

    @Override
    public Car create(Car creator) {
        return carRepo.save(creator);
    }

    @Override
    public void delete(Car creator) {
        carRepo.delete(creator);
    }

    @Override
    public void deleteById(Long id) {
        carRepo.deleteById(id);
    }

    @Override
    public Car update(Car creator) {
        return carRepo.save(creator);
    }

    @Override
    public void createForCreator(Car car, Long creatorId) {
        Creator creator = creatorRepo.findById(creatorId).get();
        car.addCreator(creator);
        carRepo.save(car);
    }

    @Override
    public List<Car> findForCreatorId(Long creatorId) {
        return carRepo.findForCreator(creatorRepo.
                getByID(creatorId));
    }
}
