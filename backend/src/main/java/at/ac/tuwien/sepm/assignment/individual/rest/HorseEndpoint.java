package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.exception.DataValidationException;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/horses")
public class HorseEndpoint {
    private static final Logger log = LoggerFactory.getLogger(HorseEndpoint.class);
    private final HorseService service;
    private final HorseMapper mapper;

    public HorseEndpoint(HorseService service, HorseMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public Stream<HorseDto> allHorses() {
        log.info("GET "+ "/horses");
        try {
            return service.allHorses().stream()
                    .map(mapper::entityToDto);
        } catch (PersistenceException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage() );
        }
    }

    @GetMapping(value = "/{id}")
    public HorseDto getOneById(@PathVariable("id") Long id) {
        log.info("GET "+ "/horses" + "/{}", id);
        try {
            return mapper.entityToDto(service.getOneById(id));
        } catch (Exception e) {
            throw handleException(e, "getOneByID()" + id);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createHorse(@RequestBody final HorseDto horseDto) {
        log.info("POST " + "create horse " + horseDto);
        try {
            service.save(horseDto);
        } catch (Exception e) {
            throw handleException(e, "create horse " + horseDto);
        }
    }

    @PutMapping
    public void updateHorse(@RequestBody final HorseDto horseDto){
        log.info("PUT " + "update horse " + horseDto);
        try{
            service.updateHorse(horseDto);
        } catch (Exception e) {
            e.printStackTrace(); //TODO
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteHorse(@PathVariable("id") Long id){
        log.info("DELETE " + "/horses " + "/{}", id);
        try {
            service.deleteHorse(id);
        } catch (Exception e){
            //TODO
        }
    }

    private ResponseStatusException handleException(Exception e, String message) {
        log.error(message,e);
        if(e instanceof ValidationException){
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }else if(e instanceof NotFoundException){
            return  new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }else if(e instanceof DataValidationException){
            return new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        }else{
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
