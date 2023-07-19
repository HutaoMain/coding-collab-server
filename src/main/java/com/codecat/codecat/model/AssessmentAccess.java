package com.codecat.codecat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "assessment_access")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private Long classId;

    private Long assessmentId;

    private String email;
}
