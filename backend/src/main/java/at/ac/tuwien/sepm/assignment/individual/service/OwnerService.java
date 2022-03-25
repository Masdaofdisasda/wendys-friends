package at.ac.tuwien.sepm.assignment.individual.service;


import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceDataException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Service for working with owners.
 */
public interface OwnerService {

    /**
     * Lists all owners stored in the system.
     * @return list of all stored owners
     */
    List<Owner> allOwners();

    /**
     * validates owner fields and sends data to persistence
     * @param ownerDto owner with data to validate
     * @throws ValidationException if there is a problem validating the owner fields
     * @throws PersistenceDataException if there is a problem accessing the database
     */
    void save(OwnerDto ownerDto);
}
