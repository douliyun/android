package fcl.activity;

import wb.DrivingBest.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginMethod extends Activity{
	Button zhanghao;
	Button shouji;
	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.login_method);  
        zhanghao = (Button)findViewById(R.id.zhanghao);
        shouji = (Button)findViewById(R.id.shouji);
        zhanghao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(LoginMethod.this,Login.class) ;    //跳转到账号密码登录界面  
                startActivity(intent);  
			}
		});
        shouji.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent1 = new Intent(LoginMethod.this,LoginMobile.class) ;    //跳转到账号密码登录界面  
                startActivity(intent1);
			}
		}); 
    }  
}
