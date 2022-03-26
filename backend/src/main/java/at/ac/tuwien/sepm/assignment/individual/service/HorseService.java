package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
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
    List<Horse> allHorses();

    /**
     * forwards request to get a single horse to persistence
     * @param id of a horse
     * @return horse with id
     * @throws NotFoundException if no such horse was found
     */
    Horse getOneById(Long id);

    /**
     * validates horse fields and sends data to persistence
     * @param horseDto horse with data to validate
     * @throws ValidationException if any field is invalid
     */
    void save(HorseDto horseDto) throws ValidationException;

    /**
     * changes data
     * @param horseDto horse to be changed
     * @throws NotFoundException if horse does not exist in database
     * @throws ValidationException when horse is invalid
     */
    void updateHorse(HorseDto horseDto);

    /** removes database entry
     * @param id of horse to delete
     * @throws NotFoundException if horse does not exist in database.
     * */
    void deleteHorse(Long id);

    /**
     * look for female horses with names matching searchText
     * @param searchText matching term
     * @return all horses matching searchText
     */
    List<Horse> getFemaleHorse(String searchText);

    /**
     * look for male horses with names matching searchText
     * @param searchText matching term
     * @return all horses matching searchText
     */
    List<Horse> getMaleHorse(String searchText);

    /**
     * looks for matching horses and return all horses in db if fields are empty
     * @param horseDto search parameters
     * @return list of matching horses
     * @throws NotFoundException if no matching horse was found
     */
    List<Horse> searchHorse(HorseDto horseDto) throws NotFoundException;

}
