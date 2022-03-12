package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class HorseJdbcDao implements HorseDao {
    private static final String TABLE_NAME = "horse";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    //private static final Logger LOGGER = LoggerFactory.getLogger(OwnerJdbcDao.class);

    private final JdbcTemplate jdbcTemplate;
    private final HorseMapper mapper;

    public HorseJdbcDao(JdbcTemplate jdbcTemplate, HorseMapper mapper) {
        this.jdbcTemplate = jdbcTemplate; this.mapper = mapper;
    }

    @Override
    public List<Horse> getAll() {
        try {
            return jdbcTemplate.query(SQL_SELECT_ALL, this::mapRow);
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not query all horses", e);
        }
    }

    @Override
    public Horse getOneById(Long id) throws NotFoundException {
        //LOGGER.info("Get owner with id {}", id);
        if (id <= 0){
            throw new IllegalArgumentException("id must best be greater than zero");
        }

        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        List<Horse> horses = jdbcTemplate.query(sql, this::mapRow, id);
        if (horses.isEmpty()) throw new NotFoundException(String.format("Could not find owner with id %s", id));
        return horses.get(0);
    }

    @Override
    public Horse save(HorseDto horseDto) {
        final String sql = "INSERT INTO " + TABLE_NAME +
                " (name)" +
                " VALUES (?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        HorseDto finalHorseDto = horseDto;
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, finalHorseDto.name());
            return stmt;
        }, keyHolder);
        horseDto = new HorseDto(((Number)keyHolder.getKeys().get("id")).longValue(), horseDto.name());
        return mapper.dtoToEntity(horseDto);
    }

    private Horse mapRow(ResultSet result, int rownum) throws SQLException {
        Horse horse = new Horse();
        horse.setId(result.getLong("id"));
        horse.setName(result.getString("name"));
        return horse;
    }
}
