package com.landry.transfertdedevise.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public enum Role {
    USER(Set.of(
            Permission.USER_READ,
            Permission.USER_UPDATE,
            Permission.USER_CREATE,
            Permission.USER_DELETE
    ));

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

    /*
     * Remarques
     * Permissions : Vous pouvez ajuster les permissions associées à chaque rôle en fonction des besoins spécifiques de votre application et de votre entreprise.
     * Rôles personnalisés : Vous pouvez également ajouter des rôles spécifiques en fonction des projets ou des équipes au sein de votre entreprise.
     * Flexibilité : Assurez-vous que la structure de rôle reste flexible pour pouvoir intégrer de nouveaux rôles à mesure que l'entreprise évolue.
     */
}
