package pl.krzysztofstuglik.myImage.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krzysztofstuglik.myImage.models.forms.RegisterForm;
import pl.krzysztofstuglik.myImage.models.services.AuthService;

@RestController
@RequestMapping("rest")
public class UserRestController {

    private final AuthService authService;

    @Autowired
    public UserRestController(AuthService authService) {
        this.authService = authService;
    }

    @Value("${api.key}")
    String apiKey;

    @GetMapping(value = "/user", produces = "application/json")
    public ResponseEntity getAllUser(@RequestHeader("Api-key") String key) {
        if (!key.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Key do not exist");
        }
        return ResponseEntity.ok(authService.getAllUsers());
    }

    @GetMapping(value = "/user/{username}", produces = "application/json")
    public ResponseEntity getUserByUsername(@PathVariable("username") String username,
                                            @RequestHeader("Api-key") String key) {
        if (!key.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Key do not exist");
        }
        return authService.findByUsername(username)
                .map(s -> ResponseEntity.ok(s))
                .orElseGet(() ->
                        ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping(value = "/user", consumes = "application/json")
    public ResponseEntity addUser(@RequestBody RegisterForm registerForm,
                                  @RequestHeader("Api-key") String key) {
        if (!key.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Key do not exist");
        }
        return authService.tryToRegister(registerForm)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }

}
