package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Data Access Object for horses.
 * Implements access functionality to the application's persistent data store regarding horses.
 */
public interface HorseDao {

    /**
     * @return a list of all stored horses
     * @throws DataAccessException if there is a problem during database access
     */
    List<Horse> getAll();

    /**
     * @param id of the horse that should be returned
     * @return a Horse matching the given id
     * @throws DataAccessException if there is a problem during database access
     * @throws NotFoundException if there is no horse with given id
     */
    Horse getHorseById(Long id);

    /**
     * @param horseDto a new horse that should be saved in the database
     * @throws DataAccessException if there is a problem during database access
     */
    void createHorse(HorseDto horseDto);

    /**
     * @param horseDto an existing horse with information to update
     * @throws DataAccessException if there is a problem during database access
     * @throws NotFoundException if the horse is not already in the database.
     */
    void updateHorse(HorseDto horseDto);

    /**
     * @param id of an existing horse that should be deleted
     * @throws DataAccessException if there is a problem during database access
     * @throws NotFoundException if the horse is not in the database.
     */
    void deleteHorse(Long id);

    /**
     * @param searchText the name (or part of a name) of a female horse
     * @return list of horses matching at least part of the searchText
     * @throws DataAccessException if there is a problem during db access
     * @throws NotFoundException if no female horse matching searchText is in the database.
     */
    List<Horse> getFemaleHorse(String searchText);

    /**
     * searches male horses matching searchText
     * @param searchText text to match
     * @return list of matching horses
     * @throws DataAccessException if there is a problem during db access
     * @throws NotFoundException if no male horse matching searchText is in the database.
     */
    List<Horse> getMaleHorse(String searchText);

    /**
     * @param horseDto parameters to match
     * @return list of horses matching the given parameters
     * @throws DataAccessException if there is a problem during db access
     * @throws NotFoundException if no matching horse is in the database.
     */
    List<Horse> searchHorse(HorseDto horseDto);

}
