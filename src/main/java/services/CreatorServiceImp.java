package services;

import model.Creator;
import repo.CreatorRepo;

import java.util.List;

public class CreatorServiceImp implements CreatorService{

    private final CreatorRepo creatorRepo;

    public CreatorServiceImp(CreatorRepo creatorRepo) {
        this.creatorRepo = creatorRepo;
    }

    @Override
    public List<Creator> findAll() {
        return creatorRepo.findAll();
    }

    @Override
    public Creator create(Creator creator) {
        return creatorRepo.save(creator);
    }

    @Override
    public void delete(Creator creator) {
        creatorRepo.delete(creator);
    }

    @Override
    public void deleteById(Long id) {
        creatorRepo.deleteById(id);
    }

    @Override
    public Creator update(Creator creator) {
        return creatorRepo.save(creator);
    }
}
