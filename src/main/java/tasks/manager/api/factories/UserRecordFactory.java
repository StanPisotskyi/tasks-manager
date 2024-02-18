package tasks.manager.api.factories;

import org.springframework.stereotype.Service;
import tasks.manager.api.entities.User;
import tasks.manager.api.records.UserRecord;

@Service
public class UserRecordFactory {
    public UserRecord create(User user) {
        return new UserRecord(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
    }
}
