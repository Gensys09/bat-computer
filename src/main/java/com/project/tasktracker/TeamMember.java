package com.project.tasktracker;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

//entity class to represent a TeamMember
@Entity
public class TeamMember {
    private @Id @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    private String role;

    public TeamMember() {}

    TeamMember(String firstName, String lastName, String role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    //getters and setters

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public void setName(String name) {
        String[] names = name.split(" ");
        this.firstName = names[0];
        this.lastName = names[1];
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        //pattern variable (Java 14 feature)
        if (!(o instanceof TeamMember teamMember)){
            return false;
        }
        return Objects.equals(this.id, teamMember.id) && Objects.equals(this.firstName, teamMember.firstName)
                && Objects.equals(this.lastName, teamMember.lastName) && Objects.equals(this.role, teamMember.role);
    }

    //when you override equals, you should also override hashCode
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName, this.role);
    }

    public String toString() {
        return "TeamMember{" + "id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName + '\'' + "role='" + this.role + '\'' + '}';
    }
    //TeamMember{id=1, firstName='Bruce', lastName='Wayne', role='Detective'}

}
