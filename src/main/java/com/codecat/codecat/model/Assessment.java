package com.codecat.codecat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "assessment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assessmentTitle;

    private String deadline;

    private String duration;

    private String workMode;

    private Long classId;

    private Boolean isTake;

    private String timeAndDateOfAssessment;
}
