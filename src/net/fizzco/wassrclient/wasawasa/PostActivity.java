package net.fizzco.wassrclient.wasawasa;

import org.apache.http.HttpStatus;

import net.fizzco.wassrclient.wasawasa.util.HttpClient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ヒトコト投稿画面
 * 
 * @author fizzco
 */
public class PostActivity extends Activity {

	// ヒトコト更新のURL
	final String POST_URL = "http://api.wassr.jp/statuses/update.json";

	private String wid = "";
	private String wps = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post);

		// Intentからユーザ情報を取得する
		Intent intent = getIntent();
		if (intent != null){
			if(intent.hasExtra("WID")){
				wid = intent.getStringExtra("WID");
				TextView loginid = (TextView) findViewById(R.id.lblLoginid);
				loginid.setText("id:" + wid.subSequence(0, wid.length()));
			}
			if(intent.hasExtra("WPW")){
				wps = intent.getStringExtra("WPW");
			}
		}
	}

	// ボタン押下
	public void onClickButton(View view) {
		if (view.getId() == R.id.button1) {
			// POST
			EditText textcontent = (EditText) findViewById(R.id.editText01);
			String content = textcontent.getText().toString().trim();
			if (content.length() > 0) {
				// POST
				if(HttpClient.doPost(POST_URL, wid, wps, content) == HttpStatus.SC_OK){
					finish();
				} else {
					Toast.makeText(this, "failed.", Toast.LENGTH_SHORT);
				}
			}
		}
	}
}