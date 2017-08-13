package com.catlerina.android.newsviewer.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.catlerina.android.newsviewer.model.Article;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Database {

    private DBHelper helper;
    private Context context;
    private SQLiteDatabase database;

    public Database(Context context) {
        this.context = context;
    }

    public void open(){
        helper = new DBHelper(context, DBStructure.DB_NAME, null, DBStructure.DB_VERSION);
        database = helper.getWritableDatabase();
    }

    public void close(){
        if (database!=null) database.close();
        if (helper!=null) helper.close();
    }

    public void insertArticles(List<Article> articles){
        if(articles.size()!=0){
            for(Article article:articles){
                database.insert(DBStructure.DB_TBL_ARTICLE, null, getContentValues(article));
            }
        }
    }

    private ContentValues getContentValues(Article article){

        ContentValues cv = new ContentValues();
        cv.put(DBStructure.DB_COL_ID, article.getId());
        cv.put(DBStructure.DB_COL_TITLE, article.getTitle());
        cv.put(DBStructure.DB_COL_DESCRIPTION, article.getDescription());
        cv.put(DBStructure.DB_COL_AUTHOR, article.getAuthor());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = null;
        if(article.getDate()!=null)
            date = format.format(article.getDate());

        cv.put(DBStructure.DB_COL_DATE, date);
        cv.put(DBStructure.DB_COL_URL, article.getArticleLink().toString());
        cv.put(DBStructure.DB_COL_IMAGE_URL, article.getImageLink().toString());

        return cv;
    }

    public ArrayList<Article> getArticles(){
        ArrayList<Article> articles = new ArrayList<>();
        NewsCursorWrapper cursor = articlesQuery();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                articles.add(cursor.getArticle());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return articles;
    }

    private NewsCursorWrapper articlesQuery(){
        Cursor cursor = database.query(DBStructure.DB_TBL_ARTICLE, null,null,null,null,null,DBStructure.DB_COL_DATE +" desc");
        return new NewsCursorWrapper(cursor);
    }

    public class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DBStructure.CREATE_ARTICLE_TBL);
            db.execSQL(DBStructure.CREATE_TRIGGER_ON_ARTICLE_INSERT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DBStructure.TBLS_DELETE);
            onCreate(db);
        }
    }

    public class NewsCursorWrapper extends CursorWrapper {

        public NewsCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Article getArticle(){
            int id = getInt(getColumnIndex(DBStructure.DB_COL_ID));
            String title = getString(getColumnIndex(DBStructure.DB_COL_TITLE));
            String description = getString(getColumnIndex(DBStructure.DB_COL_DESCRIPTION));
            String author = getString(getColumnIndex(DBStructure.DB_COL_AUTHOR));
            Date date = null;
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String stringDate = getString(getColumnIndex(DBStructure.DB_COL_DATE));
                if (stringDate != null) {
                    date = format.parse(stringDate);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Uri articleURL = Uri.parse(getString(getColumnIndex(DBStructure.DB_COL_URL)));
            Uri imageURL = Uri.parse(getString(getColumnIndex(DBStructure.DB_COL_IMAGE_URL)));

            return new Article(id, author, title, description, imageURL, articleURL, date);
        }
    }
}
