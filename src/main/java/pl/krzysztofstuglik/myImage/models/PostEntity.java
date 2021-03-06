package pl.krzysztofstuglik.myImage.models;

import lombok.Data;
import pl.krzysztofstuglik.myImage.models.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
@Data
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private int id;
    private String title;
    private String content;

    @Column(name = "creation_time")
    private LocalDateTime creationDate;

    @Column(name = "number_comments")
    private int numberOfComments;

    @Column(name = "number_likes")
    private int numberOfLikes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
}
