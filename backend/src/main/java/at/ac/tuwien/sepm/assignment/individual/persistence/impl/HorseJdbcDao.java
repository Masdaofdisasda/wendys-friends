package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
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
    public List<Horse> getAll() {
        log.trace("get all horses");
        return jdbcTemplate.query(SQL_SELECT_ALL, this::mapRow);
    }

    @Override
    public Horse getHorseById(Long id) {
        log.trace("getHorseById()", id);

        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        List<Horse> horses = jdbcTemplate.query(con -> {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1, id);
            log.debug(stmt.toString());
            return stmt;
        }, this::mapRow);
        if (horses.isEmpty()) throw new NotFoundException("Could not find horse with id" + id);
        log.debug("horse found");
        return horses.get(0);
    }

    @Override
    public void createHorse(HorseDto horseDto) {
        log.trace("createHorse()", horseDto);
        final String sql = "INSERT INTO " + TABLE_NAME +
                " (name, description, birthdate, gender, owner, mom, dad)" +
                " VALUES (?,?,?,?,?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        HorseDto finalHorseDto = horseDto;
        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setValues(finalHorseDto, stmt);
            log.debug(stmt.toString());
            return stmt;
        }, keyHolder);
        log.debug("horse saved in database");
    }

    @Override
    public void updateHorse(HorseDto horseDto){
        log.trace("updateHorse()" + horseDto.id());
        final String sql = "UPDATE " + TABLE_NAME +
                " SET "+ "name=? " + ", description=? " +", birthdate=? " +", gender=? " +", owner=? " + ", mom=? " + ", dad=? " +
                " WHERE id=?;";

        int i = jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql);
            setValues(horseDto, stmt);
            stmt.setLong(8, horseDto.id());
            log.debug(stmt.toString() + horseDto.id());
            return stmt;
        });
        if (i == 0) throw new NotFoundException("horse does not exist yet");
        log.debug("horse changes are saved");
    }

    private void setValues(HorseDto horseDto, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, horseDto.name());
        stmt.setString(2, horseDto.description());
        stmt.setDate(3, Date.valueOf(horseDto.birthdate()));
        stmt.setString(4, horseDto.gender());
        if (horseDto.owner() == null) {
            stmt.setNull(5, Types.INTEGER);
        } else {
            stmt.setLong(5, horseDto.owner());
        }
        if (horseDto.mom() == null) {
            stmt.setNull(6, Types.INTEGER);
        } else {
            stmt.setLong(6, horseDto.mom());
        }
        if (horseDto.dad() == null) {
            stmt.setNull(7, Types.INTEGER);
        } else {
            stmt.setLong(7, horseDto.dad());
        }
    }

    @Override
    public void deleteHorse(Long id){
        log.trace("deleteHorse()",id);
        final String sql = "DELETE FROM " + TABLE_NAME +
                " WHERE id=?";
        int i = jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1, id);
            log.debug(stmt.toString());
            return stmt;
        });
        if (i == 0) throw new NotFoundException("horse does not exist");
        log.debug("horse was deleted");
        final String sql2 = "UPDATE " + TABLE_NAME + " SET MOM=NULL WHERE MOM=?;";
        final String sql3 = "UPDATE " + TABLE_NAME + " SET DAD=NULL WHERE DAD=?;";
        jdbcTemplate.update(con-> {
            PreparedStatement stmt = con.prepareStatement(sql2);
            stmt.setLong(1,id);
            log.debug(stmt.toString());
            return stmt;
        });
        jdbcTemplate.update(con-> {
            PreparedStatement stmt = con.prepareStatement(sql3);
            stmt.setLong(1,id);
            log.debug(stmt.toString());
            return stmt;
        });
        log.debug("parent horse was removed from children");
    }

    @Override
    public List<Horse> getFemaleHorse(String searchText){
        log.trace("getFemaleHorse()",searchText);
        final String sql = "SELECT * FROM "+ TABLE_NAME +" WHERE GENDER='f' AND NAME like ?";
        List<Horse> horses = jdbcTemplate.query(con -> {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, "%"+searchText+"%");
            log.debug(stmt.toString());
            return stmt;
        }, this::mapRow);
        log.debug("female horse(s) were found");
        return horses;
    }

    @Override
    public List<Horse> getMaleHorse(String searchText){
        log.trace("getMaleHorse()",searchText);
        final String sql = "SELECT * FROM "+ TABLE_NAME +" WHERE GENDER='m' AND UPPER(NAME) like ?";
        List<Horse> horses = jdbcTemplate.query(con -> {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, "%"+searchText.toUpperCase()+"%");
            log.debug(stmt.toString());
            return stmt;
        }, this::mapRow);
        log.debug("male horse(s) were found");
        return horses;
    }

    @Override
    public List<Horse> searchHorse(HorseDto horseDto){
        log.trace("searchHorse()", horseDto);
        final String and = " AND ";
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE" +
                " (UPPER(NAME) LIKE ?)" + and +
                "(UPPER(DESCRIPTION) LIKE ?)" + and +
                "(BIRTHDATE <= ?)" + and +
                "(GENDER LIKE ?)";
        if (horseDto.owner() != null){
            sql = sql + and + "(OWNER=?);";
        }else sql = sql + ";";

        String finalSql = sql;
        List<Horse> horses = jdbcTemplate.query(con -> {
            PreparedStatement stmt = con.prepareStatement(finalSql);
            if (horseDto.name() != null){
                stmt.setString(1, "%"+horseDto.name().toUpperCase()+"%");
            } else stmt.setString(1, "%");

            if (horseDto.description() != null){
                stmt.setString(2, "%"+horseDto.description().toUpperCase()+"% AND NOT DESCRIPTION=''");
            }else {
                stmt.setString(2, "%");
            }

            if (horseDto.birthdate() != null){
                stmt.setDate(3, Date.valueOf(horseDto.birthdate()));
            } else stmt.setDate(3, Date.valueOf(LocalDate.now()));

            if (horseDto.gender() != null){
                stmt.setString(4, horseDto.gender());
            } else stmt.setString(4, "%");

            if (horseDto.owner() != null){
                stmt.setLong(5, horseDto.owner());
            }
            log.debug(stmt.toString());
            return stmt;
        }, this::mapRow);
        log.debug("search could be performed");
        return horses;

    }

    private Horse mapRow(ResultSet result, int rownum) throws SQLException {
        Horse horse = new Horse();
        horse.setId(result.getLong("id"));
        horse.setName(result.getString("name"));
        horse.setDescription(result.getString("description"));
        horse.setBirthdate(result.getDate("birthdate"));
        horse.setGender(result.getString("gender"));
        horse.setOwner(result.getLong("owner"));
        horse.setMom(result.getLong("mom"));
        horse.setDad(result.getLong("dad"));
        return horse;
    }
}
