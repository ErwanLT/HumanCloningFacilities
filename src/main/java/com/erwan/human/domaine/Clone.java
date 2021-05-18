package com.erwan.human.domaine;

import com.erwan.human.reference.CloneType;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class Clone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @Schema(description = "The generated ID when saved in database",
            name = "id",
            required = false)
    private Long id;

    @Schema(description = "The clone code name",
            name = "brithPlace",
            required = false,
            example = "Kamino")
    @Size(min = 3, max = 40)
    private final String birthPlace = "Kamino";

    @Schema(description = "The clone code name",
            name = "codeName",
            required = true)
    @NotNull
    private String codeName;

    @Schema(description = "The clone specialisation",
            name = "type",
            required = true)
    @NotNull
    @Enumerated
    private CloneType type;

    @Schema(description = "The clone's platoon",
            name = "platoon",
            required = false,
            example = "501")
    @Nullable
    private int platoon;

    @Schema(description = "The clone affilation",
            name = "affilation",
            required = false,
            minLength = 3,
            maxLength = 40,
            example = "Galactic Republic")
    private String affiliation = "Galactic Republic";
}
