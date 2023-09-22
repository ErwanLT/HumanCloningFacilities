package com.erwan.human.domaine.authentification;

import org.springframework.security.core.GrantedAuthority;

public enum AppUserRole implements GrantedAuthority {
    ROLE_EMPEROR, ROLE_KAMINOAIN;

    public String getAuthority() {
        return name();
    }

}
