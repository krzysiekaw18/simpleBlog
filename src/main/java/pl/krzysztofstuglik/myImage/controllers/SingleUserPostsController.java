package pl.krzysztofstuglik.myImage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.krzysztofstuglik.myImage.models.UserEntity;
import pl.krzysztofstuglik.myImage.models.services.AuthService;
import pl.krzysztofstuglik.myImage.models.services.PostService;
import pl.krzysztofstuglik.myImage.models.services.SessionService;

import java.util.Optional;

@Controller
public class SingleUserPostsController {

    private final AuthService authService;
    private final PostService postService;
    private final SessionService sessionService;

    @Autowired
    public SingleUserPostsController(AuthService authService, PostService postService, SessionService sessionService) {
        this.authService = authService;
        this.postService = postService;
        this.sessionService = sessionService;
    }

    @GetMapping("/blog/{username}")
    public String postsSingleUser(@PathVariable("username") String username,
                                  Model model){
        Optional<UserEntity> user = authService.findByUsername(username);
        if (user.isPresent()){
            UserEntity userEntity = user.get();
        }
        model.addAttribute("userObject", sessionService);
        return "/users_posts";
    }
}
