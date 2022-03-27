package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
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
        validateFields(horseDto);
    }

    public void validateUpdateHorse(HorseDto horseDto){
        validateFields(horseDto);
    }

    private void validateFields(HorseDto horseDto) {
        if (horseDto.name().trim().isEmpty() | horseDto.birthdate() == null | horseDto.birthdate().isAfter(java.time.LocalDate.now()))
            throw new ValidationException("Empty name or impossible birthdate");
        if (horseDto.mom()!=null ){
            Horse mom;
            try {
                mom = horseDao.getHorseById(horseDto.mom());
            } catch (DataAccessException e){
                throw new ValidationException("mother doesn't exist");
            }
            if (!mom.getGender().equals("f"))
                throw new ValidationException("mother has wrong gender: mother is " + mom.getGender());
        }
        if (horseDto.dad()!=null ){
            Horse dad;
            try {
                dad = horseDao.getHorseById(horseDto.dad());
            } catch (DataAccessException e){
                throw new ValidationException("mother or father doesn't exist");
            }
            if (!dad.getGender().equals("m"))
                throw new ValidationException("father has wrong gender: father is " + dad.getGender());
        }
    }

    public void validateSearchText(String searchText){
        if (searchText.isEmpty())
            throw new ValidationException("search text is empty");
    }

    public boolean isEmpty(HorseDto horseDto){
        if (horseDto.name() == null &&
        horseDto.description() == null &&
        horseDto.birthdate() == null &&
        horseDto.gender() == null &&
        horseDto.owner() == null){
            return true;
        }
        return false;
    }

    public void validateSearchHorse(HorseDto horseDto){
        if (horseDto.birthdate() != null) {
            if (horseDto.birthdate().isAfter(java.time.LocalDate.now()))
                throw new ValidationException("Impossible birthdate");
        }
    }

    public void validateSaveOwner(OwnerDto ownerDto){
        if (ownerDto.givenname() == null || ownerDto.surname() == null || ownerDto.givenname() == "" || ownerDto.surname() == "" ){
            throw new ValidationException("Name can't be empty");
        }
        if (!ownerDto.email().isBlank()){
            String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
            if (!ownerDto.email().matches(regex)){
                throw new ValidationException("Email address is not valid");
            }
        }
    }
}
