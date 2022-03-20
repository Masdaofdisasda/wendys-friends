package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.DataValidationException;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.rest.HorseEndpoint;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.service.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("HorseServiceImpl")
public class HorseServiceImpl implements HorseService {
    private final HorseDao dao;
    private static final Logger log = LoggerFactory.getLogger(HorseService.class);
    private final Validator validator;

    public HorseServiceImpl(HorseDao dao, Validator val) {
        this.dao = dao;
        this.validator = val;
    }

    @Override
    public List<Horse> allHorses() {
        log.trace("get all horses");
        try {
            return dao.getAll();
        } catch (DataAccessException e) {
            throw new NotFoundException(e);
        }
    }

    @Override
    public Horse getOneById(Long id) {
        log.trace("getOneById()", id);
        try {
            return dao.getOneById(id);
        } catch (NotFoundException e) {
            e.printStackTrace(); //TODO
        }

        return null;
    }

    @Override
    public void save(HorseDto horseDto) {
        log.trace("save()");
        validator.validateSaveHorse(horseDto);
        log.debug("horse fields are valid");
        try {
            dao.saveHorse(horseDto);
        } catch (Exception e) {
            e.printStackTrace(); //TODO
        }

    }

    @Override
    public void updateHorse(HorseDto horseDto){
        log.trace("updateHorse()");
        validator.validateUpdateHorse(horseDto);
        log.debug("horse fields are valid");
        try {
            dao.updateHorse(horseDto);
        } catch (Exception e) {
            e.printStackTrace(); //TODO
        }
    }

    @Override
    public void deleteHorse(Long id){
        log.trace("deleteHorse", id);
        try {
            validator.validateDeleteHorse(id);
            dao.deleteHorse(id);
        } catch (Exception e) {
            e.printStackTrace(); //TODo
        }
    }

    @Override
    public List<Horse> getFemaleHorse(String searchText){
        log.trace("getFemaleHorse()", searchText);
        try {
            return dao.getFemaleHorse(searchText);
        } catch (DataAccessException e) {
            throw new NotFoundException(e);
        }
    }

    @Override
    public List<Horse> getMaleHorse(String searchText){
        log.trace("getMaleHorse()", searchText);
        try {
            return dao.getMaleHorse(searchText);
        } catch (DataAccessException e) {
            throw new NotFoundException(e);
        }
    }
}
