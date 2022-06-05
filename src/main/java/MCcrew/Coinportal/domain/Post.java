package MCcrew.Coinportal.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="post")
public class Post {            // 게시글
    @Id @GeneratedValue
    private Long id;            // 디비생성 pk
    private Long userId;        // 게시글 작성자 id
    private int userPoint;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @Column(length= 15)
    private String userNickname; // 작성자 닉네임
    private String boardName;    // 게시판 종류 (ex: 자유게시판)
    @Column(length = 30)
    private String guestName; // 비회원이름
    @Column(length = 20)
    private String guestPwd;  // 비회원 pwd
    @Lob
    private String content;   // 글 내용
    private int upCnt;        // 좋아요 개수
    private int downCnt;      // 싫어요 개수
    private int viewCnt;      // 조회수
    private int reportCnt;    // 신고수
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // A:active, D:deleted, R:reported
    @Column(length = 2)
    private char status;       // 상태

    @JsonManagedReference
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Attachment> attachedFiles = new ArrayList<>();

    @PrePersist
    public void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void updatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public Post(Long id, Long userId, int userPoint, List<Comment> comments, String userNickname, String boardName,
                String guestName, String guestPwd, String content, int upCnt, int downCnt, int viewCnt, int reportCnt,
                LocalDateTime createdAt, LocalDateTime updatedAt, char status, List<Attachment> attachedFiles) {
        this.id = id;
        this.userId = userId;
        this.userPoint = userPoint;
        this.comments = comments;
        this.userNickname = userNickname;
        this.boardName = boardName;
        this.guestName = guestName;
        this.guestPwd = guestPwd;
        this.content = content;
        this.upCnt = upCnt;
        this.downCnt = downCnt;
        this.viewCnt = viewCnt;
        this.reportCnt = reportCnt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.attachedFiles = attachedFiles;
    }
}
