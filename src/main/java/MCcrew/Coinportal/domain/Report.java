package MCcrew.Coinportal.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Report {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_id")
    private CoinComment coinComment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String ip;

    @CreatedDate
    private LocalDateTime createdAt;

    @PrePersist
    public void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public Report(Long id, Post post, Comment comment, CoinComment coinComment, User user, String ip, LocalDateTime createdAt) {
        this.id = id;
        this.post = post;
        this.comment = comment;
        this.coinComment = coinComment;
        this.user = user;
        this.ip = ip;
        this.createdAt = createdAt;
    }
}
