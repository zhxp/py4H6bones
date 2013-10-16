package zx.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.Map;

public enum Role implements GrantedAuthority {
    NONE(""),
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    ;

    private static Map<String, Role> ROLE_MAP = new HashMap<>();

    static {
        for (Role role : Role.values()) {
            ROLE_MAP.put(role.roleName, role);
        }
    }

    public static Role fromRoleName(final String roleName) {
        Role role = ROLE_MAP.get(roleName);
        if (role == null) {
            role = NONE;
        }
        return role;
    }

    private final String roleName;

    private Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}
