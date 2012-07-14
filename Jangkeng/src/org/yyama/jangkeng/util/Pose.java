package org.yyama.jangkeng.util;

public enum Pose {
    GU(1, "img/gu.PNG"), CHOKI(2, "img/choki.PNG"), PA(3, "img/pa.PNG"), ELSE(
            4, "img/pa.PNG");

    int value;
    String imgFile;

    public String getImgFile() {
        return imgFile;
    }

    Pose(int i, String imgFile) {
        this.value = i;
        this.imgFile = imgFile;
    }

    public int getValue() {
        return value;
    }
}
