package MCcrew.Coinportal.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CoinComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String coinSymbol;
    private String nickname;
    private String content;

    // 05.08 추가
    private int commentGroup;
    private int level;

    @CreatedDate
    private LocalDateTime createdAt;
    private int upCnt;
    private int downCnt;
    private int reportCnt;
    // A:active, D:deleted, R:reported
    private char status;

    @PrePersist
    public void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public CoinComment(Long id, Long userId, String coinSymbol, String nickname, String content,
                       int commentGroup, int level,
                       LocalDateTime createdAt, int upCnt, int downCnt, int reportCnt, char status) {
        this.id = id;
        this.userId = userId;
        this.coinSymbol = coinSymbol;
        this.nickname = nickname;
        this.content = content;
        this.commentGroup = commentGroup;
        this.level = level;
        this.createdAt = createdAt;
        this.upCnt = upCnt;
        this.downCnt = downCnt;
        this.reportCnt = reportCnt;
        this.status = status;
    }
}
