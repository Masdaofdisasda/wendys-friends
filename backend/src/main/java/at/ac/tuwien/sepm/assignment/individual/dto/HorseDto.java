package at.ac.tuwien.sepm.assignment.individual.dto;

import java.time.LocalDate;

/**
 * Class for Horse DTOs
 * Contains all common properties
 */
public record HorseDto(Long id, String name, String description, LocalDate birthdate, String gender, String owner ) {

}