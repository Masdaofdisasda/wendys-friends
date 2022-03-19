package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository("HorseJdbcDao")
public class HorseJdbcDao implements HorseDao {
    private static final String TABLE_NAME = "horse";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final Logger log = LoggerFactory.getLogger(HorseJdbcDao.class);

    private final JdbcTemplate jdbcTemplate;
    private final HorseMapper mapper;

    public HorseJdbcDao(JdbcTemplate jdbcTemplate, HorseMapper mapper) {
        this.jdbcTemplate = jdbcTemplate; this.mapper = mapper;
    }

    @Override
    public List<Horse> getAll() throws PersistenceException {
        log.trace("get all horses");
        try {
            return jdbcTemplate.query(SQL_SELECT_ALL, this::mapRow);
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not query all horses", e);
        }
    }

    @Override
    public Horse getOneById(Long id) throws NotFoundException {
        log.trace("Get horse with id {}", id);

        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        List<Horse> horses = jdbcTemplate.query(sql, this::mapRow, id);
        if (horses.isEmpty()) throw new NotFoundException("Could not find owner with id" + id);
        log.debug("horse found");
        return horses.get(0);
    }

    @Override
    public void saveHorse(HorseDto horseDto) {
        log.trace("saveHorse()");
        final String sql = "INSERT INTO " + TABLE_NAME +
                " (name, description, birthdate, gender, owner)" +
                " VALUES (?,?,?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        HorseDto finalHorseDto = horseDto;
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, finalHorseDto.name());
            stmt.setString(2, finalHorseDto.description());
            stmt.setDate(3, Date.valueOf(finalHorseDto.birthdate()));
            stmt.setString(4, finalHorseDto.gender());
            stmt.setString(5, finalHorseDto.owner());
            log.debug(stmt.toString());
            return stmt;
        }, keyHolder);
        log.debug("horse saved in database");
    }

    public void updateHorse(HorseDto horseDto){
        log.trace("updateHorse()" + horseDto.id());
        final String sql = "UPDATE " + TABLE_NAME +
                " SET "+ "name=? " + ", description=? " +", birthdate=? " +", gender=? " +", owner=? " +
                " WHERE id=?;";

        int i = jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, horseDto.name());
            stmt.setString(2, horseDto.description());
            stmt.setDate(3, Date.valueOf(horseDto.birthdate()));
            stmt.setString(4, horseDto.gender());
            stmt.setString(5, horseDto.owner());
            stmt.setLong(6, horseDto.id());
            log.debug(stmt.toString());
            return stmt;
        });
        if (i == 0) throw new NotFoundException("Horse does not exist yet");
        log.debug("horse changes are saved");
    }

    private Horse mapRow(ResultSet result, int rownum) throws SQLException {
        log.trace("map horse " + result.getString("name") + " to entity");
        Horse horse = new Horse();
        horse.setId(result.getLong("id"));
        horse.setName(result.getString("name"));
        horse.setDescription(result.getString("description"));
        horse.setBirthdate(result.getDate("birthdate"));
        horse.setGender(result.getString("gender"));
        horse.setOwner(result.getString("owner"));
        return horse;
    }
}
