package com.codecat.codecat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    private String assessmentCode;

    private String assessmentTitle;

    private String deadline;

    private String duration;

    private String workMode;

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.REMOVE)
    private List<Problem> problemList;
}
