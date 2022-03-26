package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceDataException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Service for working with horses.
 */
public interface HorseService {

    /**
     * @return a list of all stored horses
     * @throws PersistenceDataException if there is a problem during database access
     */
    List<Horse> allHorses();

    /**
     * @param id of the horse that should be returned
     * @return a Horse matching the given id
     * @throws PersistenceDataException if there is a problem during database access
     * @throws NotFoundException if there is no horse with given id
     */
    Horse getHorseById(Long id);

    /**
     * @param horseDto a new horse that should be saved in the database
     * @throws PersistenceDataException if there is a problem during database access
     * @throws ValidationException if there is something wrong with the horseDto
     */
    void createHorse(HorseDto horseDto) throws ValidationException;

    /**
     * @param horseDto an existing horse with information to update
     * @throws PersistenceDataException if there is a problem during database access
     * @throws NotFoundException if the horse is not already in the database.
     * @throws ValidationException if there is something wrong with the horseDto
     */
    void updateHorse(HorseDto horseDto);

    /**
     * @param id of an existing horse that should be deleted
     * @throws PersistenceDataException if there is a problem during database access
     * @throws NotFoundException if the horse is not in the database.
     */
    void deleteHorse(Long id);

    /**
     * @param searchText the name (or part of a name) of a female horse
     * @return list of horses matching at least part of the searchText
     * @throws PersistenceDataException if there is a problem during db access
     * @throws NotFoundException if no female horse matching searchText is in the database.
     * @throws ValidationException if the searchText is empty
     */
    List<Horse> getFemaleHorse(String searchText);

    /**
     * searches male horses matching searchText
     * @param searchText text to match
     * @return list of matching horses
     * @throws PersistenceDataException if there is a problem during db access
     * @throws NotFoundException if no male horse matching searchText is in the database.
     * @throws ValidationException if the searchText is empty
     */
    List<Horse> getMaleHorse(String searchText);

    /**
     * @param horseDto parameters to match
     * @return list of horses matching the given parameters
     * @throws PersistenceDataException if there is a problem during db access
     * @throws NotFoundException if no matching horse is in the database.
     * @throws ValidationException if there is something wrong with the horseDto
     */
    List<Horse> searchHorse(HorseDto horseDto);

}
