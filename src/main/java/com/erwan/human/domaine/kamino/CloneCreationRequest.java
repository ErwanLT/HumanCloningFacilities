package com.erwan.human.domaine.kamino;

import com.erwan.human.reference.CloneType;
import lombok.Data;

@Data
public class CloneCreationRequest {
    private int platoon;
    private CloneType type;
}
