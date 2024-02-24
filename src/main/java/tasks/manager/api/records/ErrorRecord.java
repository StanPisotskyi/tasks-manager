package tasks.manager.api.records;

import java.util.Map;

public record ErrorRecord(Map<String, String> errors) {
}
