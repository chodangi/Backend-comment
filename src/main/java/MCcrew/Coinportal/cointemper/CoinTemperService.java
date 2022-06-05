package MCcrew.Coinportal.cointemper;

import MCcrew.Coinportal.domain.Dto.CoinCommentDto;
import MCcrew.Coinportal.domain.CoinComment;
import MCcrew.Coinportal.domain.Dto.CoinTemperDto;
import MCcrew.Coinportal.domain.Report;
import MCcrew.Coinportal.domain.User;
import MCcrew.Coinportal.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class CoinTemperService {

    private static BigDecimal coinTemperBTC = new BigDecimal("50.0"); // 비트코인
    private static BigDecimal coinTemperETH = new BigDecimal("50.0"); // 이더리움
    private static BigDecimal coinTemperXRP = new BigDecimal("50.0"); // 리플
    private static BigDecimal coinTemperADA = new BigDecimal("50.0"); // 에이다
    private static BigDecimal coinTemperSOL = new BigDecimal("50.0"); // 솔라나
    private static BigDecimal coinTemperDOG = new BigDecimal("50.0"); // 도지코인
    private static BigDecimal coinTemperDOT = new BigDecimal("50.0"); // 폴카닷
    private static BigDecimal coinTemperTRX = new BigDecimal("50.0"); // 트론
    private static BigDecimal coinTemperDAI = new BigDecimal("50.0"); // 다이
    private static BigDecimal coinTemperAVX = new BigDecimal("50.0"); // 아발란체
    private static BigDecimal coinTemperWMX = new BigDecimal("50.0"); // 위믹스
    private static BigDecimal coinTemperREP = new BigDecimal("50.0"); // 어거
    private static BigDecimal coinTemperETC = new BigDecimal("50.0"); // 이더리움 클래식
    private static BigDecimal coinTemperBTG = new BigDecimal("50.0"); // 비트코인 골드


    private BigDecimal min_val = new BigDecimal("0.0");           // 온도계 최소값
    private BigDecimal max_val = new BigDecimal("100.0");         // 온도계 최대값
    private BigDecimal diff = new BigDecimal("0.1");

    private final CoinTemperRepository coinTemperRepository;
    private final UserRepository userRepository;

    public CoinTemperService(CoinTemperRepository coinTemperRepository, UserRepository userRepository) {
        this.coinTemperRepository = coinTemperRepository;
        this.userRepository = userRepository;
    }

    /**
         현재 코인 체감 온도 반환
     */
    public HashMap<String,Double> getCoinTempers() throws Exception {
        HashMap<String,Double> temperList = new HashMap<>();
        temperList.put("BTC", coinTemperBTC.doubleValue());
        temperList.put("ETH", coinTemperETH.doubleValue());
        temperList.put("XRP", coinTemperXRP.doubleValue());

        temperList.put("ADA", coinTemperADA.doubleValue());
        temperList.put("SOL", coinTemperSOL.doubleValue());
        temperList.put("DOG", coinTemperDOG.doubleValue());
        temperList.put("DOT", coinTemperDOT.doubleValue());
        temperList.put("TRX", coinTemperTRX.doubleValue());

        temperList.put("DAI", coinTemperDAI.doubleValue());
        temperList.put("AVX", coinTemperAVX.doubleValue());
        temperList.put("WMX", coinTemperWMX.doubleValue());
        temperList.put("REP", coinTemperREP.doubleValue());
        temperList.put("ETC", coinTemperETC.doubleValue());
        temperList.put("BTG", coinTemperBTG.doubleValue());

        return temperList;
    }

    /**
        매도 온도
     */
    public double temperDecrease(String symbol){
        double result = 0;
        switch(symbol){
            case "BTC":
                coinTemperBTC = coinDec(coinTemperBTC);
                result = coinDec(coinTemperBTC).doubleValue();
                break;
            case "ETH":
                coinTemperETH = coinDec(coinTemperETH);
                result = coinDec(coinTemperETH).doubleValue();
                break;
            case "XRP":
                coinTemperXRP = coinDec(coinTemperXRP);
                result = coinDec(coinTemperXRP).doubleValue();
                break;
            case "ADA":
                coinTemperADA = coinDec(coinTemperADA);
                result = coinDec(coinTemperADA).doubleValue();
                break;
            case "SOL":
                coinTemperSOL = coinDec(coinTemperSOL);
                result = coinDec(coinTemperSOL).doubleValue();
                break;
            case "DOG":
                coinTemperDOG = coinDec(coinTemperDOG);
                result = coinDec(coinTemperDOG).doubleValue();
                break;
            case "DOT":
                coinTemperDOT = coinDec(coinTemperDOT);
                result = coinDec(coinTemperDOT).doubleValue();
                break;
            case "TRX":
                coinTemperTRX = coinDec(coinTemperTRX);
                result = coinDec(coinTemperTRX).doubleValue();
                break;
            case "DAI":
                coinTemperDAI = coinDec(coinTemperDAI);
                result = coinDec(coinTemperDAI).doubleValue();
                break;
            case "AVX":
                coinTemperAVX = coinDec(coinTemperAVX);
                result = coinDec(coinTemperAVX).doubleValue();
                break;
            case "WMX":
                coinTemperWMX = coinDec(coinTemperWMX);
                result = coinDec(coinTemperWMX).doubleValue();
                break;
            case "REP":
                coinTemperREP = coinDec(coinTemperREP);
                result = coinDec(coinTemperREP).doubleValue();
                break;
            case "ETC":
                coinTemperETC = coinDec(coinTemperETC);
                result = coinDec(coinTemperETC).doubleValue();
                break;
            case "BTG":
                coinTemperBTG = coinDec(coinTemperBTG);
                result = coinDec(coinTemperBTG).doubleValue();
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }

    /**
        매수 온도
     */
    public double temperIncrease(String symbol){
        double result = 0;
        switch(symbol){
            case "BTC":
                coinTemperBTC = coinInc(coinTemperBTC);
                result = coinInc(coinTemperBTC).doubleValue();
                break;
            case "ETH":
                coinTemperETH = coinInc(coinTemperETH);
                result =  coinInc(coinTemperETH).doubleValue();
                break;
            case "XRP":
                coinTemperXRP = coinInc(coinTemperXRP);
                result =  coinInc(coinTemperXRP).doubleValue();
                break;
            case "ADA":
                coinTemperADA = coinInc(coinTemperADA);
                result = coinInc(coinTemperADA).doubleValue();
                break;
            case "SOL":
                coinTemperSOL = coinInc(coinTemperSOL);
                result = coinInc(coinTemperSOL).doubleValue();
                break;
            case "DOG":
                coinTemperDOG = coinInc(coinTemperDOG);
                result = coinInc(coinTemperDOG).doubleValue();
                break;
            case "DOT":
                coinTemperDOT = coinInc(coinTemperDOT);
                result = coinInc(coinTemperDOT).doubleValue();
                break;
            case "TRX":
                coinTemperTRX = coinInc(coinTemperTRX);
                result = coinInc(coinTemperTRX).doubleValue();
                break;
            case "DAI":
                coinTemperDAI = coinInc(coinTemperDAI);
                result = coinInc(coinTemperDAI).doubleValue();
                break;
            case "AVX":
                coinTemperAVX = coinInc(coinTemperAVX);
                result = coinInc(coinTemperAVX).doubleValue();
                break;
            case "WMX":
                coinTemperWMX = coinInc(coinTemperWMX);
                result = coinInc(coinTemperWMX).doubleValue();
                break;
            case "REP":
                coinTemperREP = coinInc(coinTemperREP);
                result = coinInc(coinTemperREP).doubleValue();
                break;
            case "ETC":
                coinTemperETC = coinInc(coinTemperETC);
                result = coinInc(coinTemperETC).doubleValue();
                break;
            case "BTG":
                coinTemperBTG = coinInc(coinTemperBTG);
                result = coinInc(coinTemperBTG).doubleValue();
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }

    /**
        코인 온도 증가
     */
    public BigDecimal coinDec(BigDecimal coinTemper){
        if((coinTemper.subtract(diff)).compareTo(min_val) != -1){
            coinTemper = coinTemper.subtract(diff);
            return coinTemper;
        }
        else{
            return min_val;
        }
    }
    /**
        코인 온도 감소
     */
    public BigDecimal coinInc(BigDecimal coinTemper){
        if((coinTemper.add(diff)).compareTo(max_val) != 1){
            coinTemper = coinTemper.add(diff);
            return coinTemper;
        }
        else{
            return max_val;
        }
    }

    /**
        댓글달기
    */
    public CoinComment createComment(CoinTemperDto dto, String symbol, Long userIdx) {
        CoinComment coinComment = new CoinComment();
        User findUser = userRepository.findById(userIdx);
        coinComment.setUserId(userIdx);
        coinComment.setCoinSymbol(symbol);
        coinComment.setNickname(findUser.getUserNickname());
        coinComment.setContent(dto.getContent());
        coinComment.setCommentGroup(coinTemperRepository.getLastGroup().get(0)+1);
        coinComment.setLevel(dto.getLevel());
        coinComment.setStatus('A');

        return coinTemperRepository.save(coinComment);
    }

    /**
     대댓글달기
     */
    public CoinComment createReplyComment(CoinTemperDto dto, String symbol, Long userIdx) {
        CoinComment coinComment = new CoinComment();
        User findUser = userRepository.findById(userIdx);
        coinComment.setUserId(userIdx);
        coinComment.setCoinSymbol(symbol);
        coinComment.setNickname(findUser.getUserNickname());
        coinComment.setContent(dto.getContent());
        coinComment.setCommentGroup(dto.getCommentGroup());
        coinComment.setLevel(dto.getLevel());
        coinComment.setStatus('A');

        return coinTemperRepository.save(coinComment);
    }

    /**
        symbol 모든 댓글 반환
     */
    public List<CoinComment> getCommentList(String symbol) {
        List<CoinComment> coinCommentList = coinTemperRepository.findByCoinSymbol(symbol);
        return coinCommentList;
    }

    /**
        댓글 신고
     */
    public boolean reportCoinComment(Long coinId, Long userId){
        CoinComment findCoinComment =  coinTemperRepository.findById(coinId);
        User findUser = userRepository.findById(userId);
        // 이력이 있다면 신고 불가
        if(coinTemperRepository.findReportById(findCoinComment, findUser) > 0) return false;
        // 이력이 없다면 report 테이블에 레코드 추가
        Report report = Report.builder()
                .coinComment(findCoinComment)
                .user(findUser)
                .build();
        coinTemperRepository.save(report);

        // 신고 3회 누적시 삭제
        if(findCoinComment.getReportCnt() >= 2) findCoinComment.setStatus('D');
        else findCoinComment.setReportCnt(findCoinComment.getReportCnt()+1);
        coinTemperRepository.save(findCoinComment);
        return true;
    }

    /**
        댓글 좋아요
     */
    public int likeCoinComment(Long id){
        CoinComment coinComment =  coinTemperRepository.findById(id);
        coinComment.setUpCnt(coinComment.getUpCnt() + 1);
        return coinTemperRepository.save(coinComment).getUpCnt();
    }

    /**
        댓글 싫어요
     */
    public int dislikeCoinComment(Long id){
        CoinComment coinComment =  coinTemperRepository.findById(id);
        coinComment.setDownCnt(coinComment.getDownCnt() + 1);
        return coinTemperRepository.save(coinComment).getDownCnt();
    }

    /**
        회원 댓글 수정
     */
    public CoinComment updateCoinComment(CoinCommentDto coinCommentDto, Long userId) {
        Long commentId = coinCommentDto.getCommentId();
        CoinComment coinComment = coinTemperRepository.findById(commentId);
        if(coinComment.getUserId() != userId){
            return new CoinComment();
        }else{
            coinComment.setCoinSymbol(coinCommentDto.getCoinSymbol());
            coinComment.setNickname(coinCommentDto.getNickname());
            coinComment.setContent(coinCommentDto.getContent());
            return coinTemperRepository.save(coinComment);
        }
    }

    /**
        회원 댓글 삭제
     */
    public boolean deleteCoinComment(CoinCommentDto coinCommentDto , Long userId) {
        Long commentId = coinCommentDto.getCommentId();
        CoinComment coinComment = coinTemperRepository.findById(commentId);
        if(coinComment.getUserId() == userId){
            int deletedColumn = coinTemperRepository.delete(commentId);
            if(deletedColumn > 0){
                return true;
            }
            else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
        비회원 댓글 삭제
     */
    public boolean status2Block(Long commentId) {
        CoinComment findCoinComment = coinTemperRepository.findById(commentId);
        findCoinComment.setStatus('D');
        coinTemperRepository.save(findCoinComment);
        return true;
    }
}
