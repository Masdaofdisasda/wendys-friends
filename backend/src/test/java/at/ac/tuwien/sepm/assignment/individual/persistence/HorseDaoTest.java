package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import java.util.List;

import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles({"test", "datagen"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class HorseDaoTest {

  @Autowired
  HorseDao horseDao;

  @Test
  public void getAllReturnsAllStoredHorses() {
    List<Horse> horses = horseDao.getAll();
    assertThat(horses.size()).isEqualTo(10);
    assertThat(horses.get(0).getId()).isEqualTo(-10);
    assertThat(horses.get(0).getName()).isEqualTo("Lad");
  }

  @Test
  public void getOneByIdReturnsAllDataFields(){
    Horse horse = horseDao.getOneById(-9L);
    assertThat(horse.getName()).isEqualTo("Shamin");
    assertThat(horse.getDescription()).isEqualTo("horse test 9");
    assertThat(horse.getBirthdate()).isEqualTo("2009-01-01");
    assertThat(horse.getGender()).isEqualTo("m");
    assertThat(horse.getOwner()).isEqualTo("sql");
    assertThat(horse.getMom()).isEqualTo(-1);
    assertThat(horse.getDad()).isEqualTo(-2);
  }
}
