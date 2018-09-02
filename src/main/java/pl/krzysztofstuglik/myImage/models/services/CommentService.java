package pl.krzysztofstuglik.myImage.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krzysztofstuglik.myImage.models.CommentEntity;
import pl.krzysztofstuglik.myImage.models.PostEntity;
import pl.krzysztofstuglik.myImage.models.UserEntity;
import pl.krzysztofstuglik.myImage.models.forms.CommentForm;
import pl.krzysztofstuglik.myImage.models.repositories.CommentRepository;

import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final SessionService sessionService;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public CommentService(CommentRepository commentRepository, SessionService sessionService, PostService postService, UserService userService) {
        this.commentRepository = commentRepository;
        this.sessionService = sessionService;
        this.postService = postService;
        this.userService = userService;
    }

    public void addComment(CommentForm commentForm, int postId){
        CommentEntity commentEntity = createCommentEntity(commentForm, postId);
        commentRepository.save(commentEntity);
        postService.growingNumberOfComments(postId);
        userService.growingNumberOfUserComments();
    }

    private CommentEntity createCommentEntity(CommentForm commentForm, int postId) {
        CommentEntity commentEntity = new CommentEntity();
        PostEntity postEntity = new PostEntity();
        UserEntity userEntity = new UserEntity();

        postEntity.setId(postId);
        userEntity.setId(sessionService.getUserEntity().getId());

        commentEntity.setText(commentForm.getText());
        commentEntity.setPost(postEntity);
        commentEntity.setUser(userEntity);
        return commentEntity;
    }

    public Optional<CommentEntity> getCommentById(int commentId){
        return commentRepository.findById(commentId);
    }

    public void deleteComment(int postId, int commentId){
        commentRepository.deleteById(commentId);
        postService.decliningNumberOfComments(postId);
        userService.decliningNumberOfUserComments();
    }

    public void addLike(int postId){
        postService.growingNumberOfLikes(postId);
    }

    public void addDisLike(int postId){
        postService.decliningNumberOfLikes(postId);
    }
}
