package fcl.activity;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import wb.DrivingBest.R;
import wb.DrivingBest.R.id;
import wb.DrivingBest.R.layout;
import wb.DrivingBest.R.string;
import fcl.data.DBAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ExerciseActivity extends Activity {

	public static final int problemLimit = 900;
	public static final String label = "label";
	int curIndex;
	String myAnswer;
	
	int[] myWAset = new int[900];                                   // 以往错题
	
	int[] problemTurn = new int[900];
	int Option;                                       // 表示是随机 or 顺序
	int labelProblemID;

	String TESTSUBJECT;
	String TESTANSWER;
	String ANSWERA;
	String ANSWERB;
	String ANSWERC;
	String ANSWERD;
	String IMAGENAME;

	int TESTTPYE;
	int TESTBELONG;
	int EXPR1;

	boolean autoCheck;
	boolean auto2next;
	boolean auto2addWAset;
	EditText editText;
	TextView proTextView;
	ImageView imageview;
	RadioButton radioA;
	RadioButton radioB;
	RadioButton radioC;
	RadioButton radioD;
	RadioGroup radioGroup;
	Button forword_btn;
	Button next_btn;
	Button check_btn;
	Button addWAset_btn;
	TextView promptText;

	Cursor cursor;
	DBAdapter dbAdapter;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	// InputStream in;
	// BufferedReader br;
	FileInputStream fis;
	FileOutputStream fos;                            //用来存储数据


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exerciselayout);
		proTextView = (TextView) findViewById(R.id.pro_text);
		imageview = (ImageView) findViewById(R.id.imageview);
		radioA = (RadioButton) findViewById(R.id.radioA);
		radioB = (RadioButton) findViewById(R.id.radioB);
		radioC = (RadioButton) findViewById(R.id.radioC);
		radioD = (RadioButton) findViewById(R.id.radioD);
		forword_btn = (Button) findViewById(R.id.forwordBtn);
		next_btn = (Button) findViewById(R.id.nextBtn);
		check_btn = (Button) findViewById(R.id.checkBtn);
		addWAset_btn = (Button) findViewById(R.id.addWAsetBtn);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		promptText = (TextView) findViewById(R.id.promptText);
		
		Init();                                     // 图像、数据初始化
		settingInit();                               // 配置设定
		OnPaint();                                  // 重绘,将题目导入
		forword_btn.setOnClickListener(new OnClickListener() {
			// 上一题
			@Override
			public void onClick(View v) {
				if (curIndex == 0) {
					ShowToast("当前为第一题");
				} else {
					if (Option == DrivingBest.OPTION_WRONGEXERCISE) {
						int tindex = curIndex;
						while (--tindex >= 0) {
							if (myWAset[tindex] == 1) {
								curIndex = tindex;
								OnPaint();
								return;
							}
						}
						ShowToast("当前为第一题");
						return;
					} else {
						curIndex--;
						OnPaint();
					}
				}
			}
		});
		//下一题
		next_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (curIndex == problemLimit - 1) {
					ShowToast("当前为最后一题");
				} else {
					if (Option == DrivingBest.OPTION_WRONGEXERCISE) {
						int tindex = curIndex;
						while (++tindex < problemLimit) {
							if (myWAset[tindex] == 1) {
								curIndex = tindex;
								OnPaint();
								return;
							}
						}
						ShowToast("当前为最后一题");
						return;
					} else {
						curIndex++;
						OnPaint();
					}
				}
			}
		});

		//确认按钮
		check_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				int answerNum = radioGroup.getCheckedRadioButtonId();
				switch (answerNum) {
				case R.id.radioA:
					myAnswer = "A";
					break;
				case R.id.radioB:
					myAnswer = "B";
					break;
				case R.id.radioC:
					myAnswer = "C";
					break;
				case R.id.radioD:
					myAnswer = "D";
					break;
				case -1:
					myAnswer = "";
				default:
					myAnswer = "";
					break;
				}
				if (TESTTPYE == 2) {
					if (myAnswer == "A") {
						myAnswer = "对";
					} else if(myAnswer == "C"){
						myAnswer = "错";
					}
				}
				// ShowToast(myAnswer + " " + TESTANSWER);
				if (myAnswer.compareTo(TESTANSWER) == 0) {
					promptText.setText(R.string.prompt_right);
					promptText.setVisibility(View.VISIBLE);
					promptText.setTextColor(Color.GREEN);
					if (auto2next) {
						next_btn.performClick();
					}
				} else {
					promptText.setText(R.string.prompt_wrong);
					promptText.setText(promptText.getText().toString()
							+ TESTANSWER);
					promptText.setVisibility(View.VISIBLE);
					promptText.setTextColor(Color.RED);
					if (Option != DrivingBest.OPTION_WRONGEXERCISE
							&& auto2addWAset) {
						addWAset_btn.performClick();
					}
				}
			}
		});

		//加入错题库
		addWAset_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Option == DrivingBest.OPTION_WRONGEXERCISE) {
					myWAset[problemTurn[curIndex]] = 0;
					saveWaset();
					ShowToast("移除成功");
				} else {
					myWAset[problemTurn[curIndex]] = 1;
					ShowToast("加入成功");
				}
			}
		});

		//选择radio
		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if (autoCheck
								&& (radioA.isChecked() || radioB.isChecked()
										|| radioC.isChecked() || radioD
										.isChecked())) {
							check_btn.performClick();
						}
					}

				});
	}
	public void ShowToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	public void Init() {
		Bundle bundle = getIntent().getExtras();
		Option = bundle.getInt("option");
		try {
			dbAdapter = new DBAdapter(this);
			dbAdapter.open();                
			//打开数据库将数据保存到sharepreferences
			sharedPreferences = getSharedPreferences(
					DrivingBest.PREFERENCE_NAME, DrivingBest.MODE);
			// SharedPreferences存储方式
			editor = sharedPreferences.edit();
			// SharedPreferences.Editor editor = sharedPreferences.edit();

		} catch (Exception e) {
			Log.i("Init", "WA");
		}
		

		for (int i = 0; i < 900; i++) {
			// WAset[i] = 0;
			// mySelect[i] = "";
			problemTurn[i] = i;
		}

		//读取错题
		try {
			String Text = "";
			fis = openFileInput(WrongSetShowList.WAsetFilename);
			byte[] readBytes = new byte[fis.available()];
			while (fis.read(readBytes) != -1) {
				Text = new String(readBytes);
			}
			String[] tmp_waset = Text.split("#");
			String tmpString;
			if (tmp_waset[0].compareTo("") != 0) {
				for (int i = 0; i < tmp_waset.length; i++) {
					tmpString = tmp_waset[i].substring(0,
							tmp_waset[i].indexOf('.'));
					// ShowToast(tmpString);
					myWAset[Integer.parseInt(tmpString) - 1] = 1;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			// ShowToast(e.toString());
		}

		if (Option == DrivingBest.OPTION_RDM) {
			Random r = new Random();
			int t, rt1, rt2;
			for (int i = 0; i < 900; i++) {
				rt1 = r.nextInt(900);
				rt2 = r.nextInt(900);
				t = problemTurn[rt1];
				problemTurn[rt1] = problemTurn[rt2];
				problemTurn[rt2] = t;
			}
		}
		curIndex = 0;
		if (Option == DrivingBest.OPTION_WRONGEXERCISE) {
			curIndex = bundle.getInt("startfrom");
			addWAset_btn.setText("移除错题库");
		}
		cursor = dbAdapter.getAllData();
		Log.i("Count", cursor.getCount() + "");
	}

	public void settingInit() {                
		autoCheck = sharedPreferences.getBoolean(DrivingBest.CONFIG_AUTOCHECK,
				false);
		// 自动确认，从配置文件读取。
		labelProblemID = sharedPreferences.getInt(label, 0);
		// 到时候从配置文件读
		auto2next = sharedPreferences.getBoolean(DrivingBest.CONFIG_AUTO2NEXT,
				false);
		auto2addWAset = sharedPreferences.getBoolean(
				DrivingBest.CONFIG_AUTO2ADDWRONGSET, false);
	}

	public void OnPaint() {
		if (cursor.getCount() == 0) {
			Toast.makeText(this, "fuck", Toast.LENGTH_LONG).show();
		} else {
			/*
			 * 初始化View
			 */
			cursor.moveToPosition(problemTurn[curIndex]);
			radioGroup.clearCheck();
			TESTSUBJECT = cursor.getString(cursor
					.getColumnIndex(DBAdapter.TESTSUBJECT));
			TESTSUBJECT = TESTSUBJECT.replace("“|”", "下图");
			TESTANSWER = cursor.getString(cursor
					.getColumnIndex(DBAdapter.TESTANSWER));
			IMAGENAME = cursor.getString(cursor
					.getColumnIndex(DBAdapter.IMAGENAME));
			TESTTPYE = cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE));
			proTextView
					.setText((problemTurn[curIndex] + 1) + "." + TESTSUBJECT);
			promptText.setVisibility(View.GONE);      //结果显示
			promptText.setText("");
			// 图片处理
			if (IMAGENAME.compareTo("image") != 0) {
				InputStream inputStream;
				try {
					IMAGENAME = IMAGENAME.replace('-', '_');
					// Toast.makeText(this, IMAGENAME,
					// Toast.LENGTH_LONG).show();
					inputStream = super.getAssets().open(IMAGENAME);
					imageview.setImageDrawable(Drawable.createFromStream(
							inputStream, "assets"));
					imageview.setVisibility(View.VISIBLE);
					// imageview.setImageDrawable(Drawable.createFromPath("res.drawable."+IMAGENAME+".jpg"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Toast.makeText(this, e.toString(), Toast.LENGTH_LONG)
							.show();
					e.printStackTrace();
				}
			} else {
				imageview.setVisibility(View.GONE);
			}
			ANSWERA = cursor
					.getString(cursor.getColumnIndex(DBAdapter.ANSWERA));
			ANSWERB = cursor
					.getString(cursor.getColumnIndex(DBAdapter.ANSWERB));
			ANSWERC = cursor
					.getString(cursor.getColumnIndex(DBAdapter.ANSWERC));
			ANSWERD = cursor
					.getString(cursor.getColumnIndex(DBAdapter.ANSWERD));
			if (ANSWERA.compareTo("") == 0) {
				// 判断题
				radioA.setText("对");
				radioC.setText("错");
				radioB.setVisibility(View.INVISIBLE);
				radioD.setVisibility(View.INVISIBLE);
			} else {
				// 选择题
				radioA.setText("A." + ANSWERA);
				radioB.setText("B." + ANSWERB);
				radioC.setText("C." + ANSWERC);
				radioD.setText("D." + ANSWERD);
				radioA.setVisibility(View.VISIBLE);
				radioB.setVisibility(View.VISIBLE);
				radioC.setVisibility(View.VISIBLE);
				radioD.setVisibility(View.VISIBLE);
			}

		}
	}

	@Override
	protected void onRestart() {                 //重新开始
		settingInit();
		super.onRestart();
	}

	public void saveWaset() {               //保存错题
		try {
			String text = "";
			fos = openFileOutput(WrongSetShowList.WAsetFilename, MODE_PRIVATE);
			for (int i = 0; i < problemLimit; i++) {
				if (myWAset[i] == 1) {
					// cursor.moveToPosition(i);
					cursor.moveToPosition(i);
					text += (i + 1)
							+ "."
							+ cursor.getString(cursor
									.getColumnIndex(DBAdapter.TESTSUBJECT))
							+ "#";
				}
			}
			if (text.compareTo("") == 0)
				text = "#";
			fos.write(text.getBytes());
		} catch (Exception e) {
			// TODO: handle exception
			// ShowToast(e.toString());
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		// 保存错题库
		saveWaset();
		dbAdapter.close();
		super.onDestroy();
	}
}
