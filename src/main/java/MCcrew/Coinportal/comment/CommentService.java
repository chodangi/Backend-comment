package MCcrew.Coinportal.comment;

import MCcrew.Coinportal.domain.Dto.CommentDto;
import MCcrew.Coinportal.board.BoardRepository;
import MCcrew.Coinportal.domain.Comment;
import MCcrew.Coinportal.domain.Dto.PostCommentDto;
import MCcrew.Coinportal.domain.Post;
import MCcrew.Coinportal.domain.User;
import MCcrew.Coinportal.login.JwtService;
import MCcrew.Coinportal.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public CommentService(CommentRepository commentRepository, BoardRepository boardRepository, UserRepository userRepository, JwtService jwtService) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    /**
         댓글 생성
    */
    public Comment createComment(PostCommentDto commentDto, Long userId) {
        Post findPost = boardRepository.findById(commentDto.getPostId());
        User findUser = userRepository.findById(userId);
        Comment newComment = Comment.builder()
                .userId(findUser.getId())
                .post(findPost)
                .nickname(commentDto.getNickname())
                .password(commentDto.getPassword())
                .content(commentDto.getContent())
                .commentGroup(commentRepository.getLastGroup().get(0)+1)
                .level(commentDto.getLevel())
                .build();
        return commentRepository.save(newComment);
    }

    /**
     대댓글 생성
     */
    public Comment createReplyComment(PostCommentDto commentDto, Long userId) {
        Post findPost = boardRepository.findById(commentDto.getPostId());
        User findUser = userRepository.findById(userId);
        Comment newComment = Comment.builder()
                .userId(findUser.getId())
                .post(findPost)
                .nickname(commentDto.getNickname())
                .password(commentDto.getPassword())
                .content(commentDto.getContent())
                .commentGroup(commentDto.getCommentGroup())
                .level(commentDto.getLevel())
                .build();
        findPost.getComments().add(newComment);
        return commentRepository.save(newComment);
    }

    /**
        댓글 수정
     */
    public Comment updateComment(CommentDto commentDto, String jwt){
        Comment findComment = commentRepository.findById(commentDto.getCommentId());
        Long userId = jwtService.getUserIdByJwt(jwt);
        if(userId == 0L){
            return new Comment();
        }else{
            if(commentDto.getUserId() == userId){
                findComment.setNickname(commentDto.getNickname());
                findComment.setPassword(commentDto.getPassword());
                findComment.setContent(commentDto.getContent());
                findComment.setCommentGroup(commentDto.getCommentGroup());
                findComment.setLevel(commentDto.getLevel());
                return commentRepository.save(findComment);
            }else{
                return new Comment();
            }
        }
    }

    /**
        삭제 상태로 변경
     */
    public boolean status2Delete(Long commentId, String jwt){
        Long userId = jwtService.getUserIdByJwt(jwt);
        if(userId == 0L){
            return false;
        }else{
            Comment comment = commentRepository.findById(commentId);
            if(comment.getUserId() != userId){
                return false;
            }else{
                try {
                    comment.setStatus('D');
                    commentRepository.save(comment);
                    return true;
                }catch(Exception e){
                    return false;
                }
            }
        }
    }

    /**
        디비에서 댓글 삭제
     */
    public boolean deleteComment(Long commentId){
        int column  = commentRepository.delete(commentId);
        if(column > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
        댓글 신고
     */
    public int reportComment(Long commentId) {
        Comment findComment = commentRepository.findById(commentId);
        System.out.println("getReport: "+findComment.getReportCnt());
        findComment.setReportCnt(findComment.getReportCnt() + 1);
        commentRepository.save(findComment);
        return findComment.getReportCnt();
    }

    /**
        내가 작성한 댓글 조회
     */
    public List<Comment> getMyComment(Long userId){
        return commentRepository.findByUserId(userId);
    }
}
