package nextstep.helloworld.mvc.mapping;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/param-header")
public class ParamHeaderController {

    @GetMapping("/message")
    public ResponseEntity<String> message() {
        return ResponseEntity.ok().body("message");
    }

    @GetMapping(path="/message", params="name")
    public ResponseEntity<String> messageForParam(String name) {
        return ResponseEntity.ok().body(name);
    }

    @GetMapping(path="/message", headers="HEADER")
    public ResponseEntity<String> messageForHeader(@RequestHeader HttpHeaders header) {
        return ResponseEntity.ok().body(header.getFirst("HEADER"));
    }
}