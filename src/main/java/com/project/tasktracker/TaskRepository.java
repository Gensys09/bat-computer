package com.project.tasktracker;

import org.springframework.data.jpa.repository.JpaRepository;

interface TaskRepository extends JpaRepository<Task, Long> {
}
