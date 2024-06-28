package pl.falynsky.hospital_authorization_service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/1")
    public String demo1() {
        return "1";
    }

    @GetMapping("/2")
    public String demo2() {
        return "2";
    }
}
