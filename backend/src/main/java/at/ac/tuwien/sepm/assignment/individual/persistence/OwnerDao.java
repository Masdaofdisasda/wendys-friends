package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Data Access Object for owners.
 * Implements access functionality to the application's persistent data store regarding owners.
 */
public interface OwnerDao {
    /**
     * Get all horses stored in the persistent data store.
     * @return a list of all stored horses
     * @throws DataAccessException if there is a problem during db access
     */
    List<Owner> getAll();

    /**
     * saves a new owner dataset in the database
     * @param ownerDto owner that should be saved
     * @throws DataAccessException if there is a problem during db access
     */
    void saveOwner(OwnerDto ownerDto);
}
