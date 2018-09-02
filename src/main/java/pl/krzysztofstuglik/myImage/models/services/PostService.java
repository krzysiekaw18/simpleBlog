package pl.krzysztofstuglik.myImage.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.krzysztofstuglik.myImage.models.PostEntity;
import pl.krzysztofstuglik.myImage.models.UserEntity;
import pl.krzysztofstuglik.myImage.models.forms.PostForm;
import pl.krzysztofstuglik.myImage.models.repositories.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final SessionService sessionService;
    private final UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, SessionService sessionService, UserService userService) {
        this.postRepository = postRepository;
        this.sessionService = sessionService;
        this.userService = userService;
    }

    public void addPost(PostForm postForm) {
        PostEntity postEntity = createEntityFromForm(postForm);
        userService.growingNumberOfUserPosts();
        postRepository.save(postEntity);
    }

    private PostEntity createEntityFromForm(PostForm postForm) {
        PostEntity postEntity = new PostEntity();
        postEntity.setId(postForm.getId());
        postEntity.setTitle(postForm.getTitle());
        postEntity.setContent(postForm.getContent());
        postEntity.setUser(sessionService.getUserEntity());
        return postEntity;
    }

    public List<PostEntity> getAllPostByLoggedUser(){
        return postRepository.findAllByUser_Id(sessionService.getUserEntity().getId());
    }

    public PostEntity getAllPostData(int id) {
        return postRepository.findById(id).get();
    }

    public Optional<PostEntity> getPostById(int id) {
        return postRepository.findById(id);
    }

    public Iterable<PostEntity> getAllPosts(){
        return postRepository.findAllByOrderByIdDesc();
    }

    public PostForm updatePost(Optional<PostEntity> entityOptional) {
        PostForm postForm = new PostForm();
        postForm.setTitle(entityOptional.get().getTitle());
        postForm.setContent(entityOptional.get().getContent());
        return postForm;
    }

    public void deletePost(int postId) {
        postRepository.deleteById(postId);
        userService.decliningNumberOfUserPosts();
    }


    public Page<PostEntity> findAllOrderedByDataDesc(int page) {
        return postRepository.findAllByOrderByIdDesc(PageRequest.of(subtractPageByOne(page), 5));
    }

    public Page<PostEntity> findAllOrderedByDataAsc(int page) {
        return postRepository.findAllByOrderByIdAsc(PageRequest.of(subtractPageByOne(page), 5));
    }

    public Page<PostEntity> findByUserOrderedByDatePageable(UserEntity userEntity, int page) {
        return postRepository.findByUserOrderByIdDesc(userEntity, (PageRequest.of(subtractPageByOne(page), 5)));
    }

    public Page<PostEntity> findAllOrderByLikesDesc(int page){
        return postRepository.findAllByOrderByNumberOfLikesDesc(PageRequest.of(subtractPageByOne(page), 5));
    }

    public Page<PostEntity> findAllOrderByLikesAsc(int page){
        return postRepository.findAllByOrderByNumberOfLikesAsc(PageRequest.of(subtractPageByOne(page), 5));
    }

    public Page<PostEntity> findAllOrderByCommentsDesc(int page){
        return postRepository.findAllByOrderByNumberOfCommentsDesc(PageRequest.of(subtractPageByOne(page), 5));
    }

    public Page<PostEntity> findAllOrderByCommentsAsc(int page){
        return postRepository.findAllByOrderByNumberOfCommentsAsc(PageRequest.of(subtractPageByOne(page), 5));
    }

    private int subtractPageByOne(int page) {
        return (page < 1) ? 0 : page - 1;
    }

    public void growingNumberOfComments(int postId){
        PostEntity postEntity = getAllPostData(postId);
        postEntity.setNumberOfComments(postEntity.getNumberOfComments() + 1);
        postRepository.save(postEntity);
    }

    public void decliningNumberOfComments(int postId) {
        PostEntity postEntity = getAllPostData(postId);
        postEntity.setNumberOfComments(postEntity.getNumberOfComments() - 1);
        postRepository.save(postEntity);
    }

    public void growingNumberOfLikes(int postId){
        PostEntity postEntity = getAllPostData(postId);
        postEntity.setNumberOfLikes(postEntity.getNumberOfLikes() + 1);
        postRepository.save(postEntity);
    }

    public void decliningNumberOfLikes(int postId){
        PostEntity postEntity = getAllPostData(postId);
        postEntity.setNumberOfLikes(postEntity.getNumberOfLikes() - 1);
        if (postEntity.getNumberOfLikes() < 0){
            postEntity.setNumberOfLikes(0);
        }
        postRepository.save(postEntity);
    }

    public void savePost(PostEntity postEntity){
        postRepository.save(postEntity);
    }

    public void deletePostById(int id){
        postRepository.deleteById(id);
    }

}
