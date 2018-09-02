package pl.krzysztofstuglik.myImage.controllers.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krzysztofstuglik.myImage.models.CommentEntity;
import pl.krzysztofstuglik.myImage.models.services.CommentService;

@RestController
@RequestMapping("rest")
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Value("${api.key}")
    String apiKey;

    @GetMapping(value = "/comment", produces = "application/json")
    public ResponseEntity allComments(@RequestHeader("Api-key") String key) {
        if (!key.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Key do not exist");
        }
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping(value = "/comment/{id}", produces = "application/json")
    public ResponseEntity getUserByUsername(@PathVariable("id") int id,
                                            @RequestHeader("Api-key") String key) {
        if (!key.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Key do not exist");
        }
        return commentService.getCommentById(id)
                .map(s -> ResponseEntity.ok(s))
                .orElseGet(() ->
                        ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping(value = "/comment", consumes = "application/json")
    public ResponseEntity saveComment(@RequestBody CommentEntity commentEntity,
                                      @RequestHeader("Api-key") String key) {
        if (!key.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Key do not exist");
        }
        commentService.saveComment(commentEntity);
        return ResponseEntity.ok(commentEntity);
    }

    @DeleteMapping(value = "/comment/{id}", produces = "application/json")
    public ResponseEntity deleteComment(@PathVariable("id") int id,
                                        @RequestHeader("Api-key") String key) {
        if (!key.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Key do not exist");
        }
        commentService.deleteCommentById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/comment", consumes = "application/json")
    public ResponseEntity updateComment(@RequestBody CommentEntity commentEntity,
                                        @RequestHeader("Api-key") String key) {
        if (!key.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Key do not exist");
        }
        commentService.saveComment(commentEntity);
        return ResponseEntity.ok(commentEntity);
    }


}
