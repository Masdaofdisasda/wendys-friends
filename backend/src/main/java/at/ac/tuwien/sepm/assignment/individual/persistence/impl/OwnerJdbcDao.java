package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.mapper.OwnerMapper;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository("OwnerJdbcDao")
public class OwnerJdbcDao implements OwnerDao {
    private static final String TABLE_NAME = "owner";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final Logger log = LoggerFactory.getLogger(OwnerJdbcDao.class);

    private final JdbcTemplate jdbcTemplate;
    private final OwnerMapper mapper;

    public OwnerJdbcDao(JdbcTemplate jdbcTemplate, OwnerMapper mapper) {
        this.jdbcTemplate = jdbcTemplate; this.mapper = mapper;
    }

    @Override
    public List<Owner> getAll() {
        log.trace("getAll()");
        return jdbcTemplate.query(SQL_SELECT_ALL, this::mapRow);
    }

    @Override
    public void createOwner(OwnerDto ownerDto){
        log.trace("createOwner()", ownerDto);
        final String sql = "INSERT INTO " + TABLE_NAME +
                " (givenname, surname, email)" +
                " VALUES (?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con ->{
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, ownerDto.givenname());
            stmt.setString(2, ownerDto.surname());
            stmt.setString(3, ownerDto.email());
            log.debug(stmt.toString());
            return stmt;
        }, keyHolder);
        log.debug("owner saved in database");
    }

    @Override
    public Owner getOwnerById(Long id){
        log.trace("getOwnerById()", id);

        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        List<Owner> owners = jdbcTemplate.query(con -> {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1,id);
            log.debug(stmt.toString());
            return stmt;
        }, this::mapRow);
        if (owners.isEmpty()) throw new NotFoundException("Could not find owner with id" + id);
        log.debug("owner found");
        return owners.get(0);
    }

    @Override
    public List<Owner> getOwner(String searchText){
        log.trace("getOwner()", searchText);
        final String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE UPPER(SURNAME) LIKE ? OR UPPER(GIVENNAME) LIKE ?";
        List<Owner> owners = jdbcTemplate.query(con  -> {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, "%"+searchText.toUpperCase()+"%");
            stmt.setString(2, "%"+searchText.toUpperCase()+"%");
            log.debug(stmt.toString());
            return stmt;
        }, this::mapRow);
        log.debug("owner(s) were found");
        return owners;
    }

    private Owner mapRow(ResultSet result, int rownum) throws SQLException {
        Owner owner = new Owner();
        owner.setId(result.getLong("id"));
        owner.setGivenname(result.getString("givenname"));
        owner.setSurname(result.getString("surname"));
        owner.setEmail(result.getString("email"));
        return owner;
    }
}
