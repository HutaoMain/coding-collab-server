package com.codecat.codecat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "classes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String classCode;

    private String courseCode;

    private String courseName;

    private String scheduleClass;

    private String startTime;

    private String endTime;

    @OneToMany(mappedBy = "classes", cascade = CascadeType.REMOVE)
    private List<Problem> problemList;
}
