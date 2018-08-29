package pl.krzysztofstuglik.myImage.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krzysztofstuglik.myImage.models.CommentEntity;
import pl.krzysztofstuglik.myImage.models.PostEntity;
import pl.krzysztofstuglik.myImage.models.UserEntity;
import pl.krzysztofstuglik.myImage.models.forms.CommentForm;
import pl.krzysztofstuglik.myImage.models.repositories.CommentRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final SessionService sessionService;

    @Autowired
    public CommentService(CommentRepository commentRepository, SessionService sessionService) {
        this.commentRepository = commentRepository;
        this.sessionService = sessionService;
    }

    public void addComment(CommentForm commentForm, int postId){
        CommentEntity commentEntity = createCommentEntity(commentForm, postId);
        commentRepository.save(commentEntity);
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

    public void deleteComment(int cemmentId){
        commentRepository.deleteById(cemmentId);
    }
}
