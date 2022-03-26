package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

import java.time.LocalDate;
import java.util.List;

import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles({"test", "datagen"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class HorseDaoTest {

  @Autowired
  HorseDao horseDao;

  @Test
  public void getAllReturnsAllStoredHorses() { //test 0
    List<Horse> horses = horseDao.getAll();
    assertThat(horses.size()).isEqualTo(10);
    assertThat(horses.get(0).getId()).isEqualTo(-10);
    assertThat(horses.get(0).getName()).isEqualTo("Lad");
  }

  @Test
  public void getOneByIdReturnsAllDataFields(){ //test 1 - positive
    Horse horse = horseDao.getHorseById(-9L);
    assertThat(horse.getName()).isEqualTo("Shamin");
    assertThat(horse.getDescription()).isEqualTo("horse test 9");
    assertThat(horse.getBirthdate()).isEqualTo("2009-01-01");
    assertThat(horse.getGender()).isEqualTo("m");
    assertThat(horse.getOwner()).isEqualTo(-1L);
    assertThat(horse.getMom()).isEqualTo(-1);
    assertThat(horse.getDad()).isEqualTo(-2);
  }

  @Test
  public void getFemaleHorseOnlyGetsFemale(){ //test 2 - positive
    List<Horse> horses = horseDao.getFemaleHorse("e");
    assertThat(horses.size()).isEqualTo(2);
    assertThat(horses.get(0).getName()).isEqualTo("Ceres");
    assertThat(horses.get(1).getName()).isEqualTo("Wendy");
  }

  @Test
  @Transactional
  public void deleteHorseThrowsNotFoundException(){ //test 3 - negative

    Assertions.assertThrows(NotFoundException.class, ()->{
      horseDao.deleteHorse(-100L);
      horseDao.deleteHorse(-500L);
      horseDao.deleteHorse(0L);
      horseDao.deleteHorse(500L);
    });
  }

  @Test
  @Transactional
  public void updateHorseThrowsNotFoundException(){ //test 4 - negative
    HorseDto h1 = new HorseDto(-100L, "test1", null, LocalDate.of(2000,01,01), "m", null, null,null);
    HorseDto h2 = new HorseDto(-3423L, "test2", null, LocalDate.of(2000,01,02), "m", null, null,null);
    HorseDto h3 = new HorseDto(567L, "test3", null, LocalDate.of(2000,01,03), "m", null, null,null);
    HorseDto h4 = new HorseDto(87354L, "test4", null, LocalDate.of(2000,01,04), "m", null, null,null);

    Assertions.assertThrows(NotFoundException.class, ()->{
      horseDao.updateHorse(h1);
      horseDao.updateHorse(h2);
      horseDao.updateHorse(h3);
      horseDao.updateHorse(h4);
    });
  }
}
