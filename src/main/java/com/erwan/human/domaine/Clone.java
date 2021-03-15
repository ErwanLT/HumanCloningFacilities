package com.erwan.human.domaine;

import com.erwan.human.reference.CloneType;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ApiModel
public class Clone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @ApiModelProperty(value = "The generated ID when saved in database",
            name = "ID",
            dataType = "Long",
            required = false,
            position = 0)
    private Long id;

    @ApiModelProperty( value = "The birthplace of my clone",
            name = "birthPlace",
            dataType = "String",
            required = false,
            position = 1
    )
    private final String birthPlace = "Kamino";

    @NotNull
    @ApiModelProperty( value = "The code of my clone",
            name = "codeName",
            dataType = "String",
            required = true,
            position = 2
    )
    private String codeName;

    @NotNull
    @Enumerated
    @ApiModelProperty( value = "The type of my clone",
            name = "type",
            dataType = "String",
            required = true,
            position = 3,
            allowableValues = "flametrooper, medic, gunner, scoot, jetpack"
    )
    private CloneType type;

    @Nullable
    @ApiModelProperty( value = "The platoon of my clone",
            name = "platoon",
            dataType = "Integer",
            required = false,
            position = 4
    )
    private int platoon;

    @ApiModelProperty( value = "The platoon of my clone",
            name = "platoon",
            dataType = "Integer",
            required = false,
            position = 5
    )
    private String affiliation = "Galactic Republic";
}
