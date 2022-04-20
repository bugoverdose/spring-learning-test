package nextstep.helloworld.mvc.handler;

import nextstep.helloworld.mvc.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/method-argument")
public class MethodArgumentController {

    @GetMapping("/users")
    public ResponseEntity<List<User>> requestParam(String name) {
        List<User> users = Arrays.asList(
                new User(name, "email"),
                new User(name, "email")
        );
        return ResponseEntity.ok().body(users);
    }
    // requestParam(@RequestParam(name="name") String userName)

    // [
    //    {
    //        "id": null,
    //        "name": "hello",
    //        "email": "email"
    //    },
    //    {
    //        "id": null,
    //        "name": "hello",
    //        "email": "email"
    //    }
    //]

    @PostMapping("/users/body")
    public ResponseEntity requestBody(@RequestBody User user) {
        User newUser = new User(1L, user.getName(), user.getEmail());
        return ResponseEntity.created(URI.create("/users/" + newUser.getId())).body(newUser);
    }
    // {
    //    "id": 1,
    //    "name": "이름",
    //    "email": "email@email.com"
    //}
}