package pl.krzysztofstuglik.myImage.models;

import lombok.Data;
import pl.krzysztofstuglik.myImage.models.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Data
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Column(name = "add_comment")
    private LocalDateTime creationDateComment;
}
