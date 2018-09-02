package pl.krzysztofstuglik.myImage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        model.addAttribute("changePage", new ChangePage(postService.findAllOrderedByDataDesc(page)));
        model.addAttribute("userObject", sessionService);
        return "home";
    }

    @GetMapping("/profile")
    public String login(Model model,
                        RedirectAttributes redirectAttributes) {
        model.addAttribute("userObject", sessionService);
        if (!sessionService.isLogin()){
            redirectAttributes.addFlashAttribute("notShowProfile", "Aby zobaczyć swój profil musisz się zalogować");
            return "redirect:/login";
        }
        return "profile";
    }

    @GetMapping("home/DateAsc")
    public String byDataAsc(@RequestParam(defaultValue = "0") int page,
                           Model model){
        model.addAttribute("changePage", new ChangePage(postService.findAllOrderedByDataAsc(page)));
        model.addAttribute("userObject", sessionService);
        return "home";
    }

    @GetMapping("home/LikesDesc")
    public String byLikesDesc(@RequestParam(defaultValue = "0") int page,
                           Model model){
        model.addAttribute("changePage", new ChangePage(postService.findAllOrderByLikesDesc(page)));
        model.addAttribute("userObject", sessionService);
        return "home";
    }

    @GetMapping("home/LikesAsc")
    public String byLikesAsc(@RequestParam(defaultValue = "0") int page,
                              Model model){
        model.addAttribute("changePage", new ChangePage(postService.findAllOrderByLikesAsc(page)));
        model.addAttribute("userObject", sessionService);
        return "home";
    }

    @GetMapping("home/CommentsDesc")
    public String byCommentsDesc(@RequestParam(defaultValue = "0") int page,
                              Model model){
        model.addAttribute("changePage", new ChangePage(postService.findAllOrderByCommentsDesc(page)));
        model.addAttribute("userObject", sessionService);
        return "home";
    }

    @GetMapping("home/CommentsAsc")
    public String byCommentsAsc(@RequestParam(defaultValue = "0") int page,
                              Model model){
        model.addAttribute("changePage", new ChangePage(postService.findAllOrderByCommentsAsc(page)));
        model.addAttribute("userObject", sessionService);
        return "home";
    }
}
