package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;

import java.util.List;

/**
 * Service for working with horses.
 */
public interface HorseService {


    /**
     * Lists all horses stored in the system.
     * @return list of all stored horses
     */
    List<Horse> allHorses() throws PersistenceException;

    /**
     * forwards request to get a single horse to persistence
     * @param id number bigger than 0
     * @return horse with id
     * @throws NotFoundException if no such horse was found
     */
    Horse getOneById(Long id) throws NotFoundException;

    /**
     * validates horse fields and sends data to persistence
     * @param horseDto horse with data to validate
     * @return saved horse entity
     * @throws ValidationException if any field is invalid
     */
    void save(HorseDto horseDto) throws ValidationException;
}
