package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceDataException;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import at.ac.tuwien.sepm.assignment.individual.service.OwnerService;
import at.ac.tuwien.sepm.assignment.individual.service.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("OwnerServiceImpl")
public class OwnerServiceImpl implements OwnerService {
    private final OwnerDao dao;
    private static final Logger log = LoggerFactory.getLogger(OwnerService.class);
    private final Validator validator;

    public OwnerServiceImpl(OwnerDao dao, Validator val) {
        this.dao = dao;
        this.validator = val;
    }

    @Override
    public List<Owner> allOwners() {
        log.trace("get all owners");
        try {
            return dao.getAll();
        } catch (DataAccessException e) {
            throw new NotFoundException(e);
        }
    }

    @Override
    public void save(OwnerDto ownerDto){
        log.trace("save()");
        validator.validateSaveOwner(ownerDto);
        log.debug("owner fields are valid");
        try {
            dao.saveOwner(ownerDto);
        }catch (DataAccessException e){
            throw new PersistenceDataException(e);
        }
    }

    @Override
    public Owner getOneById(Long id){
        log.trace("getOneById()", id);
        try {
            return dao.getOneById(id);
        } catch (NotFoundException e){
            throw new NotFoundException(e);
        }
    }

    @Override
    public List<Owner> getOwner(String searchText){
        log.trace("getOwner()", searchText);
        validator.validateSearchText(searchText);
        log.debug("search text is valid");
        try {
            return dao.getOwner(searchText);
        } catch (Exception e){
            throw new NotFoundException(e);
            //TODO
        }
    }
}
