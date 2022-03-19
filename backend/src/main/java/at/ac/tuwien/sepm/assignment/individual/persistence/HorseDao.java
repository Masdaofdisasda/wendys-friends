package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;

import java.util.List;

/**
 * Data Access Object for horses.
 * Implements access functionality to the application's persistent data store regarding horses.
 */
public interface HorseDao {
    /**
     * Get all horses stored in the persistent data store.
     * @return a list of all stored horses
     */
    List<Horse> getAll() throws PersistenceException;

    /**
     * Find a single horse according to its id
     * @param id of the horse
     * @return a Horse with the id
     * @throws NotFoundException if the horse could not be found
     */
    Horse getOneById(Long id) throws NotFoundException;

    /**
     * saves a new horse dataset in the database
     * @param horseDto horse that should be saved
     * @return the saved entity
     */
    void saveHorse(HorseDto horseDto);

    /**
     * @param horseDto horse to be changed
     * @throws NotFoundException if horse is not in database.
     */
    void updateHorse(HorseDto horseDto);
}
