package at.ac.tuwien.sepm.assignment.individual.entity;

import java.util.Date;

public class Horse {
    private Long id;
    private String name;
    private String description;
    private Date birthdate;
    private String gender;
    private String owner;

    public Horse(Long id, String name, String description, Date birthdate, String gender, String owner ){
        this.id = id;
        this.name = name;
        this.description = description;
        this.birthdate = birthdate;
        this.gender = gender;
        this.owner = owner;
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

    public Date getBirthdate(){return birthdate;}

    public void setBirthdate(Date birthdate){this.birthdate = birthdate;}

    public String getGender() {
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getOwner(){return owner;}

    public void setOwner( String owner){this.owner = owner;}

}
