package services;

import model.Creator;

import java.util.List;

public interface CreatorService {

    List<Creator> findAll();

    Creator create(Creator creator);

    void delete(Creator creator);

    void deleteById(Long id);

    Creator update(Creator creator);

}
