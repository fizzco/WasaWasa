package net.fizzco.wassrclient.wasawasa;

import org.apache.http.HttpStatus;

import net.fizzco.wassrclient.wasawasa.R;
import net.fizzco.wassrclient.wasawasa.util.HttpClient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	}

	// ボタン押下処理
	public void onClickButton(View view) {
		if(view.getId() == R.id.btnLogin){
			TextView message = (TextView) findViewById(R.id.textView2);		

			// Login情報
			String wasid = ((EditText)findViewById(R.id.edtId)).getText().toString().trim();
			String wasps = ((EditText)findViewById(R.id.edtPass)).getText().toString().trim();
			if ((wasid.length()!=0)&&(wasps.length() != 0)) {
				
				message.setText("waitting...");

				// 足跡取得を投げて、401エラーをひろう。
				if (HttpClient.doPost("http://api.wassr.jp/footmark/recent.json",
						wasid, wasps , "") == HttpStatus.SC_UNAUTHORIZED){

					// ログイン失敗したらそれを表示する。
					message.setText("401 Authorization required.");

				} else {

					// 認証OK
					Log.d("WasaWasa!", "id:"+ wasid + "/pass:"+wasps);

					message.setText("LogIn .... Success!");

					Intent intent =new Intent(getApplicationContext(), MainActivity.class);
					intent.putExtra("WID", wasid);
					intent.putExtra("WPW", wasps);
					startActivity(intent);

					finish(); // Login画面は破棄

					Log.d("WasaWasa!", "startActivity(intent);");

				}
			}
		}
	}
}