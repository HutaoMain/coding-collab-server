package com.codecat.codecat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "problem")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String problemCode;

    private String problemName;

    private String problemDescription;

    private String programmingLanguage;

    private String problemConstraint;

    private String pattern;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.REMOVE)
    private List<TestCase> testCase;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "assessment_id", referencedColumnName = "id")
    private Assessment assessment;
}
