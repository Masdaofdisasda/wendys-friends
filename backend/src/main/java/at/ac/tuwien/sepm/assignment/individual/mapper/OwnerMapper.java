package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {

    public OwnerDto entityToDto(Owner owner){
        return new OwnerDto(owner.getId(), owner.getGivenname(), owner.getSurname(), owner.getEmail());
    }

    public Owner dtoToEntity(OwnerDto ownerDto){
        return new Owner(ownerDto.id(), ownerDto.givenname(), ownerDto.surname(), ownerDto.email());
    }

}
