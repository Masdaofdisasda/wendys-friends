package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test", "datagen"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
@EnableWebMvc
@WebAppConfiguration
public class HorseEndpointTest {

  @Autowired
  private WebApplicationContext webAppContext;
  private MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
  }

  @Test
  public void gettingAllHorses() throws Exception {
    byte[] body = mockMvc
        .perform(MockMvcRequestBuilders
            .get("/horses")
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andReturn().getResponse().getContentAsByteArray();

    List<HorseDto> horseResult = objectMapper.readerFor(HorseDto.class).<HorseDto>readValues(body).readAll();

    assertThat(horseResult).isNotNull();
    assertThat(horseResult.size()).isEqualTo(10);
    assertThat(horseResult.get(0).id()).isEqualTo(-10);
    assertThat(horseResult.get(0).name()).isEqualTo("Lad");
  }

  @Test
  public void gettingNonexistentUrlReturns404() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders
            .get("/asdf123")
        ).andExpect(status().isNotFound());
  }

  @Test
  public void getMaleHorsesReturnsHorses() throws Exception { //test 1 - positive

    byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/horses/f/Wendy")
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

    List<HorseDto> horseResult = objectMapper.readerFor(HorseDto.class).<HorseDto>readValues(body).readAll();

    assertThat(horseResult).isNotNull();
    assertThat(horseResult.size()).isLessThanOrEqualTo(5);
    assertThat(horseResult.get(0).id()).isEqualTo(-1);
    assertThat(horseResult.get(0).name()).isEqualTo("Wendy");
  }

  @Test
  @Transactional
  public void deleteHorseReturnsNotFoundException() throws Exception { //test 2 - negative
    byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                    .delete("/horses/-245")
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsByteArray();

  }

  @Test
  public void getHorseByIdReturnsHorse() throws Exception { //test 3 - positive

    byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/horses/-1")
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

    List<HorseDto> horseResult = objectMapper.readerFor(HorseDto.class).<HorseDto>readValues(body).readAll();

    assertThat(horseResult.size()).isEqualTo(1);
    assertThat(horseResult.get(0).name()).isEqualTo("Wendy");
    assertThat(horseResult.get(0).description()).isEqualTo("horse test 1");
    assertThat(horseResult.get(0).birthdate()).isEqualTo("2001-01-01");
    assertThat(horseResult.get(0).gender()).isEqualTo("f");
    assertThat(horseResult.get(0).owner()).isEqualTo(-1);


  }

  @Test
  public void getHorseByIdReturnsNotFoundException() throws Exception { //test 4 - negative

    byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                    .get("/horses/-10000")
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsByteArray();



  }

}
