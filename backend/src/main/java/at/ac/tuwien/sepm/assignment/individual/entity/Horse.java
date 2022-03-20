package at.ac.tuwien.sepm.assignment.individual.entity;

import java.sql.Date;
import java.time.LocalDate;

public class Horse {
    private Long id;
    private String name;
    private String description;
    private LocalDate birthdate;
    private String gender;
    private String owner;
    private Long mom;
    private Long dad;

    public Horse(Long id, String name, String description, LocalDate birthdate, String gender, String owner, Long mom, Long dad ){
        this.id = id;
        this.name = name;
        this.description = description;
        this.birthdate = birthdate;
        this.gender = gender;
        this.owner = owner;
        this.mom = mom;
        this.dad = dad;
    }
    public Horse(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription(){ return description;}

    public void setDescription(String description) { this.description = description;}

    public LocalDate getBirthdate(){return birthdate;}

    public void setBirthdate(Date birthdate){ this.birthdate = birthdate.toLocalDate();}

    public String getGender() {
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getOwner(){return owner;}

    public void setOwner( String owner){this.owner = owner;}

    public Long getMom(){return mom;}

    public void setMom( Long owner){this.mom = mom;}

    public Long getDad(){return dad;}

    public void setDad( Long owner){this.dad = dad;}

}
