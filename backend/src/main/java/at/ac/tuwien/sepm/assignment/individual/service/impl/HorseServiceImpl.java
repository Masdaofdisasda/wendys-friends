package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.rest.HorseEndpoint;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {
    private final HorseDao dao;
    private static final Logger log = LoggerFactory.getLogger(HorseService.class);

    public HorseServiceImpl(HorseDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Horse> allHorses() {
        log.trace("get all horses");
        return dao.getAll();
    }

    @Override
    public Horse getOneById(Long id) throws NotFoundException {
        log.trace("Get horse with id {}", id);
        return dao.getOneById(id);
    }

    @Override
    public Horse save(HorseDto horseDto) {
        log.trace("save new horse named " + horseDto.name());
        if (horseDto.name().trim().isEmpty()) throw new IllegalArgumentException("horse doesn't have a name");
        if (horseDto.birthdate().isAfter(java.time.LocalDate.now())) throw new IllegalArgumentException("horse was not born yet");

        return dao.save(horseDto);
    }
}
