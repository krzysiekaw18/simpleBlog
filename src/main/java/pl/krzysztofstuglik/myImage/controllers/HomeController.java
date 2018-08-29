package pl.krzysztofstuglik.myImage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.krzysztofstuglik.myImage.models.services.PostService;
import pl.krzysztofstuglik.myImage.models.services.SessionService;

@Controller
public class HomeController {

    private final SessionService sessionService;
    private final PostService postService;


    @Autowired
    public HomeController(SessionService sessionService, PostService postService) {
        this.sessionService = sessionService;
        this.postService = postService;
    }

    @GetMapping("/BestBlog")
    public String welcome(){
        return "welcome_page";
    }

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("userObject", sessionService);
        model.addAttribute("posts", postService.getAll());
        return "home";
    }

    @GetMapping("/profile")
    public String login(Model model){
        model.addAttribute("userObject", sessionService);
        return "profile";
    }
}
