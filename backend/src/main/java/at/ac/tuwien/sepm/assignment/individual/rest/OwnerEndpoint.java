package at.ac.tuwien.sepm.assignment.individual.rest;


import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.exception.DataValidationException;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceDataException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.mapper.OwnerMapper;
import at.ac.tuwien.sepm.assignment.individual.service.OwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/owners")
public class OwnerEndpoint {
    private static final Logger log = LoggerFactory.getLogger(OwnerEndpoint.class);
    private final OwnerService service;
    private final OwnerMapper mapper;

    public OwnerEndpoint(OwnerService service, OwnerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public Stream<OwnerDto> allOwners() {
        log.info("GET "+ "/owners");
        try {
            return service.allOwners().stream()
                    .map(mapper::entityToDto);
        } catch (NotFoundException e) {
            throw handleException(e,"allOwners()");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOwner(@RequestBody final OwnerDto ownerDto){
        log.info("POST " + "create owner ", ownerDto);
        try {
            service.save(ownerDto);
        } catch (Exception e){
            throw handleException(e, "createOwner");
        }
    }

    private ResponseStatusException handleException(Exception e, String message) {
        log.error(message,e);
        if(e instanceof ValidationException){
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }else if(e instanceof NotFoundException){
            return  new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }else if(e instanceof PersistenceDataException){
            return new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        }else{
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
