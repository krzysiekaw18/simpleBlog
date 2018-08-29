package pl.krzysztofstuglik.myImage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.krzysztofstuglik.myImage.models.PostEntity;
import pl.krzysztofstuglik.myImage.models.forms.CommentForm;
import pl.krzysztofstuglik.myImage.models.forms.PostForm;
import pl.krzysztofstuglik.myImage.models.services.CommentService;
import pl.krzysztofstuglik.myImage.models.services.PostService;
import pl.krzysztofstuglik.myImage.models.services.SessionService;

import java.util.Optional;

@Controller
public class PostController {

    private final PostService postService;
    private final SessionService sessionService;
    private final CommentService commentService;

    @Autowired
    public PostController(PostService postService, SessionService sessionService, CommentService commentService) {
        this.postService = postService;
        this.sessionService = sessionService;
        this.commentService = commentService;
    }

    @GetMapping("/addPost")
    public String post(Model model,
                       RedirectAttributes redirectAttributes) {
        if (!sessionService.isLogin()) {
            redirectAttributes.addFlashAttribute("infoNotLogin", "Aby dodawać posty musisz być zalogowany");
            return "redirect:/login";
        }
        model.addAttribute("postForm", new PostForm());
        model.addAttribute("userObject", sessionService);
        return "add_post";
    }

    @PostMapping("/addPost")
    public String post(@ModelAttribute("postForm") PostForm postForm,
                       RedirectAttributes redirectAttributes) {
        postService.addPost(postForm);
        redirectAttributes.addFlashAttribute("postAddedInfo", "Twój post został dodany");
        return "redirect:/home";
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable("id") int id,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        if (!sessionService.isLogin()) {
            redirectAttributes.addFlashAttribute("infoNotLoginPost", "Aby poznać szczegóły posta oraz czytać i dodawać komentarze musisz się zalogować");
            return "redirect:/login";
        }
        model.addAttribute("postDetails", postService.getAllPostData(id));
        model.addAttribute("userObject", sessionService);
        model.addAttribute("commentForm", new CommentForm());
        return "post_info";
    }

    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable("id") int postId,
                             RedirectAttributes redirectAttributes) {
        if (sessionService.isLogin() && sessionService.getUserEntity().getPosts()
                .stream()
                .anyMatch(s -> s.getId() == postId)) {
            postService.deletePost(postId);
            redirectAttributes.addFlashAttribute("postDeleted", "Twój post został usunięty");
            return "redirect:/home";
        }
        return "redirect:/post/" + postId;
    }

    @GetMapping("/editPost/{id}")
    public String editPost(@PathVariable("id") int postId,
                           Model model) {
        Optional<PostEntity> postEntityOptional = postService.getPost(postId);
        if (postEntityOptional.isPresent()) {
            postService.updatePost(postEntityOptional);
            model.addAttribute("userObject", sessionService);
            model.addAttribute("postForm", postEntityOptional);
            model.addAttribute("postUpdated", "Post został zmieniony");
            return "add_post";
        }
        return "redirect:/post/" + postId;
    }
}