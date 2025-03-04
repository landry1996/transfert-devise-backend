package com.landry.transfertdedevise.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    USER_READ("user:read"),       // Permission pour lire les informations de l'utilisateur
    USER_UPDATE("user:update"),   // Permission pour mettre à jour les informations de l'utilisateur
    USER_CREATE("user:create"),   // Permission pour créer un nouvel utilisateur
    USER_DELETE("user:delete");   // Permission pour supprimer un utilisateur

    @Getter
    private final String permission;
}