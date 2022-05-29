package MCcrew.Coinportal.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="comment")
public class Comment { // 게시글 댓글
    @Id @GeneratedValue
    private Long id;       // 댓글 디비 생성 pk

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;      // 댓글단 게시글

    @Column(name = "user_id_ref")
    private Long userId;

    @Column(length = 15)
    private String nickname;
    @Column(length = 20)
    private String password;
    @Lob
    private String content;
    private int commentGroup;
    private int level;
    private int reportCnt;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updateAt;
    // A:active, D:deleted, R:reported
    private char status;

    @PrePersist
    public void createdAt() {
        this.createdAt = LocalDateTime.now().plusHours(9);
        this.updateAt = LocalDateTime.now().plusHours(9);
        this.reportCnt = 0;
        this.status = 'A';
    }

    @PreUpdate
    public void updatedAt() {
        this.updateAt = LocalDateTime.now().plusHours(9);
    }

    @Builder
    public Comment(Long id, Post post, Long userId, String nickname, String password, String content, int commentGroup, int level, int reportCnt, LocalDateTime createdAt, LocalDateTime updateAt, char status) {
        this.id = id;
        this.post = post;
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.content = content;
        this.commentGroup = commentGroup;
        this.level = level;
        this.reportCnt = reportCnt;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.status = status;
    }
}
