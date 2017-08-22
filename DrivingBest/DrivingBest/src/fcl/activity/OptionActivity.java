package fcl.activity;

import wb.DrivingBest.R;
import wb.DrivingBest.R.id;
import wb.DrivingBest.R.layout;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class OptionActivity extends Activity {
	
	//设置页面

	CheckBox chk_autocheck;                        //自动检查
	CheckBox chk_auto2next;                        //自动跳转下一题
	CheckBox chk_auto2addWAset;                    //自动加入错题集
	CheckBox chk_sound;                              //开启声音
	

	Button btn_saveSetting;                           //保存
	Button btn_return;                                 //返回

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.optionlayout);

		chk_autocheck = (CheckBox) findViewById(R.id.chk_autocheck);
		chk_auto2next = (CheckBox) findViewById(R.id.chk_auto2next);
		chk_auto2addWAset = (CheckBox) findViewById(R.id.chk_auto2addWAset);
		chk_sound = (CheckBox) findViewById(R.id.chk_sound);
		btn_saveSetting = (Button) findViewById(R.id.btn_savesetting);
		btn_return = (Button) findViewById(R.id.btn_return);
		
		//CONGIG初始化
		configInit();
		btn_saveSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveSettingAction();
				finish();
			}
		});
		
		btn_return.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public void saveSettingAction(){
		try {
			SharedPreferences sharedPreferences = getSharedPreferences(
					DrivingBest.PREFERENCE_NAME, DrivingBest.MODE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putBoolean(DrivingBest.CONFIG_AUTOCHECK, chk_autocheck.isChecked());
			editor.putBoolean(DrivingBest.CONFIG_AUTO2NEXT, chk_auto2next.isChecked());
			editor.putBoolean(DrivingBest.CONFIG_AUTO2ADDWRONGSET, chk_auto2addWAset.isChecked());
			editor.putBoolean(DrivingBest.CONFIG_SOUND, chk_sound.isChecked());
			editor.commit();
			ShowToast("保存配置成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ShowToast("保存配置错误");
			e.printStackTrace();
		}
	}
	public void ShowToast(String str) {   //用来显示提示文字
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	private void configInit() {                 //初始化
		// TODO Auto-generated method stub
		SharedPreferences sharedPreferences = getSharedPreferences(
				DrivingBest.PREFERENCE_NAME, DrivingBest.MODE);
		chk_autocheck.setChecked(sharedPreferences.getBoolean(DrivingBest.CONFIG_AUTOCHECK, false));
		chk_auto2next.setChecked(sharedPreferences.getBoolean(DrivingBest.CONFIG_AUTO2NEXT, false));
		chk_auto2addWAset.setChecked(sharedPreferences.getBoolean(DrivingBest.CONFIG_AUTO2ADDWRONGSET, false));
		chk_sound.setChecked(sharedPreferences.getBoolean(DrivingBest.CONFIG_SOUND, false));
		
	}
}
