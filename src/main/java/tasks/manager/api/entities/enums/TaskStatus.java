package tasks.manager.api.entities.enums;

public enum TaskStatus {
    NEW("NEW"),
    IN_PROGRESS("IN_PROGRESS"),
    TESTING("TESTING"),
    CLOSED("CLOSED");

    private final String status;

    public static TaskStatus findByStatus(String status) {
        TaskStatus res = null;

        for (TaskStatus item : values()) {
            if (item.getStatus().equalsIgnoreCase(status)) {
                res = item;
                break;
            }
        }

        if (res == null) {
            throw new RuntimeException("Invalid value for tasks.manager.api.entities.enums.TaskStatus");
        }

        return res;
    }

    TaskStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
