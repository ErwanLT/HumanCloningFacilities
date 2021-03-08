package com.erwan.human.domaine;

import com.erwan.human.reference.CloneType;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Clone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private final String birthPlace = "Kamino";

    @NotNull
    private String codeName;

    @NotNull
    @Enumerated
    private CloneType type;

    @Nullable
    private int platoon;

    private String affiliation = "Galactic Republic";
}
