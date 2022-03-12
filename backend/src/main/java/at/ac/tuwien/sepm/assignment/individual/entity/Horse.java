package at.ac.tuwien.sepm.assignment.individual.entity;

public class Horse {
    private Long id;
    private String name;

    public Horse(Long id, String name){
        this.id = id;
        this.name = name;
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

}
