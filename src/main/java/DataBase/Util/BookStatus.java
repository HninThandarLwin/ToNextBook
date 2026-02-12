package DataBase.Util;

public enum BookStatus {
    ONGOING("Ongoing", "連載中"),
    COMPLETE("Complete", "完結"),
    DROP("Dropped", "打ち切り"),
    HIATUS("Hiatus", "休載中");

    private final String en;
    private final String jp;

    BookStatus(String en, String jp) {
        this.en = en;
        this.jp = jp;
    }

    public String getEn() {
        return en;
    }

    public String getJp() {
        return jp;
    }
}

