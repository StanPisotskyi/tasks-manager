package tasks.manager.api.factories;

import org.springframework.stereotype.Service;
import tasks.manager.api.entities.User;
import tasks.manager.api.records.UserRecord;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRecordFactory {
    public UserRecord create(User user) {
        return new UserRecord(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getRole());
    }

    public List<UserRecord> create(Iterable<User> users) {
        List<UserRecord> res = new ArrayList<>();

        for (User user : users) {
            res.add(this.create(user));
        }

        return res;
    }
}
