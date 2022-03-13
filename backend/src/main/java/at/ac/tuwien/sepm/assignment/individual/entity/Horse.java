package at.ac.tuwien.sepm.assignment.individual.entity;

import java.util.Date;

public class Horse {
    private Long id;
    private String name;
    private String description;
    private Date birthdate;
    private int sex;
    private String owner;

    public Horse(Long id, String name, String description, Date birthdate, int sex, String owner ){
        this.id = id;
        this.name = name;
        this.description = description;
        this.birthdate = birthdate;
        this.sex = sex;
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

    public Date getBirthdate(){return birthdate;}

    public int getSex() {return sex;}

    public String getOwner(){return owner;}

}
