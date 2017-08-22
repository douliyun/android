package fcl.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



/**
 *这个方法是关于数据库的操作
 * @author wb
 *
 */
//private final static String DATABASE_CREATE = "create table " + DATABSE_TABLE + " (TestID integer primary key autoincrement,"  
//+ "TestSubject text not null, TestAnswer text not null, TestType integer,TestBelong integer,AnswerA text," +
//		"AnswerB text,AnswerC text,AnswerD text,ImageName text,Expr1 integer);"; 

public class DBAdapter {
	public final static String DATABSE_TABLE = "TestSubject";
	public static final String TESTID = "TestID";
	public static final String TESTSUBJECT = "TestSubject";
	public static final String TESTANSWER = "TestAnswer";
	public static final String ANSWERA = "AnswerA";
	public static final String ANSWERB = "AnswerB";
	public static final String ANSWERC = "AnswerC";
	public static final String ANSWERD = "AnswerD";
	
	public static final String TESTTPYE = "TestType";
	public static final String TESTBELONG = "TestBelong";
	public static final String EXPR1 = "Expr1";
	public static final String IMAGENAME = "ImageName";
	
	
	
	
//一个DataBaseHelper类实例
	private DBHelper dataBaseHelper;
	//Context
	private Context context;
	//SQLiteDatabase;
	SQLiteDatabase sqLiteDatabase;
	
	
	public DBAdapter(Context context){
		//获得上下文信息context
		this.context = context;
	}
	/*
	 * Open the Database;
	 */
	public void open(){
		dataBaseHelper = new DBHelper(context);
		try {
			sqLiteDatabase = dataBaseHelper.getWritableDatabase();
		} catch (Exception e) {
			// TODO: handle exception
			sqLiteDatabase = dataBaseHelper.getReadableDatabase();
			Log.i("open-->",e.toString());
		}
	}
	 // Close the Database
	public void close(){
		sqLiteDatabase.close();
	}
	
	/*
	 * 
	 */
	public long DBInsert(ContentValues cv){
		return sqLiteDatabase.insert(DBAdapter.DATABSE_TABLE,null,cv);
	}
	
	 public Cursor getAllData(){  
		 	//System.out.println("ASD");
	        String[] searchResult =  {TESTID,TESTSUBJECT, TESTANSWER,TESTTPYE, TESTBELONG
	        		,ANSWERA,ANSWERB,ANSWERC,ANSWERD,IMAGENAME,EXPR1};  
//	        Cursor tcursor = sqLiteDatabase.query(dataBaseHelper.DATABSE_TABLE, searchResult, null, null, null, null, null); 
//	        System.out.println(tcursor.getString(tcursor
//					.getColumnIndex(NotepadDbAdapter.TITLE)));
//	        System.out.println(tcursor.getString(tcursor
//					.getColumnIndex(NotepadDbAdapter.CREATED)));
	        Log.i("GetAllData","YES");
	        return sqLiteDatabase.query(dataBaseHelper.DATABSE_TABLE, searchResult, null, null, null, null, null);   
	 
		 } 

}
