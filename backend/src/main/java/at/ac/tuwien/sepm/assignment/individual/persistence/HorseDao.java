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
     * Get all horses stored in the persistent data store.
     * @return a list of all stored horses
     * @throws DataAccessException if there is a problem during db access
     */
    List<Horse> getAll();

    /**
     * Find a single horse according to its id
     * @param id of the horse
     * @return a Horse with the id
     * @throws DataAccessException if there is a problem during db access
     * @throws NotFoundException if the horse could not be found
     */
    Horse getHorseById(Long id);

    /**
     * saves a new horse dataset in the database
     * @param horseDto horse that should be saved
     * @return the saved entity
     * @throws DataAccessException if there is a problem during db access
     */
    void createHorse(HorseDto horseDto) throws DataAccessException;

    /**
     * change horse data
     * @param horseDto horse to be changed
     * @throws DataAccessException if there is a problem during db access
     * @throws NotFoundException if horse is not in database.
     */
    void updateHorse(HorseDto horseDto) throws DataAccessException;

    /**
     * deletes horse entry
     * @param id of horse to be deleted
     * @throws DataAccessException if there is a problem during db access
     */
    void deleteHorse(Long id) throws DataAccessException;

    /**
     * searches female horses matching searchText
     * @param searchText text to match
     * @return list of matching horses
     * @throws DataAccessException if there is a problem during db access
     */
    List<Horse> getFemaleHorse(String searchText) throws DataAccessException;

    /**
     * searches male horses matching searchText
     * @param searchText text to match
     * @return list of matching horses
     * @throws DataAccessException if there is a problem during db access
     */
    List<Horse> getMaleHorse(String searchText) throws DataAccessException;

    /**
     * searches for matching horses
     * @param horseDto parameters to match
     * @return list of matching horses
     * @throws DataAccessException if there is a problem during db access
     */
    List<Horse> searchHorse(HorseDto horseDto) throws DataAccessException;

}
