package com.codecat.codecat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonIgnore
    @ManyToMany(mappedBy = "classes", cascade = CascadeType.REMOVE)
    private List<User> users;

    @OneToMany(mappedBy = "classes", cascade = CascadeType.REMOVE)
    private List<Problem> problemList;
}
