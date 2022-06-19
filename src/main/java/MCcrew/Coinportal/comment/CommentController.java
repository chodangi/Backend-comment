package MCcrew.Coinportal.comment;

import MCcrew.Coinportal.domain.Dto.CommentDto;
import MCcrew.Coinportal.domain.Comment;
import MCcrew.Coinportal.domain.Dto.PostCommentDto;
import MCcrew.Coinportal.domain.Post;
import MCcrew.Coinportal.domain.User;
import MCcrew.Coinportal.login.JwtService;
import MCcrew.Coinportal.util.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final JwtService jwtService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CommentController(CommentService commentService, JwtService jwtService) {
        this.commentService = commentService;
        this.jwtService = jwtService;
    }

    /**
     댓글 달기
     */
    @ApiOperation(value = "회원 비회원 댓글 post",
            notes = "비회원 댓글 작성일 경우 Header의 jwt에 아무런 값도 안 보내주시면 됩니다.")
    @PostMapping("")
    public ResponseEntity<? extends BasicResponse> commentController(@RequestBody PostCommentDto commentDto, @RequestHeader(required = false) String jwt){
        logger.info("commentCreateController(): 댓글을 작성합니다.");
        Comment comment;

        // 비회원
        if(jwt==null) {
            if (commentDto.getCommentGroup() == -1) comment = commentService.createNonUserComment(commentDto);
            else comment = commentService.createNonUserReplyComment(commentDto);
        }
        // 회원
        else {
            Long userId = jwtService.getUserIdByJwt(jwt);
            if (commentDto.getCommentGroup() == -1) comment = commentService.createComment(commentDto, userId);
            else comment = commentService.createReplyComment(commentDto, userId);
        }
        return ResponseEntity.ok().body(new CommonResponse(comment));
    }

    /**
        댓글 수정
     */
    @ApiOperation(value = "회원 비회원 댓글 content 수정",
            notes = "비회원 댓글 작성일 경우 Header의 jwt에 아무런 값도 안 보내주시면 됩니다. 비회원의 경우 password가 일치해야 작업이 수행됩니다.")
    @PutMapping("/")
    public ResponseEntity<? extends BasicResponse> commentUpdateController(@RequestBody CommentDto commentDto, @RequestHeader(required = false) String jwt){
        logger.info("commentUpdateController(): 댓글을 수정합니다.");
        Comment comment;

        if(jwt==null) {
            comment = commentService.updateNonUserComment(commentDto);
            if(comment.getId() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("허가되지 않은 요청입니다."));
        } else {
            comment =  commentService.updateComment(commentDto, jwt);
            if(comment.getId() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("허가되지 않은 요청입니다."));
        }
        return ResponseEntity.ok().body(new CommonResponse(comment));
    }

    /**
        삭제 상태로 변경
     */
    @ApiOperation(value = "회원 비회원 댓글 삭제 상태로 변경",
            notes = "비회원 댓글 작성일 경우 Header의 jwt에 아무런 값도 안 보내주시면 됩니다.")
    @PutMapping("/{comment-id}")
    public ResponseEntity<? extends BasicResponse> commentStatus2DeleteController(@PathVariable("comment-id") Long commentId, @RequestHeader(required = false) String jwt){
        logger.info("commentStatus2DeleteController(): "+ commentId + "번 댓글을 삭제 상태로 변경합니다.");
        boolean result;
        if(jwt==null) result = commentService.nonUserStatus2Delete(commentId);
        else result = commentService.status2Delete(commentId, jwt);

        return ResponseEntity.ok().body(new CommonResponse(result));
    }

    /**
        댓글 신고
     */
    @PostMapping("/report/{commentId}")
    public ResponseEntity<? extends BasicResponse> report2Controller(@PathVariable Long commentId, @RequestHeader String jwt, HttpServletRequest request) {
        logger.info("report2Controller(): "+ commentId + "번 댓글을 신고신고신고합니다.");
        String remoteAddr = "";
        if(request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        Long userId = jwtService.getUserIdByJwt(jwt);
        // 관리자일 경우 바로 삭제
        if(userId==1 || userId==14 || userId==15 || userId==16 || userId==17) commentService.status2Block(commentId);
        //HashMap<String,Object> result = commentService.reportComment(commentId);
        boolean result = commentService.reportComment2(commentId, userId, remoteAddr);
        return ResponseEntity.ok(new CommonResponse(result));
    }
}
