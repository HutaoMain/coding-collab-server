package com.codecat.codecat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "activity_tracker")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private Long testCaseId;

    private Integer points;

    private String compiledCode;

    private String expectedOutput;

    private String problemConstraint;
}
