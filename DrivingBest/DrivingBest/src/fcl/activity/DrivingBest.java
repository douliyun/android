package fcl.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.PublicKey;

import fcl.data.DBAdapter;
import wb.DrivingBest.R;
import wb.DrivingBest.R.id;
import wb.DrivingBest.R.layout;
import wb.DrivingBest.R.raw;
import android.R.string;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DrivingBest extends Activity {
	/** Called when the activity is first created. */
	public static final int OPTION_ORDER = 1;
	public static final int OPTION_RDM = 2;
	public static final int OPTION_TEST = 3;
	public static final int OPTION_WRONGEXERCISE = 4;
	public static final int MODE = MODE_PRIVATE;
	public static final String PREFERENCE_NAME = "SaveSetting";
	public static final String CONFIG_AUTOCHECK = "config_autocheck";
	public static final String CONFIG_AUTO2NEXT = "config_auto2next";
	public static final String CONFIG_AUTO2ADDWRONGSET = "config_auto2addwrongset";
	public static final String CONFIG_SOUND = "config_SOUND";
	public static final String CONFIG_CHECKBYRANDOM = "config_checkbyrandom";
	private TextView tv;
	

	private Button btn_order ;                                  //顺序练习
	private Button btn_rdm  ;                                     //随机练习
	private Button btn_test ;                                   //模拟考试
	private Button btn_myWAset;                                   //错题集
	private Button btn_option ;                                 //设置
	private Button head;                                         //跳转登录
	private Button btn_about ;                                     //关于
	private Button btn_exit ;                                       //退出

	Dialog dialog;           //弹出的提示框

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		btn_order = (Button) findViewById(R.id.btn_order);
		btn_rdm = (Button) findViewById(R.id.btn_rdm);
		btn_test = (Button) findViewById(R.id.btn_test);
		btn_myWAset = (Button) findViewById(R.id.btn_myWAset);
		btn_option = (Button) findViewById(R.id.btn_option);

		btn_about = (Button) findViewById(R.id.btn_about);
		btn_exit = (Button) findViewById(R.id.btn_exit);
		head = (Button)findViewById(R.id.head);
		
		head.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent0 = new Intent();
				intent0.setClass(DrivingBest.this, User.class);            //跳转到个人信息界面
				startActivity(intent0);
			}
		});

		btn_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent();
				intent1.putExtra("option", OPTION_ORDER);               
				//通过intent.putExtra方法来检索对应类型的数据，比如你的intent.putExtra("key", "hello, world!");,
				//利用putExtra接收传递的信息数据
				intent1.setClass(DrivingBest.this, ExerciseActivity.class);
				startActivity(intent1);
			}
		});

		btn_rdm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent();
				intent2.putExtra("option", OPTION_RDM);
				intent2.setClass(DrivingBest.this, ExerciseActivity.class);
				startActivity(intent2);
			}
		});
		
		//错题集
		btn_myWAset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent3 = new Intent();
				intent3.setClass(DrivingBest.this, WrongSetShowList.class);
				startActivity(intent3);
				
			}
		});

		btn_test.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent4 = new Intent();
				intent4.setClass(DrivingBest.this, ExamActivity.class);
				startActivity(intent4);
			}
		});
		/*
		 * 设置 
		 */
		btn_option.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent5 = new Intent();
				intent5.setClass(DrivingBest.this, OptionActivity.class);
				startActivity(intent5);
			}
		});
		btn_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				exitdialog();
			}
		});
		
		btn_about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				aboutdialog();
			}
		});
	}

	


	protected void exitdialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("确认退出吗？");

		builder.setTitle("提示");

		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface v, int which) {
						// TODO Auto-generated method stub
						// dialog.dismiss();
						finish();
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	protected void aboutdialog() {             //关于的提示框
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("DrivingBest By 范成磊");

		builder.setTitle("关于");

		builder.setPositiveButton("确定",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// dialog.dismiss();
						// this.finish();
					}
				});
		builder.create().show();
	}



}