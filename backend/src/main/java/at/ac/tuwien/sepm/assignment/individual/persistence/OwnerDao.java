package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Data Access Object for owners.
 * Implements access functionality to the application's persistent data store regarding owners.
 */
public interface OwnerDao {

    /**
     * @return a list of all stored owners
     * @throws DataAccessException if there is a problem during database access
     */
    List<Owner> getAll();

    /**
     * @param ownerDto a new owner that should be saved in the database
     * @throws DataAccessException if there is a problem during database access
     */
    void createOwner(OwnerDto ownerDto);

    /**
     * @param id of the owner that should be returned
     * @return an owner matching the given id
     * @throws DataAccessException if there is a problem during database access
     * @throws NotFoundException if there is no owner with given id
     */
    Owner getOwnerById(Long id);

    /**
     * @param searchText the name (or part of a name) of an owner
     * @return list of owners matching at least part of the searchText
     * @throws DataAccessException if there is a problem during db access
     * @throws NotFoundException if no owner matching searchText is in the database.
     */
    List<Owner> getOwner(String searchText);
}
