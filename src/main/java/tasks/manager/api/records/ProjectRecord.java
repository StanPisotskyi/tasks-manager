package tasks.manager.api.records;

import tasks.manager.api.entities.enums.Visibility;

import java.util.Date;

public record ProjectRecord(Long id, String title, String alias, Visibility status, Date createdAt, UserRecord createdBy) {
}
