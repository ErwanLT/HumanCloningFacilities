package com.erwan.human.domaine.kamino;

import com.erwan.human.reference.CloneType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CloneByCategorie {
    private CloneType type;
    private Long number;
}
