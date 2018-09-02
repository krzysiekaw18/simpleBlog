package pl.krzysztofstuglik.myImage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.krzysztofstuglik.myImage.models.PostEntity;
import pl.krzysztofstuglik.myImage.models.services.PostService;
import pl.krzysztofstuglik.myImage.models.services.SessionService;
import pl.krzysztofstuglik.myImage.utils.ChangePage;

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
    public String welcome() {
        return "welcome_page";
    }

    @GetMapping("/home")
    public String home(@RequestParam(defaultValue = "0") int page,
                       Model model) {
        model.addAttribute("changePage", new ChangePage(postService.findAllOrderedByDataPageable(page)));
        model.addAttribute("userObject", sessionService);
        return "home";
    }

    @GetMapping("/profile")
    public String login(Model model) {
        model.addAttribute("userObject", sessionService);
        return "profile";
    }
}
