package com.codecat.codecat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String assessmentTitle;

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.REMOVE)
    private List<Problem> problem;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "classes_id", referencedColumnName = "id")
    private Classes classes;
}
