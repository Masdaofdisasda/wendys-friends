package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.springframework.stereotype.Component;

@Component
public class HorseMapper {

    public HorseDto entityToDto(Horse horse)
    {
        return new HorseDto(horse.getId(), horse.getName(), horse.getDescription(), horse.getBirthdate(), horse.getGender(), horse.getOwner(),
        horse.getMom(), horse.getDad());
    }
    public Horse dtoToEntity(HorseDto horseDto){
        return new Horse(horseDto.id(),horseDto.name(), horseDto.description(), horseDto.birthdate(), horseDto.gender(), horseDto.owner(),
                horseDto.mom(), horseDto.dad());
    }
}
