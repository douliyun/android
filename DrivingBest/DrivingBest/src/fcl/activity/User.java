package fcl.activity;

import java.io.File;
import wb.DrivingBest.R;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class User extends Activity {
	//ImageView  change;
   // private ImageUtils imageUtils = null;
	Button update_pwd;
    private ImageView change;
    private Bitmap mBitmap;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static Uri tempUri;
    private static final int CROP_SMALL_PICTURE = 2;
 
	@Override
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.users);
        update_pwd = (Button)findViewById(R.id.update_pwd);
        change = (ImageView)findViewById(R.id.change);
        update_pwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Intent intent = new Intent(User.this,PwdUpdate.class);
				startActivity(intent);
			}
		});
        change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				chooseDialog();
			}
		});
	}

	protected void chooseDialog() {
		// TODO �Զ����ɵķ������
		AlertDialog.Builder builder = new AlertDialog.Builder(User.this);
		builder.setTitle("����ͷ��");
		String[] items = {"ѡ����������","����"};
		builder.setNegativeButton("ȡ��", null);
		
		builder.setItems(items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
                case CHOOSE_PICTURE: 
                	// ѡ�񱾵���Ƭ
                    Intent openAlbumIntent = new Intent(
                            Intent.ACTION_GET_CONTENT);
                    openAlbumIntent.setType("image/*");
                    //��startActivityForResult�������������дonActivityResult()�������õ�ͼƬ���ü�����
                    startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                    break;
                case TAKE_PICTURE: // ����
                    Intent openCameraIntent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    tempUri = Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), "temp_image.jpg"));
                    // ���������õ���Ƭ���浽SD����Ŀ¼
                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                    startActivityForResult(openCameraIntent, TAKE_PICTURE);
                    break;
            }
			}
		});
		builder.show();	
		
	}
	//��mainActivity.java����дonActivityResult()�������ڸ��ݴ��صĲ�����������趨
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == User.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    cutImage(tempUri); // ��ͼƬ���вü�����
                    break;
                case CHOOSE_PICTURE:
                    cutImage(data.getData()); // ��ͼƬ���вü�����
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // �øղ�ѡ��ü��õ���ͼƬ��ʾ�ڽ�����
                    }
                    break;
            }
        }
    }
    private void setImageToView(Intent data) {
    	Bundle extras = data.getExtras();
        if (extras != null) {
            mBitmap = extras.getParcelable("data");
            //����ͼƬ�Ƿ��εģ�������һ�������ദ���Բ�Σ��ܶ�ͷ����Բ�Σ����ֹ��������Ϻܶ಻��������
            change.setImageBitmap(mBitmap);//��ʾͼƬ
            //������ط�����д���ϴ���ͼƬ���������Ĵ��룬���ڽ�����дһƪ�ⷽ��Ĳ��ͣ������ڴ�...
        }
		
	}

	//�ü�ͼƬ����
	private void cutImage(Uri uri) {
		if (uri == null) {
            Log.i("alanjet", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP���action�������ü�ͼƬ�õ�
        intent.setDataAndType(uri, "image/*");
        // ���òü�
        intent.putExtra("crop", "true");
        // aspectX aspectY �ǿ�ߵı���
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY �ǲü�ͼƬ���
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
		
	}
}
