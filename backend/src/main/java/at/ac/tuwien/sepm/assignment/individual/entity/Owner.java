package at.ac.tuwien.sepm.assignment.individual.entity;

public class Owner {
    private Long id;
    private String givenname;
    private String surname;
    private String email;

    public Owner(Long id, String givenname, String surname, String email){
        this.id = id;
        this.givenname = givenname;
        this.surname = surname;
        this.email = email;
    }

    public Owner(){

    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getGivenname() {return givenname;}

    public void setGivenname(String givenname) {this.givenname = givenname;}

    public String getSurname() {return surname;}

    public void setSurname(String surname) {this.surname = surname;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}
}
