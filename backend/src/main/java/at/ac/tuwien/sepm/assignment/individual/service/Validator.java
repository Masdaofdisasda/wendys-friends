package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Validator {
    private HorseDao horseDao;
    private String date = "yyyy-MM-dd";

    @Autowired
    public Validator(@Qualifier("HorseJdbcDao") HorseDao horseDao){
        this.horseDao = horseDao;
    }

    public void validateSaveHorse(HorseDto horseDto){
        if (horseDto.name().trim().isEmpty() | horseDto.birthdate().isAfter(java.time.LocalDate.now()))
            throw new ValidationException("Empty name or impossible birthdate");
    }
}
