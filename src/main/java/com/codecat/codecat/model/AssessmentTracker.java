package com.codecat.codecat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "assessment_tracker")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long assessmentId;

    private String deadline;

    private String duration;

    private String email;

    private String endTime;
}
