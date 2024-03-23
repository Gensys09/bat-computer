package com.batman.batcomputer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "OBJECTIVE")
public class Objective {
    private @Id
    @GeneratedValue Long id;

    private String description;
    private String status;
    public Objective() {}
    Objective(String description, String status) {
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof Objective objective)) {
            return false;
        }

        return Objects.equals(this.id, objective.id) &&
                Objects.equals(this.description, objective.description) &&
                Objects.equals(this.status, objective.status);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.description, this.status);
    }

    @Override
    public String toString() {
        return "Objective{" +
                "id=" + this.id +
                ", description='" + this.description + '\'' +
                ", status='" + this.status + '\'' +
                '}';
    }
}

