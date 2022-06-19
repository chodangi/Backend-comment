package MCcrew.Coinportal.comment;

import MCcrew.Coinportal.domain.Dto.CommentDto;
import MCcrew.Coinportal.board.BoardRepository;
import MCcrew.Coinportal.domain.Comment;
import MCcrew.Coinportal.domain.Dto.PostCommentDto;
import MCcrew.Coinportal.domain.Post;
import MCcrew.Coinportal.domain.Report;
import MCcrew.Coinportal.domain.User;
import MCcrew.Coinportal.login.JwtService;
import MCcrew.Coinportal.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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
                .userPoint(commentDto.getUserPoint())
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
     비회원 댓글 생성
     */
    public Comment createNonUserComment(PostCommentDto commentDto) {
        Post findPost = boardRepository.findById(commentDto.getPostId());
        Comment newComment = Comment.builder()
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
                .userPoint(commentDto.getUserPoint())
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
     비회원 대댓글 생성
     */
    public Comment createNonUserReplyComment(PostCommentDto commentDto) {
        Post findPost = boardRepository.findById(commentDto.getPostId());
        Comment newComment = Comment.builder()
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
     비회원 댓글 수정
     */
    public Comment updateNonUserComment(CommentDto commentDto){
        Comment findComment = commentRepository.findById(commentDto.getCommentId());
        if(commentDto.getPassword().equals(findComment.getPassword())){
            findComment.setContent(commentDto.getContent());
            return commentRepository.save(findComment);
        }else{
            return new Comment();
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
     비회원 삭제 상태로 변경
     */
    public boolean nonUserStatus2Delete(Long commentId){
        Comment comment = commentRepository.findById(commentId);
        try {
            comment.setStatus('D');
            commentRepository.save(comment);
            return true;
        }catch(Exception e){
            return false;
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
    public HashMap<String, Object> reportComment(Long commentId) {
        Comment findComment = commentRepository.findById(commentId);
        System.out.println("getReport: "+findComment.getReportCnt());

        // 회원 및 아이피 당 신고 1번으로 제한


        // 신고 3번 누적 시 댓글 가림
        if (findComment.getReportCnt() >= 2) {
            findComment.setStatus('D');
        } else {
            findComment.setReportCnt(findComment.getReportCnt() + 1);
        }
        commentRepository.save(findComment);

        HashMap<String,Object> map = new HashMap<>();
        map.put("reportCnt", findComment.getReportCnt());
        map.put("status", findComment.getStatus());
        return map;
    }

    public boolean reportComment2(Long commentId, Long userId, String ip) {
        // 해당 comment를 신고한 이력이 있는지 user 혹은 ip로 체크
        Comment findComment = commentRepository.findById(commentId);
        User findUser = userRepository.findById(userId);
        // 이력이 있다면 또 신고 불가
        if (commentRepository.findReportByIdOrIp(findComment, findUser, ip) > 0) {
            return false;
        }

        // 이력이 없다면 report 테이블에 레코드 추가
        Report report = Report.builder()
                .comment(findComment)
                .user(findUser)
                .ip(ip)
                .build();
        commentRepository.save(report);

        // reportCnt+1, 신고 3번 누적 시 댓글 가림
        if (findComment.getReportCnt() >= 2) {
            findComment.setStatus('D');
        } else {
            findComment.setReportCnt(findComment.getReportCnt() + 1);
        }
        commentRepository.save(findComment);
        return true;
    }

    public boolean status2Block(Long commentId) {
        Comment findComment = commentRepository.findById(commentId);
        findComment.setStatus('D');
        commentRepository.save(findComment);
        return true;
    }

    /**
        내가 작성한 댓글 조회
     */
    public List<Comment> getMyComment(Long userId){
        return commentRepository.findByUserId(userId);
    }
}
