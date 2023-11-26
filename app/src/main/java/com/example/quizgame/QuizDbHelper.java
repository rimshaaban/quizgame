package com.example.quizgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.quizgame.QuizContract.*;

import java.util.ArrayList;
import java.util.List;
public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 4;
    private static QuizDbHelper instance;
    private SQLiteDatabase db;
    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";
        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
    private void fillCategoriesTable() {
        Category c1 = new Category("Programming");
        insertCategory(c1);
        Category c2 = new Category("GENERAL");
        insertCategory(c2);
        Category c3 = new Category("FOOTBALL");
        insertCategory(c3);
    }
    public void addCategory(Category category) {
        db = getWritableDatabase();
        insertCategory(category);
    }
    public void addCategories(List<Category> categories) {
        db = getWritableDatabase();
        for (Category category : categories) {
            insertCategory(category);
        }
    }
    private void insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }
    private void fillQuestionsTable() {
        // prog easy
        Question q1 = new Question("Programming, Easy: what is the language that use for web ",
                "html", "java", "sql", 1,
                Question.DIFFICULTY_EASY, Category.PROGRAMMING);
        insertQuestion(q1);
        Question q2 = new Question("Programming, Easy: what is name the app that use for network",
                "packet trace", "netbeans", "labviwe", 1,
                Question.DIFFICULTY_EASY, Category.PROGRAMMING);
        insertQuestion(q2);
        Question q3 = new Question("Programming, Easy: what is name the app that use for database",
                "protues", "ltspice", "xammp", 3,
                Question.DIFFICULTY_EASY, Category.PROGRAMMING);
        insertQuestion(q3);
        //end prog easy
        // med prog
        Question q22 = new Question("Programming, MEDIUM: how many loop do we have in java ",
                "4", "3", "2", 2,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        insertQuestion(q22);
        Question q23 = new Question("Programming, MEDIUM: what is the output of x = 2 ; ++x",
                "4", "1", "3", 3,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        insertQuestion(q23);
        Question q24 = new Question("Programming, MEDIUM: what dose char option ues in java ",
                "integer", "double", "character", 1,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        insertQuestion(q24);
        //end med prog
        // general easy
        Question q4 = new Question("GENERAL, Easy:what is the capital of spain",
                "madrid", "barcelona", "roma", 1,
                Question.DIFFICULTY_EASY, Category.GENERAL);
        insertQuestion(q4);
        Question q5 =  new Question("GENERAL, Easy: where was the first case of coronavirus",
                "iran", "china", "italy", 2,
                Question.DIFFICULTY_EASY, Category.GENERAL);
        insertQuestion(q5);
        Question q6 =  new Question("GENERAL, Easy:In which city located eiffel tower",
                "london", "roma", "paris", 3,
                Question.DIFFICULTY_EASY, Category.GENERAL);
        insertQuestion(q6);
        //end general easy
        // med general
        Question q7 = new Question("GENERAL, Medium: what is the largest country",
                "USA", "Russia", "India", 2,
                Question.DIFFICULTY_MEDIUM, Category.GENERAL);
        insertQuestion(q7);
        Question q8 = new Question("GENERAL, Medium: What year did world war 2 happen ?",
                "1940", "1939", "1938", 2,
                Question.DIFFICULTY_MEDIUM, Category.GENERAL);
        insertQuestion(q8);
        Question q9 = new Question("GENERAL, Medium: What year did world war 2 end ",
                "1949", "1950", "1945", 3,
                Question.DIFFICULTY_MEDIUM, Category.GENERAL);
        insertQuestion(q9);
        // end med general
        //hard general
        Question q13 = new Question("GENERAL, hard:sweden norway finland and denmark used to known as :", // med general
                "Scandinavian", "Barbarian", "soviets", 1,
                Question.DIFFICULTY_HARD, Category.GENERAL);
        insertQuestion(q13);
        Question q14 = new Question("GENERAL, hard:Which scientific unit is named after an Italian nobleman?",
                "Pascal", "volt", "ohm", 2,
                Question.DIFFICULTY_HARD, Category.GENERAL);
        insertQuestion(q14);
        Question q15 = new Question("GENERAL, hard: A number 1, followed by one hundred zeroes is known by what name?",
                "Megatron", "Googol", "Nanomole", 2,
                Question.DIFFICULTY_HARD, Category.GENERAL);
        insertQuestion(q15);
        // end hard general
        // football hard
        Question q10 = new Question("FOOTBALL, Hard: who won the champions league in 2004",
                "milan", "porto", "real madrid", 2,
                Question.DIFFICULTY_HARD, Category.FOOTBALL);
        insertQuestion(q10);
        Question q11 = new Question("FOOTBALL, Hard: who won the euro cup in 2004",
                "italy", "protugal", "Greece", 3,
                Question.DIFFICULTY_HARD, Category.FOOTBALL);
        insertQuestion(q11);
        Question q12 = new Question("FOOTBALL, Hard: who won the ballon dor in 2007",
                "zidan", "kaka", "torees", 2,
                Question.DIFFICULTY_HARD, Category.FOOTBALL);
        insertQuestion(q12);
        //end hard football
        //med football
        Question q16 = new Question("FOOTBALL, Hard: in which country did the 2018 world cup happen ?",
                "USA", "france", "Russia", 3,
                Question.DIFFICULTY_MEDIUM, Category.FOOTBALL);
        insertQuestion(q16);
        Question q17 = new Question("FOOTBALL, Hard: who many times dose bayren wins the champions league",
                "6", "7", "9", 1,
                Question.DIFFICULTY_MEDIUM, Category.FOOTBALL);
        insertQuestion(q17);
        Question q18 = new Question("FOOTBALL, Hard: who won the first world cup ",
                "Uruguay", "italy", "brazil", 1,
                Question.DIFFICULTY_MEDIUM, Category.FOOTBALL);
        insertQuestion(q18);
        //end med football
        // easy football
        Question q19 = new Question("FOOTBALL, easy: who won the world cup in 2018",
                "brazil", "england", "france", 3,
                Question.DIFFICULTY_EASY, Category.FOOTBALL);
        insertQuestion(q19);
        Question q20 = new Question("FOOTBALL, easy: who won asia cup in 2017 ",
                "Qatar", "Iraq", "jaban", 1,
                Question.DIFFICULTY_EASY, Category.FOOTBALL);
        insertQuestion(q20);
        Question q21 = new Question("FOOTBALL, easy: who won UEFA champions league in 2020",
                "PSG", "Bayren", "Liverpool", 2,
                Question.DIFFICULTY_EASY, Category.FOOTBALL);
        insertQuestion(q21);
        //end easy football
    }
    public void addQuestion(Question question) {
        db = getWritableDatabase();
        insertQuestion(question);
    }
    public void addQuestions(List<Question> questions) {
        db = getWritableDatabase();
        for (Question question : questions) {
            insertQuestion(question);
        }
    }
    private void insertQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }
    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }
        c.close();
        return categoryList;
    }
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String selection = QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};
        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
