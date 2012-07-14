package org.yyama.jangkeng.entity;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.yyama.jangkeng.util.Pose;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class OutcomeRate {
    @PrimaryKey
    private Key key;

    @Persistent
    private int cntOfDraw;

    @Persistent
    private int cntOfLose;

    @Persistent
    private int cntOfMatch;

    @Persistent
    private int cntOfWin;

    public OutcomeRate(Pose key, int cntOfDraw, int cntOfLose, int cntOfMatch,
            int cntOfWin) {
        this.key = KeyFactory.createKey(OutcomeRate.class.getSimpleName(),
                key.getValue());
        this.cntOfDraw = cntOfDraw;
        this.cntOfLose = cntOfLose;
        this.cntOfMatch = cntOfMatch;
        this.cntOfWin = cntOfWin;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public int getCntOfDraw() {
        return cntOfDraw;
    }

    public void setCntOfDraw(int cntOfDraw) {
        this.cntOfDraw = cntOfDraw;
    }

    public int getCntOfLose() {
        return cntOfLose;
    }

    public void setCntOfLose(int cntOfLose) {
        this.cntOfLose = cntOfLose;
    }

    public int getCntOfMatch() {
        return cntOfMatch;
    }

    public void setCntOfMatch(int cntOfMatch) {
        this.cntOfMatch = cntOfMatch;
    }

    public int getCntOfWin() {
        return cntOfWin;
    }

    public void setCntOfWin(int cntOfWin) {
        this.cntOfWin = cntOfWin;
    }

}
