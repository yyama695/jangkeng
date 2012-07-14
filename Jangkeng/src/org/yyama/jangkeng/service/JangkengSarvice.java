package org.yyama.jangkeng.service;

import static org.yyama.jangkeng.util.Pose.CHOKI;
import static org.yyama.jangkeng.util.Pose.GU;
import static org.yyama.jangkeng.util.Pose.PA;
import static org.yyama.jangkeng.util.WinLoseDraw.DRAW;
import static org.yyama.jangkeng.util.WinLoseDraw.LOSE;
import static org.yyama.jangkeng.util.WinLoseDraw.WIN;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.jdo.JDOCanRetryException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import org.yyama.jangkeng.entity.OutcomeRate;
import org.yyama.jangkeng.util.Pose;
import org.yyama.jangkeng.util.WinLoseDraw;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class JangkengSarvice {
    private final Logger logger = Logger.getLogger(JangkengSarvice.class
            .getName());

    /**
     * ランダムにコンピュータのポーズを決める
     */
    public Pose getPose() {
        Random rnd = new Random();
        Pose p = Pose.ELSE;
        switch (rnd.nextInt(3)) {
        case 0:
            p = Pose.GU;
            break;
        case 1:
            p = Pose.CHOKI;
            break;
        case 2:
            p = Pose.PA;
            break;
        }
        return p;
    }

    /**
     * 勝敗の判定
     */
    public WinLoseDraw dicisionGame(Pose userPose, Pose compPose) {
        WinLoseDraw wld = WinLoseDraw.ELSE;
        switch (userPose) {
        case GU:
            wld = (compPose == GU) ? DRAW : (compPose == CHOKI) ? WIN : LOSE;
            break;
        case CHOKI:
            wld = (compPose == GU) ? LOSE : (compPose == CHOKI) ? DRAW : WIN;
            break;
        case PA:
            wld = (compPose == GU) ? WIN : (compPose == CHOKI) ? LOSE : DRAW;
            break;
        }
        return wld;
    }

    /**
     * データ初期化
     */
    private void initOutcomeRate(PersistenceManager pm) {
        OutcomeRate vode1 = new OutcomeRate(GU, 0, 0, 0, 0);
        OutcomeRate vode2 = new OutcomeRate(CHOKI, 0, 0, 0, 0);
        OutcomeRate vode3 = new OutcomeRate(PA, 0, 0, 0, 0);
        pm.makePersistentAll(vode1, vode2, vode3);
    }

    /**
     * 対戦成績の合計を計算し、返す
     * 
     * @throws Exception
     * 
     */
    private OutcomeRate getScoreLineTotal(List<OutcomeRate> list)
            throws Exception {

        OutcomeRate result = new OutcomeRate(Pose.ELSE, 0, 0, 0, 0);

        for (OutcomeRate or : list) {
            result.setCntOfMatch(result.getCntOfMatch() + or.getCntOfMatch());
            result.setCntOfWin(result.getCntOfWin() + or.getCntOfWin());
            result.setCntOfLose(result.getCntOfLose() + or.getCntOfLose());
            result.setCntOfDraw(result.getCntOfDraw() + or.getCntOfDraw());
        }

        logger.fine("対戦数が更新されました。" + result.getCntOfMatch());
        return result;
    }

    /**
     * 今回の対戦成績を記録する
     * 
     */
    public void setRecord(Pose compPose, WinLoseDraw victoryOrDedeat) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Key key = KeyFactory.createKey(OutcomeRate.class.getSimpleName(),
                compPose.getValue());
        OutcomeRate or = null;
        Transaction tx = null;
        try {

            or = pm.getObjectById(OutcomeRate.class, key);
        } catch (JDOObjectNotFoundException e) {
            initOutcomeRate(pm);
        }
        try {
            tx = pm.currentTransaction();
            tx.begin();
            switch (victoryOrDedeat) {
            case WIN:
                or.setCntOfLose(or.getCntOfLose() + 1);
                or.setCntOfMatch(or.getCntOfMatch() + 1);
                break;
            case LOSE:
                or.setCntOfWin(or.getCntOfWin() + 1);
                or.setCntOfMatch(or.getCntOfMatch() + 1);
                break;
            case DRAW:
                or.setCntOfDraw(or.getCntOfDraw() + 1);
                or.setCntOfMatch(or.getCntOfMatch() + 1);
                break;
            case ELSE:
                break;
            }
            tx.commit();
        } catch (JDOCanRetryException e) {
            logger.warning("DataStore書き込みに失敗。カウントせず続行します。");
            // finally区でロールバックされるので、特に何もしない。
        } finally {
            if (tx != null && tx.isActive())
                tx.rollback();
            if (pm != null && !pm.isClosed())
                pm.close();
        }
    }

    /**
     * 対戦成績の合計を返す
     * 
     */
    @SuppressWarnings("unchecked")
    public OutcomeRate getScoreline() throws Exception {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            String query = "select from " + OutcomeRate.class.getName();
            List<OutcomeRate> list = (List<OutcomeRate>) pm.newQuery(query)
                    .execute();
            return getScoreLineTotal(list);
        } finally {
            pm.close();
        }
    }
}
