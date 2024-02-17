package tasks.manager.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import tasks.manager.api.records.Ping;

@RestController
public class PingController {

    @GetMapping("/ping")
    public Ping ping() {
        return new Ping(true);
    }
}
