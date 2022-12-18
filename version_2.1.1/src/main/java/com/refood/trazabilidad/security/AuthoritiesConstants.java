package com.refood.trazabilidad.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String RESPONSABLE = "RESPONSABLE_NUCLEO";

    public static final String VOLUNTARIO = "VOLUNTARIO_NUCLEO";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
