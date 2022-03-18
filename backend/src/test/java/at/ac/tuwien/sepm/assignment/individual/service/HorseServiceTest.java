package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

import java.time.LocalDate;
import java.util.List;

import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
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
    } catch (PersistenceException e) {
      e.printStackTrace();
    }
    assertThat(horses.size()).isEqualTo(2);
    assertThat(horses.get(0).getId()).isEqualTo(-1);
    assertThat(horses.get(0).getName()).isEqualTo("Wendy");
  }

  @Test
  public void saveHorseCanSaveHorse(){
    HorseDto horseDto = new HorseDto(1L, "Horsy", "is a horse", LocalDate.of(2020, 7, 24), "f", "John Marston");
    horseService.save(horseDto);

    List<Horse> horses = null;
    try {
      horses = horseService.allHorses();
    } catch (PersistenceException e) {
      e.printStackTrace();
    }
    assertThat(horses.size()).isEqualTo(2);
    assertThat(horses.get(1).getId()).isEqualTo(1);
    assertThat(horses.get(1).getName()).isEqualTo("Horsy");
    assertThat(horses.get(1).getDescription()).isEqualTo("is a horse");
    assertThat(horses.get(1).getBirthdate()).isEqualTo(LocalDate.of(2020, 7, 24));
    assertThat(horses.get(1).getGender()).isEqualTo("f");
    assertThat(horses.get(1).getOwner()).isEqualTo("John Marston");
  }

}
