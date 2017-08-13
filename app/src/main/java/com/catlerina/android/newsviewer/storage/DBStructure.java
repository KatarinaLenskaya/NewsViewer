package com.catlerina.android.newsviewer.storage;

public class DBStructure {
    public final static int DB_VERSION = 1;

    public static final String DB_NAME = "news_cache_db";
    public static final String DB_TBL_ARTICLE = "article";
    public final static String DB_COL_ID_PRIMARY = "_id";
    public final static String DB_COL_ID = "article_id";
    public final static String DB_COL_TITLE = "article_title";
    public final static String DB_COL_DESCRIPTION = "article_description";
    public final static String DB_COL_AUTHOR = "article_author";
    public final static String DB_COL_DATE = "published_at";
    public final static String DB_COL_URL = "article_url";
    public final static String DB_COL_IMAGE_URL = "image_url";

    public final static String CREATE_ARTICLE_TBL=
            "create table " + DB_TBL_ARTICLE + ("(")+
                    DB_COL_ID_PRIMARY + " integer primary key autoincrement, " +
                    DB_COL_ID + " integer, " +
                    DB_COL_TITLE + " text," +
                    DB_COL_DESCRIPTION + " text, " +
                    DB_COL_AUTHOR + " text," +
                    DB_COL_DATE + " datetime, " +
                    DB_COL_URL + " text," +
                    DB_COL_IMAGE_URL + " text," +
                    " UNIQUE ( " + DB_COL_ID + " ) ON CONFLICT IGNORE" +
                    ");";

    private final static int MAX_NOT_NULL_ROW_COUNT = 28;
    private final static int MAX_NULL_ROW_COUNT = 2;
    public final static String CREATE_TRIGGER_ON_ARTICLE_INSERT =
            "create trigger [rows_limit_on_insert] after insert " +
                    " on " + DB_TBL_ARTICLE +
                    " begin " +

                    " delete from " + DB_TBL_ARTICLE +
                    " where " + DB_COL_DATE +
                    " is null and " + DB_COL_ID_PRIMARY +
                    " not in (select " + DB_COL_ID_PRIMARY +
                    " from " + DB_TBL_ARTICLE +
                    " where " + DB_COL_DATE +
                    " is null order by " + DB_COL_ID_PRIMARY +
                    " desc limit " + MAX_NULL_ROW_COUNT + " );" +

                    " delete from " + DB_TBL_ARTICLE +
                    " where " + DB_COL_DATE +
                    " not in (select " + DB_COL_DATE +
                    " from " + DB_TBL_ARTICLE +
                    " order by " + DB_COL_DATE +
                    " desc limit " + MAX_NOT_NULL_ROW_COUNT +
                    " ); end";

    public static final String TBLS_DELETE =
            "DROP TABLE IF EXISTS " + DB_TBL_ARTICLE;

}
