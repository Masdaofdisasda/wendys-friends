package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/horses")
public class HorseEndpoint {
    //private static final Logger LOGGER = LoggerFactory.getLogger(OwnerEndpoint.class);
    private final HorseService service;
    private final HorseMapper mapper;

    public HorseEndpoint(HorseService service, HorseMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public Stream<HorseDto> allHorses() {
        return service.allHorses().stream()
                .map(mapper::entityToDto);
    }

    @GetMapping(value = "/{id}")
    public HorseDto getOneById(@PathVariable("id") Long id) {
        //LOGGER.info("GET "+ BASE_URL + "/{}", id); // BASE_URL is a constant expression. id is not.
        try {
            return mapper.entityToDto(service.getOneById(id));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading owner", e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HorseDto createHorse(@RequestBody final HorseDto horseDto) {
        //try {
            return mapper.entityToDto(service.save(horseDto));
        //} catch (ValidationException e) {
        //    throw new
        //            ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during saving owner", e);
        }
}
