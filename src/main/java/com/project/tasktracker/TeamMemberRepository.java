package com.project.tasktracker;

import org.springframework.data.jpa.repository.JpaRepository;

interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

}
