package at.ac.tuwien.sepm.assignment.individual.service;

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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles({"test", "datagen"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class HorseServiceTest {

  @Autowired
  HorseService horseService;

  @Test
  public void getAllReturnsAllStoredHorses() {
    List<Horse> horses = null;
    try {
      horses = horseService.allHorses();
    } catch (NotFoundException e) {
      e.printStackTrace();
    }
    assertThat(horses.size()).isEqualTo(11);
    assertThat(horses.get(0).getId()).isEqualTo(-10);
    assertThat(horses.get(0).getName()).isEqualTo("Lad");
  }

  @Test
  public void saveHorseCanSaveHorse(){
    HorseDto horseDto = new HorseDto(1L, "Horsy", "is a horse", LocalDate.of(2020, 7, 24), "f", -1L,
            -1L, -2L);
    horseService.createHorse(horseDto);

    List<Horse> horses = null;
    try {
      horses = horseService.allHorses();
    } catch (NotFoundException e) {
      e.printStackTrace();
    }
    assertThat(horses.size()).isEqualTo(11);
    assertThat(horses.get(10).getId()).isEqualTo(1);
    assertThat(horses.get(10).getName()).isEqualTo("Horsy");
    assertThat(horses.get(10).getDescription()).isEqualTo("is a horse");
    assertThat(horses.get(10).getBirthdate()).isEqualTo(LocalDate.of(2020, 7, 24));
    assertThat(horses.get(10).getGender()).isEqualTo("f");
    assertThat(horses.get(10).getOwner()).isEqualTo(-1);
  }

  @Test
  public void saveHorseReturnsException(){
    HorseDto horseDto1 = new HorseDto(1L, "", "is a horse1", LocalDate.of(2020, 7, 24), "f", -1L,
            null, -2L);
    HorseDto horseDto2 = new HorseDto(2L, "horse2", "is a horse2", LocalDate.of(2030, 7, 24), "f", -1L,
            -1L, null);
    HorseDto horseDto3 = new HorseDto(3L, "horse3", "is a horse3", LocalDate.of(2020, 7, 24), "", -1L,
            null, -2L);
    HorseDto horseDto4 = new HorseDto(3L, "horse4", "is a horse3", LocalDate.of(2020, 7, 24), "", -1L,
            -1L, null);

    Assertions.assertThrows(ValidationException.class, ()->{
      horseService.createHorse(horseDto1);
      horseService.createHorse(horseDto2);
      horseService.createHorse(horseDto3);
      horseService.createHorse(horseDto4);
    });
  }

  @Test
  public void getFemaleHorseReturnsHorse(){

    List<Horse> horses = null;
    try {
      horses = horseService.getFemaleHorse("end");
    } catch (NotFoundException e) {
      e.printStackTrace();
    }
    assertThat(horses.get(0).getId()).isEqualTo(-1);
    assertThat(horses.get(0).getName()).isEqualTo("Wendy");
  }
}
