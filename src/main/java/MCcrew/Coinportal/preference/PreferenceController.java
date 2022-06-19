package MCcrew.Coinportal.preference;

import MCcrew.Coinportal.domain.Preference;
import MCcrew.Coinportal.login.JwtService;
import MCcrew.Coinportal.util.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/preference")
public class PreferenceController {

    private final PreferenceService preferenceService;
    private final JwtService jwtService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public PreferenceController(PreferenceService preferenceService, JwtService jwtService) {
        this.preferenceService = preferenceService;
        this.jwtService = jwtService;
    }

    /**
        게시글 좋아요 클릭
    */
    @PostMapping("/preference-like/{post-id}")
    public ResponseEntity<? extends BasicResponse> likeController(@PathVariable("post-id") Long postId, @RequestHeader String jwt)  {
        logger.info("likeController(): " + postId + "번 게시글 좋아요 클릭");
        try {
            if(jwt == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("허가되지 않은 사용자입니다."));
            }
            Long userId = jwtService.getUserIdByJwt(jwt);
            if(userId == 0L){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("허가되지 않은 사용자입니다."));
            }else{
                Preference preference = preferenceService.clickLikes(postId, userId);
                if(preference.getUserId() == null){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("수행될 수 업습니다."));
                }
                return ResponseEntity.ok().body(new CommonResponse(preference));
            }
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("이미 싫어요를 누른 게시글입니다."));
        }
    }

    /**
        게시글 싫어요 클릭
     */
    @PostMapping("/preference-dislike/{post-id}")
    public ResponseEntity<? extends BasicResponse> dislikeController(@PathVariable("post-id") Long postId, @RequestHeader String jwt){
        logger.info("dislikeController(): " + postId + "번 게시글 싫어요 클릭");
        try {
            if(jwt == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("허가되지 않은 사용자입니다."));
            }
            Long userId = jwtService.getUserIdByJwt(jwt);
            if(userId == 0L){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("허가되지 않은 사용자입니다."));
            }else{
                Preference preference = preferenceService.clickDislikes(postId, userId);
                if(preference.getUserId() == null){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("수행될 수 업습니다."));
                }
                return ResponseEntity.ok().body(new CommonResponse(preference));
            }
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("이미 좋아요를 누른 게시글입니다."));
        }
    }

    /**
        내가 누른 좋아요 모두 보기
     */
    @GetMapping("/my-like")
    public ResponseEntity<? extends BasicResponse> myLikeController(@RequestHeader String jwt){
        logger.info("myLikeController(): 내가 누른 좋아요 모두 보기");
        if(jwt == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("JWT를 확인해주세요."));
        }
        Long userId = jwtService.getUserIdByJwt(jwt);
        if(userId == 0L){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("허가되지 않은 사용자입니다."));

        }else{
            List<Preference> preferenceList = preferenceService.getMyLikeAll(userId);
            return ResponseEntity.ok().body(new CommonResponse(preferenceList));
        }
    }

    @GetMapping("/{postId}/{userId}")
    public ResponseEntity<? extends BasicResponse> checkLikeController(@PathVariable Long postId, @PathVariable Long userId) {
        logger.info("checkLikeController(): 게시글 좋아요/싫어요 여부 확인하기");
        Map<String,String> map = preferenceService.getMyLike(postId, userId);
        return ResponseEntity.ok().body(new CommonResponse(map));
    }
}