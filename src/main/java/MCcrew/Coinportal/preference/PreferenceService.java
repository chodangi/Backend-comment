package MCcrew.Coinportal.preference;

import MCcrew.Coinportal.board.BoardService;
import MCcrew.Coinportal.domain.Preference;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final BoardService boardService;

    public PreferenceService(PreferenceRepository preferenceRepository, BoardService boardService) {
        this.preferenceRepository = preferenceRepository;
        this.boardService = boardService;
    }

    /**
        좋아요 클릭
     */
    public Preference clickLikes(Long postId, Long userId) throws IllegalStateException{
        Preference preference;
        try {
            preference = preferenceRepository.findByPostIdAndUserId(postId, userId);
            // 이력이 있을 경우 & 싫어요 누른 상태일 경우 -> "이미 싫어요를 누른 게시글입니다."
            if(preference.isDislikes()) {
                throw new IllegalStateException("이미 싫어요를 누른 게시글입니다.");
            }
            // 이력이 있을 경우 & 좋아요 누른 상태일 경우 -> 좋아요 취소 & 좋아요 수 -1
            else if(preference.isLikes()) {
                preference.setLikes(false);
                boardService.likePost(postId, -1);
            }
            // 이력이 있을 경우 & 좋아요 싫어요 모두 취소상태일 경우 -> 좋아요 실행 & 좋아요 수 +1
            else {
                preference.setLikes(true);
                boardService.likePost(postId, 1);
            }
        } catch (NoResultException e) {
            e.printStackTrace();
            // 이력이 없을 경우 -> 좋아요 신규 등록
            preference = new Preference();
            preference.setLikes(true);
            boardService.likePost(postId, 1);
        }
        preference.setPostId(postId);
        preference.setUserId(userId);
        preference.setDislikes(false);
        return preferenceRepository.save(preference);
    }

    /**
        싫어요 클릭
     */
    public Preference clickDislikes(Long postId, Long userId) throws IllegalStateException {
        Preference preference;
        try {
            preference = preferenceRepository.findByPostIdAndUserId(postId, userId);
            // 이력이 있을 경우 & 좋아요 누른 상태일 경우 -> "이미 싫어요를 누른 게시글입니다."
            if(preference.isLikes()) {
                throw new IllegalStateException("이미 좋아요를 누른 게시글입니다.");
            }
            // 이력이 있을 경우 & 싫어요 누른 상태일 경우 -> 싫어요 취소
            else if(preference.isDislikes()) {
                preference.setDislikes(false);
                boardService.dislikePost(postId, -1);
            }
            // 이력이 있을 경우 & 좋아요 싫어요 모두 취소상태일 경우 -> 싫어요 실행
            else {
                preference.setDislikes(true);
                boardService.dislikePost(postId, 1);
            }
        } catch (NoResultException e) {
            e.printStackTrace();
            // 이력이 없을 경우 -> 좋아요 신규 등록
            preference = new Preference();
            preference.setDislikes(true);
            boardService.dislikePost(postId, 1);
        }
        preference.setPostId(postId);
        preference.setUserId(userId);
        preference.setLikes(false);
        return preferenceRepository.save(preference);
    }

    /**
        내 모든 좋아요 가져오기
     */
    public List<Preference> getMyLikeAll(Long userId) {
        try {
            return preferenceRepository.findAllByUserId(userId);
        } catch (NoResultException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
        해당 게시글 내 좋아요 가져오기
     */
    public Map<String,String> getMyLike(Long postId, Long userId) {
        Map<String,String> likeMap = new HashMap<>();
        try{
            Preference findPreference = preferenceRepository.findByPostIdAndUserId(postId, userId);
            if(findPreference.isLikes()) likeMap.put("status","like");
            else if(findPreference.isDislikes()) likeMap.put("status", "dislike");
            else likeMap.put("status", "nothing");
        }catch(NoResultException e){
            likeMap.put("status", "nothing");
        }finally {
            return likeMap;
        }
    }
}
