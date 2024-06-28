package pl.falynsky.hospital_authorization_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/securedDemo")
public class DemoSecuredController {

    @RequestMapping("/1")
    public String hello() {
        return "1, secured!";
    }

    @RequestMapping("/2")
    public String bye() {
        return "2, secured!";
    }
}
