package org.yyama.jangkeng.action;

import static org.yyama.jangkeng.util.Pose.CHOKI;
import static org.yyama.jangkeng.util.Pose.GU;
import static org.yyama.jangkeng.util.Pose.PA;
import static org.yyama.jangkeng.util.WinLoseDraw.DRAW;
import static org.yyama.jangkeng.util.WinLoseDraw.LOSE;
import static org.yyama.jangkeng.util.WinLoseDraw.WIN;

import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.yyama.jangkeng.entity.OutcomeRate;
import org.yyama.jangkeng.service.JangkengSarvice;
import org.yyama.jangkeng.util.Pose;
import org.yyama.jangkeng.util.WinLoseDraw;

public class JangKengAction {

    protected static JangkengSarvice js = new JangkengSarvice();

    public static void doProc(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {

        // ユーザの出したポーズを取得し、リクエストにセット。ポーズのイメージもセット。
        @SuppressWarnings("unchecked")
        Map<String, String> form = req.getParameterMap();
        Pose userPose;
        if (form.containsKey("GU")) {
            userPose = GU;
        } else if (form.containsKey("CHOKI")) {
            userPose = CHOKI;
        } else if (form.containsKey("PA")) {
            userPose = PA;
        } else {
            userPose = Pose.ELSE;
        }
        req.setAttribute("userPoseImg", userPose.getImgFile());

        // コンピュータのポーズを取得し、イメージをリクエストにセット
        Pose compPose = js.getPose();
        req.setAttribute("compPoseImg", compPose.getImgFile());

        // 勝敗を判定
        WinLoseDraw wld = js.dicisionGame(userPose, compPose);

        // メッセージをセット
        String msg;
        msg = (wld == WIN) ? "おめでとう！あなたの勝ちです。" : (wld == DRAW) ? "あいこ!"
                : (wld == LOSE) ? "残念!あなたの負け。" : "さあ！対戦しよう。";
        req.setAttribute("msg", msg);

        // 今回の対戦結果を保存
        js.setRecord(compPose, wld);

        // 対戦成績の取得
        OutcomeRate or = js.getScoreline();

        req.setAttribute("matchCnt", or.getCntOfMatch());
        req.setAttribute("winCnt", or.getCntOfWin());
        req.setAttribute("loseCnt", or.getCntOfLose());
        req.setAttribute("drawCnt", or.getCntOfDraw());

        double wr = (double) or.getCntOfWin() / (double) or.getCntOfMatch()
                * 100;
        double lr = (double) or.getCntOfLose() / (double) or.getCntOfMatch()
                * 100;
        double dr = (double) or.getCntOfDraw() / (double) or.getCntOfMatch()
                * 100;

        req.setAttribute("winRate", String.format("%2.2f", wr));
        req.setAttribute("loseRate", String.format("%2.2f", lr));
        req.setAttribute("drawRate", String.format("%2.2f", dr));

        RequestDispatcher rd = req
                .getRequestDispatcher("WEB-INF/view/main.jsp");
        rd.forward(req, resp);
    }
}
