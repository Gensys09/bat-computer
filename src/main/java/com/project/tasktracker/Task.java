package com.project.tasktracker;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "TASK")
public class Task {
    private @Id
    @GeneratedValue Long id;

    private String description;
    private String status;
    public Task() {}
    Task(String description, String status) {
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
        if (!(o instanceof Task task)) {
            return false;
        }

        return Objects.equals(this.id, task.id) &&
                Objects.equals(this.description, task.description) &&
                Objects.equals(this.status, task.status);

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

