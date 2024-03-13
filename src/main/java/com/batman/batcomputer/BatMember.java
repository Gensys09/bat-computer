package com.batman.batcomputer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

//entity class to represent a BatMember
@Entity
public class BatMember {
    private @Id @GeneratedValue Long id;
    private String name;
    private String role;

    public BatMember() {}

    BatMember(String name, String role){
        this.name = name;
        this.role = role;
    }

    //getters and setters

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
        if (!(o instanceof BatMember batMember)){
            return false;
        }
        return id.equals(batMember.id);
    }

    //when you override equals, you should also override hashCode
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.role);
    }

    public String toString() {
        return "BatMember{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
    }
    //BatMember{id=1, name='Bruce Wayne', role='Batman'}


}
