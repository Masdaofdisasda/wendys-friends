package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
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
        return service.allHorses().stream()
                .map(mapper::entityToDto);
    }

    @GetMapping(value = "/{id}")
    public HorseDto getOneById(@PathVariable("id") Long id) {
        log.info("GET "+ "/horses" + "/{}", id);
        try {
            return mapper.entityToDto(service.getOneById(id));
        } catch (NotFoundException e) {
            log.error("Error finding horse");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error finding horse", e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HorseDto createHorse(@RequestBody final HorseDto horseDto) {
        log.info("POST " + "create horse " + horseDto.name());
        //try {
            return mapper.entityToDto(service.save(horseDto));
        //} catch (ValidationException e) {
        //    throw new
        //            ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during saving owner", e);
        }
}
