package edu.snow.whiskipe;

public class User {
    private int id;
    private String username;
    private String firstname;
    private String lastname;


    public User(int newId, String newUsername, String newFirstName, String newLastName){
        this.id = newId;
        this.username = newUsername;
        this.firstname = newFirstName;
        this.lastname = newLastName;
    }

    public User(String newUsername, String newFirstName, String newLastName){
        this.username = newUsername;
        this.firstname = newFirstName;
        this.lastname = newLastName;
    }

    public User(){
        //create empty user
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
