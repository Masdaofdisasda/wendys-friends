package at.ac.tuwien.sepm.assignment.individual.service;


import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceDataException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;

import java.util.List;

/**
 * Service for working with owners.
 */
public interface OwnerService {

    /**
     * @return a list of all stored owners
     * @throws PersistenceDataException if there is a problem during database access
     */
    List<Owner> allOwners();

    /**
     * @param ownerDto a new owner that should be saved in the database
     * @throws PersistenceDataException if there is a problem during database access
     * @throws ValidationException if there is something wrong with the ownerDto
     */
    void createOwner(OwnerDto ownerDto);

    /**
     * @param id of the owner that should be returned
     * @return an owner matching the given id
     * @throws PersistenceDataException if there is a problem during database access
     * @throws NotFoundException if there is no owner with given id
     */
    Owner getOwnerById(Long id);

    /**
     * @param searchText the name (or part of a name) of an owner
     * @return list of owners matching at least part of the searchText
     * @throws PersistenceDataException if there is a problem during db access
     * @throws NotFoundException if no owner matching searchText is in the database.
     * @throws ValidationException if the searchText is empty
     */
    List<Owner> getOwner(String searchText);
}
