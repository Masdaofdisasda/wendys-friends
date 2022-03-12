package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {
    private final HorseDao dao;

    public HorseServiceImpl(HorseDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Horse> allHorses() {
        return dao.getAll();
    }

    @Override
    public Horse getOneById(Long id) throws NotFoundException {
        //LOGGER.info("Get owner with id {}", id);
        return dao.getOneById(id);
    }

    @Override
    public Horse save(HorseDto horseDto) {
        return dao.save(horseDto);
    }
}
