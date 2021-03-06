//package MCcrew.Coinportal.board;
//
//import MCcrew.Coinportal.Dto.CommentDto;
//import MCcrew.Coinportal.Dto.PostDto;
//import MCcrew.Coinportal.Dto.UserDto;
//import MCcrew.Coinportal.Dto.UserRankingDto;
//import MCcrew.Coinportal.cointemper.CoinTemperService;
//import MCcrew.Coinportal.comment.CommentController;
//import MCcrew.Coinportal.comment.CommentRepository;
//import MCcrew.Coinportal.comment.CommentService;
//import MCcrew.Coinportal.domain.BetHistory;
//import MCcrew.Coinportal.domain.Comment;
//import MCcrew.Coinportal.domain.Post;
//import MCcrew.Coinportal.domain.User;
//import MCcrew.Coinportal.game.GameRepository;
//import MCcrew.Coinportal.game.GameService;
//import MCcrew.Coinportal.login.JwtService;
//import MCcrew.Coinportal.login.LoginService;
//import MCcrew.Coinportal.photo.Attachment;
//import MCcrew.Coinportal.photo.AttachmentRepository;
//import MCcrew.Coinportal.user.UserRepository;
//import MCcrew.Coinportal.user.UserService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Commit;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import java.io.UnsupportedEncodingException;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@SpringBootTest
//class BoardControllerTest {
//
//    @Autowired private BoardService boardService;
//    @Autowired private BoardRepository boardRepository;
//    @Autowired private BoardRepository boardRepository2;
//    @Autowired private BoardController boardController;
//    @Autowired private UserRepository userRepository;
//    @Autowired private UserService userService;
//    @Autowired private CommentController commentController;
//    @Autowired private CommentRepository commentRepository;
//    @Autowired private CommentService commentService;
//    @Autowired private GameService gameService;
//    @Autowired private CoinTemperService coinTemperService;
//    @Autowired private LoginService loginService;
//    @Autowired private JwtService jwtService;
//    @Autowired private AttachmentRepository attachmentRepository;
//    @Autowired private GameRepository gameRepository;
//    @Autowired private EntityManager em;
//
//    @Test
//    @Commit
//    public void ????????????????????????(){
//        // given
//        User user = new User();
//        user.setUserNickname("test123");
//        user.setPoint(1000);
//        user.setDark(true);
//        user.setOnAlarm(true);
//        user.setStatus('A');
//        userRepository.save(user);
//
//        Post post = new Post();
//        Date date = new Date();
//        post.setUserNickname("test123");
//        post.setBoardName("???????????????");
//        //post.setUserId(user);
//        post.setGuestName(null);
//        post.setGuestPwd(null);
//        post.setContent("????????? ??????????????????. ");
//        post.setUpCnt(0);
//        post.setDownCnt(0);
//        post.setViewCnt(0);
//        post.setReportCnt(0);
//        post.setCreatedAt(date);
//        post.setUpdatedAt(date);
//        post.setStatus('A');    // Active ????????? ??????
//        boardRepository.save(post);
//
//        // when
//        Post findPost = boardRepository.findById(2L);
//        System.out.println("findPost.content: " + findPost.getContent());
//        User findUser = userRepository.findById(1L);
//
//        //then
//        Assertions.assertThat(user.getUserNickname()).isEqualTo(findUser.getUserNickname());
//    }
//
//    @Test
//    public void ????????????????????????(){
//        // given
//        PostDto postDto = new PostDto();
//        postDto.setNickname("test123");
//        postDto.setBoardName("???????????????");
//        postDto.setContent("????????? ???????????????.");
//
//        //when
//        Post post = boardService.updatePost(postDto, 1L);
//        Post findPost = boardRepository.findByNickname("test123");
//
//        //then
//        Assertions.assertThat(findPost.getContent()).isEqualTo("????????? ???????????????.");
//    }
//
//    @Test
//    public void ?????????????????????????????????????????????????????????(){
//        // given
//        User findUser = userRepository.findByNickname("test123");
//        System.out.println("findUser = "+ findUser.getUserNickname());
//
//        UserDto userDto = new UserDto();
//        userDto.setUserNickname("test777");
//        userDto.setDark(false);
//        userDto.setOnAlarm(false);
//        User returnUser = userService.updateUser(userDto);
//
//        // when
//        System.out.println("========================findAll====================");
//        List<Post> postList = boardRepository.findAll();
//        System.out.println("========================findAll====================");
//
//        for(Post post: postList){
//            System.out.println("post.getNickname(): " + post.getUserNickname());
//        }
//        //then
//    }
//
//    @Test
//    public void ????????????????????????(){
//        boardRepository.delete(1L);
//        Assertions.assertThat(boardRepository.findAll().size()).isEqualTo(0);
//    }
//
//    @Test
//    public void ????????????????????????(){
//        boardService.reportPost(1L);
//    }
//
//    @Test
//    @Commit
//    public void ??????????????????????????????(){
//        // given
//        User user1 = new User();
//        user1.setUserNickname("test1");
//        user1.setEmail("test1@gmail.com");
//        user1.setPoint(0);
//        user1.setDark(true);
//        user1.setOnAlarm(true);
//        user1.setStatus('A');
//
//        User user2 = new User();
//        user2.setUserNickname("test2");
//        user2.setEmail("test2@gmail.com");
//        user2.setPoint(0);
//        user2.setDark(true);
//        user2.setOnAlarm(true);
//        user2.setStatus('A');
//
//        User user3 = new User();
//        user3.setUserNickname("test3");
//        user3.setEmail("test3@gmail.com");
//        user3.setPoint(0);
//        user3.setDark(true);
//        user3.setOnAlarm(true);
//        user3.setStatus('A');
//
//        userRepository.save(user1);
//        userRepository.save(user2);
//        userRepository.save(user3);
//
//        User findUser = userRepository.findByNickname("test1");
//        Date date = new Date();
//        Post post1 = new Post();
//        post1.setUserId(findUser.getId());
//        post1.setUserNickname(findUser.getUserNickname());
//        post1.setBoardName("???????????????");
//        post1.setGuestName(null);
//        post1.setGuestPwd(null);
//        post1.setContent("????????? ???????????????. ");
//        post1.setUpCnt(0);
//        post1.setDownCnt(0);
//        post1.setViewCnt(0);
//        post1.setReportCnt(0);
//        post1.setCreatedAt(date);
//        post1.setUpdatedAt(date);
//        post1.setStatus('A');
//        boardRepository.save(post1);
//    }
//
//    @Test
//    // @Transactional
//    public void ????????????(){
//        Date date = new Date();
//        Comment comment = new Comment();
//        Post findPost = boardRepository.findById(5L);
//        User findUser = userRepository.findById(2L);
//        comment.setPost(findPost);
////        comment.setUser_id(findUser);
//
//        // comment.setPostId(findPost.getId());
//        comment.setUserId(findUser.getId());
//
//        comment.setNickname("commentmaker");
//        comment.setPassword("123");
//        comment.setContent("????????? ???????????????. ");
//        comment.setCommentGroup(1);
//        comment.setLevel(1);
//        comment.setReportCnt(0);
//        comment.setCreatedAt(date);
//        comment.setUpdateAt(date);
//        comment.setStatus('A');
//        commentRepository.save(comment);
//
////        // ?????? ??????
////        findPost.getComments().add(comment);
////        boardRepository.save(findPost);
//
//        Post findPost2 = boardRepository.findById(5L);
//        Comment newComment = new Comment();
//        newComment.setPost(findPost2);
//        commentRepository.save(newComment);
//
//        for(Comment com: findPost2.getComments()){
//            System.out.println("comment: " + com.getContent());
//        }
//    }
//
//    @Test
//    public void ???????????????????????????(){
//        User findUser = userRepository.findById(1L);
//        System.out.println("findUser: " + findUser.toString());
//        System.out.println("findUser.id : " + findUser.getId());
//        System.out.println("findUser.point: " + findUser.getPoint());
//        findUser.setPoint(300);
//        userRepository.save(findUser);
//        Assertions.assertThat(findUser.getPoint()).isEqualTo(300);
//    }
//
//    @Test
//    public void ??????api???????????????() throws JsonProcessingException {
//        String result1 = gameService.getPriceFromBithumb("BTC/KRW");
//        System.out.println("result = " + result1);
//        String result2 = gameService.getPriceFromBithumb("ETH/KRW");
//        System.out.println("result = " + result2);
//        String result3 = gameService.getPriceFromBithumb("XRP/KRW");
//        System.out.println("result = " + result3);
//    }
//
//    @Test
//    public void ?????????????????????????????????(){
//        String symbol = "BTC";
//
//        for(int i = 0; i< 1005; i++)
//            System.out.println(coinTemperService.temperIncrease(symbol));
//        System.out.println("coinTemper: " + coinTemperService.getCoinTemper());
//
//        for(int i = 0; i< 1005; i++)
//            System.out.println(coinTemperService.temperDecrease(symbol));
//        System.out.println("coinTemper: " + coinTemperService.getCoinTemper());
//        // Assertions.assertThat(coinTemperService.getCoinTemper().doubleValue()).isEqualTo(0.0);
//    }
//
//    @Test
//    // @Transactional
//    public void ?????????????????????(){
//        Date date = new Date();
//        Post findPost = boardRepository.findByNickname("test1");
//        User findUser = userRepository.findById(2L);
//        Comment newComment = new Comment();
//        //newComment.setPostId(findPost.getId());
//        newComment.setUserId(1L);
//        newComment.setPost(findPost);
////        newComment.setUser_id(findUser);
//
//        newComment.setNickname("test1");
//        newComment.setPassword("");
//        newComment.setContent("??????????????? ??????????????? ????????????");
//        newComment.setCommentGroup(1);
//        newComment.setLevel(1);
//        newComment.setReportCnt(0);
//        newComment.setCreatedAt(date);
//        newComment.setUpdateAt(date);
//        newComment.setStatus('D');
//        commentRepository.save(newComment);
//        findPost.getComments().add(newComment);
//        boardRepository.save(findPost);
//
//        Post findPostAgain = boardRepository.findByNickname("test1");
//        Comment findComment = findPostAgain.getComments().get(0);
//        findComment.setStatus('D');
//        commentRepository.save(findComment);
//        boardRepository.save(findPostAgain);
//    }
//
//    @Test
//    public void jwt???????????????() throws UnsupportedEncodingException {
//        String resultJwt = jwtService.generateJwt("razelo", "razelo@gmail.com");
//        System.out.println("resultJwt = " + resultJwt);
//        Assertions.assertThat((resultJwt.length()>0)).isEqualTo(true);
//    }
//
//    @Test
//    public void jwtclaims?????????() throws UnsupportedEncodingException{
//        String jwt = jwtService.generateJwt("razelo", "razelo@gmail.com");
//        boolean jwtValid = jwtService.validateJwt(jwt);
//        Assertions.assertThat(jwtValid).isTrue();
//        String userName = jwtService.getUserName(jwt);
//        String userEmail = jwtService.getUserEmail(jwt);
//        System.out.println("userName = " + userName);
//        System.out.println("userEmail = " + userEmail);
//    }
//
//
//
//    @Test
//    public void OneToMany?????????????????????????????????(){
//        Post findPost = boardRepository.findByNickname("test1");
//        Date date = new Date();
//        Comment newComment  = new Comment();
//        newComment.setPost(findPost);
//        newComment.setUserId(findPost.getUserId());
//        newComment.setNickname("?????????1");
//        newComment.setPassword("");
//        newComment.setContent("????????? ???????????????. 1");
//        newComment.setCommentGroup(0);
//        newComment.setLevel(0);
//        newComment.setReportCnt(0);
//        newComment.setCreatedAt(date);
//        newComment.setUpdateAt(date);
//        newComment.setStatus('a');
//        findPost.getComments().add(newComment);
//        boardRepository.save(findPost);
//    }
//
//    @Test
//    public void post???????????????List?????????(){
//        Post findPost = boardRepository.findByNickname("test1");
//        for(Comment com: findPost.getComments()){
//            System.out.println(com.getContent());
//        }
//    }
//
//    @Test
//    public void findByNameAndEmail?????????(){
//        User findUser = userRepository.findByNameAndEmail("test1", "test1@gmail.com");
//        Assertions.assertThat(findUser.getPoint()).isEqualTo(0);
//    }
//
//    @Test
//    public void ?????????????????????2(){
//        CommentDto commentDto = new CommentDto();
//        commentDto.setPostId(4L);
//        commentDto.setUserId(9L);
//        commentDto.setNickname("razelo2");
//        commentDto.setPassword(null);
//        commentDto.setContent("?????????2??? ?????? ???????????????. ");
//        commentDto.setCommentGroup(1);
//        commentDto.setLevel(1);
//        commentService.createComment(commentDto);
//    }
//    @Test
//    public void ?????????????????????(){
//
//    }
//    @Test
//    public void ?????????????????????(){
//
//    }
//    @Test
//    public void ???????????????????????????????????????(){
//
//    }
//    @Test
//    @Commit
//    public void ?????????????????????????????????() {
//        for (int i = 0; i < 50; i++) {
//            Post post = new Post();
//            Date date = new Date();
//            post.setUserId(3L);
//            post.setUserNickname("test" + i);
//            post.setBoardName("???????????????");
//            post.setGuestName("");
//            post.setGuestPwd("");
//            post.setContent("test" + i + "??? ???????????? ????????? ??????????????????. ");
//            post.setUpCnt(0);
//            post.setDownCnt(0);
//            post.setViewCnt(0);
//            post.setReportCnt(0);
//            post.setCreatedAt(date);
//            post.setUpdatedAt(date);
//            post.setStatus('A');
//            boardRepository.save(post);
//        }
//        for (int i = 0; i < 50; i++) {
//            Post post = new Post();
//            Date date = new Date();
//            post.setUserId(3L);
//            post.setUserNickname("test" + i);
//            post.setBoardName("???????????????");
//            post.setGuestName("");
//            post.setGuestPwd("");
//            post.setContent("test" + i + "??? ???????????? ????????? ??????????????????. ");
//            post.setUpCnt(0);
//            post.setDownCnt(0);
//            post.setViewCnt(0);
//            post.setReportCnt(0);
//            post.setCreatedAt(date);
//            post.setUpdatedAt(date);
//            post.setStatus('A');
//            boardRepository.save(post);
//        }
//        for (int i = 0; i < 50; i++) {
//            Post post = new Post();
//            Date date = new Date();
//            post.setUserId(3L);
//            post.setUserNickname("test" + i);
//            post.setBoardName("???????????????");
//            post.setGuestName("");
//            post.setGuestPwd("");
//            post.setContent("test" + i + "??? ???????????? ????????? ??????????????????. ");
//            post.setUpCnt(0);
//            post.setDownCnt(0);
//            post.setViewCnt(0);
//            post.setReportCnt(0);
//            post.setCreatedAt(date);
//            post.setUpdatedAt(date);
//            post.setStatus('A');
//            boardRepository.save(post);
//        }
//        for (int i = 0; i < 50; i++) {
//            Post post = new Post();
//            Date date = new Date();
//            post.setUserId(3L);
//            post.setUserNickname("test" + i);
//            post.setBoardName("???????????????");
//            post.setGuestName("");
//            post.setGuestPwd("");
//            post.setContent("test" + i + "??? ???????????? ????????? ??????????????????. ");
//            post.setUpCnt(0);
//            post.setDownCnt(0);
//            post.setViewCnt(0);
//            post.setReportCnt(0);
//            post.setCreatedAt(date);
//            post.setUpdatedAt(date);
//            post.setStatus('A');
//            boardRepository.save(post);
//        }
//    }
//    @Test
//    public void ???????????????????????????????????????(){
//        List<Post> findPost = boardController.searchByNicknameController("test3");
//        for(Post post: findPost){
//            System.out.println("findPost : " + post.getContent());
//        }
//        Assertions.assertThat(findPost.size()).isEqualTo(50);
//    }
//    @Test
//    public void ??????????????????(){
//        boardController.likeController(4L);
//        boardController.likeController(30L);
//        boardController.likeController(40L);
//        Post findPost1 = boardRepository.findById(4L);
//        Post findPost2 = boardRepository.findById(30L);
//        Post findPost3 = boardRepository.findById(40L);
//        Assertions.assertThat(findPost1.getUpCnt()).isEqualTo(1);
//        Assertions.assertThat(findPost2.getUpCnt()).isEqualTo(1);
//        Assertions.assertThat(findPost3.getUpCnt()).isEqualTo(1);
//    }
//    @Test
//    public void ???????????????(){
//        boardController.likeController(4L);
//        boardController.likeController(30L);
//        boardController.likeController(30L);
//    }
//    @Test
//    public void ????????????????????????(){
//        Post findPost = boardRepository.findById(2L);
//        for(Attachment at: findPost.getAttachedFiles()){
//            System.out.println(at.getOriginFilename());
//            System.out.println(at.getStoreFilename());
//        }
//    }
//    @Test
//    public void ????????????????????????(){
//        String result = loginService.getNicknameFromEmail("test1@gmail.com");
//        Assertions.assertThat(result).isEqualTo("test1");
//    }
//
//    @Test
//    @Commit
//    public void ?????????????????????????????????????????????????????????(){
//
//    }
//
//    @Test
//    public void ????????????????????????(){
//        boolean deleted = boardService.deletePost(3L);
//        Assertions.assertThat(deleted).isTrue();
//    }
//    @Test
//    public void ????????????????????????2(){
//        System.out.println(boardService.deleteFile("1c545553-936e-46db-a0d2-9094a7c31afc.jpg"));
//    }
//    @Test
//    @Commit
//    public void ????????????() {
//        for (int i = 0; i < 50; i++) {
//            User newUser = new User();
//            newUser.setEmail("test" + i + "@gmail.com");
//            newUser.setUserNickname("test" + i);
//            newUser.setPoint(i);
//            newUser.setDark(true);
//            newUser.setOnAlarm(true);
//            newUser.setStatus('A');
//            userRepository.save(newUser);
//        }
//    }
//    @Test
//    public void ??????????????????????????????(){
//        List<Post> postList = boardRepository.findAll().stream().filter(p -> p.getBoardName().equals("???????????????")).collect(Collectors.toList());
//        for(Post post: postList){
//            System.out.println("result: " + post.getId() +"/" + post.getBoardName());
//        }
//    }
//
//    @Test
//    public void deleteFile??????????????????(){
//        boolean deleted = boardService.deleteFile("21438db2-0c29-43f2-9817-ab324f5714ea.jpg");
//        Assertions.assertThat(deleted).isTrue();
//    }
//
//    @Test
//    public void attachmentList?????????(){
//        Post findPost = boardRepository.findById(2L);
//        Long postId = findPost.getId();
//
//        List<Attachment> attachmentList = attachmentRepository.findByPost_Id(postId);
//        for(Attachment attach: attachmentList){
//            System.out.println(attach.getStoreFilename());
//        }
//    }
//
//    @Test
//    public void user???????????????(){
//        List<UserRankingDto> userRankingDtoList = userService.getUserRanking();
//        for(UserRankingDto userRankingDto : userRankingDtoList){
//            System.out.println(userRankingDto.getNickname() + " : "  + userRankingDto.getPoint());
//        }
//    }
//
////    @Test
////    public void ??????????????????(){
////        gameService.gameTimer();
////    }
//
//    @Test
//    public void ?????????api?????????(){
//
//    }
//
//    @Test
//    public void api?????????() throws JsonProcessingException {
//        String price=  gameService.getPriceFromBithumb("BTC/KRW");
//        System.out.println(price);
//    }
//    @Test
//    @Commit
//    public void BetHistory????????????????????????(){
//        for(int i = 0; i< 50; i++) {
//            Date date = new Date();
//            BetHistory betHistory = new BetHistory();
//
//            double priceBTC = Double.valueOf(gameService.getPriceFromBithumb("BTC/KRW"));
//            double priceETH = Double.valueOf(gameService.getPriceFromBithumb("ETH/KRW"));
//            double priceXRP = Double.valueOf(gameService.getPriceFromBithumb("XRP/KRW"));
//
//            betHistory.setUserId(65L);
//            betHistory.setPredictedAt(date);
//
//            betHistory.setBTC(true);
//            betHistory.setETH(true);
//            betHistory.setXRP(true);
//
//            betHistory.setBtcPriceNow(priceBTC);
//            betHistory.setEthPriceNow(priceETH);
//            betHistory.setXrpPriceNow(priceXRP);
//
//            gameRepository.save(betHistory);
//        }
//    }
//
//    @Test
//    public void ???????????????????????????????????????????????????(){
//        Date tempDate = new Date();
//        LocalDateTime localDateTime = LocalDateTime.now();
//        System.out.println(tempDate);
//        System.out.println(localDateTime);
//    }
//
//    @Test
//    public void ??????????????????(){
//        List<User> findUserList = userRepository.findAll();
//        for(User user: findUserList){
//            System.out.println(user.getPreviousWins());
//        }
//    }
//}