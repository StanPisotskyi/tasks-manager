package tasks.manager.api.entities.enums;

public enum Role {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String role;
    
    Role (String role) {
        this.role = role;
    }

    public static Role findByRole(String role) {
        Role res = null;

        for (Role item : values()) {
            if (item.getRole().equalsIgnoreCase(role)) {
                res = item;
                break;
            }
        }

        if (res == null) {
            throw new RuntimeException("Invalid value for tasks.manager.api.entities.enums.Role");
        }

        return res;
    }

    public String getRole() {
        return this.role;
    }
}
