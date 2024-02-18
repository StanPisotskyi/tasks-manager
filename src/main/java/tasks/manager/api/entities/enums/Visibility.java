package tasks.manager.api.entities.enums;

public enum Visibility {
    VISIBLE("VISIBLE"),
    HIDDEN("HIDDEN");

    private final String status;

    public static Visibility findByStatus(String status) {
        Visibility res = null;

        for (Visibility item : values()) {
            if (item.getStatus().equalsIgnoreCase(status)) {
                res = item;
                break;
            }
        }

        if (res == null) {
            throw new RuntimeException("Invalid value for tasks.manager.api.entities.enums.Visibility");
        }

        return res;
    }

    Visibility(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
