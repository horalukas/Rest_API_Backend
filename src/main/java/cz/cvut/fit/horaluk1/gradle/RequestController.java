package cz.cvut.fit.horaluk1.gradle;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {

    @RequestMapping("/request")
    public String request() {
        System.out.println("Request successfully received!");
        return "Request successfully received!";
    }

}
