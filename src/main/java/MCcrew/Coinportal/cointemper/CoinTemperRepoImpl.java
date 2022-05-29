package MCcrew.Coinportal.cointemper;

import MCcrew.Coinportal.domain.CoinComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoinTemperRepoImpl extends JpaRepository<CoinComment, Long> {
    Page<CoinComment> findByLevelAndCoinSymbolOrderByCreatedAtDesc(int level, String symbol, Pageable pageable);
    // 인기 댓글
    Page<CoinComment> findByLevelAndCoinSymbolOrderByUpCntDescCreatedAtDesc(int level, String symbol, Pageable pageable);
    // 키워드로 댓글 검색
    Page<CoinComment> findByCoinSymbolAndContentContainingOrderByCreatedAtDesc(String symbol, String keyword, Pageable pageable);
    // 닉네임으로 댓글 검색
    Page<CoinComment> findByCoinSymbolAndAndNicknameOrderByCreatedAtDesc(String symbol, String nickname, Pageable pageable);
    List<CoinComment> findByCommentGroupAndCoinSymbolOrderByCreatedAtDesc(int group, String symbol);
}
