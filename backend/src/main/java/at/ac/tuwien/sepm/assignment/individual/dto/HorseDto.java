package at.ac.tuwien.sepm.assignment.individual.dto;

import java.util.Date;

/**
 * Class for Horse DTOs
 * Contains all common properties
 */
public record HorseDto(Long id, String name, String description, Date birthdate, int sex, String owner ) {

}