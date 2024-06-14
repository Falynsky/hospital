package pl.falynsky.hospital.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/securedDemo")
public class DemoSecuredController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello, secured!";
    }

    @RequestMapping("/bye")
    public String bye() {
        return "Bye, secured!";
    }
}
