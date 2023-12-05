package com.example.studentapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class StudentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_Name")
    private String firstName;

    @Column(name = "last_Name")
    private String lastName;

    @Column(name = "git_hub")
    private String gitHub;

    @Column(name = "start")
    private String start;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentModel")
    private Set<TaskModel> tasks = new HashSet<>(); //w relacjach zawsze stosujemy Set
}
