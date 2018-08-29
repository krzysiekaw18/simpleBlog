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
import pl.krzysztofstuglik.myImage.models.UserEntity;
import pl.krzysztofstuglik.myImage.models.forms.CommentForm;
import pl.krzysztofstuglik.myImage.models.services.AuthService;
import pl.krzysztofstuglik.myImage.models.services.CommentService;
import pl.krzysztofstuglik.myImage.models.services.PostService;
import pl.krzysztofstuglik.myImage.models.services.SessionService;

import java.util.Optional;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final SessionService sessionService;
    private final PostService postService;
    private final AuthService authService;

    @Autowired
    public CommentController(CommentService commentService, SessionService sessionService, PostService postService, PostService postService1, AuthService authService) {
        this.commentService = commentService;
        this.sessionService = sessionService;
        this.postService = postService1;
        this.authService = authService;
    }

    @PostMapping("/comment/{postId}")
    public String comment(@PathVariable("postId") int postId,
                          @ModelAttribute("commentForm") CommentForm commentForm,
                          RedirectAttributes redirectAttributes) {
        commentService.addComment(commentForm, postId);
        redirectAttributes.addFlashAttribute("commentSuccess", "Twój komentarz został dodany");
        return "redirect:/post/" + postId;
    }


}