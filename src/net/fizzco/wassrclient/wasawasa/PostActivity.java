package net.fizzco.wassrclient.wasawasa;

import java.util.ArrayList;

import net.fizzco.wassrclient.wasawasa.util.HttpClient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class PostActivity extends Activity {

	private ArrayList<WassrStatus> list = null;
	private ListView listViewTimeline = null;

	// アダプターの宣言
	private WassrAdapter adapter = null;

	// パブリックタイムラインのURL
	final String TIMELINE_URL = "http://api.wassr.jp/statuses/public_timeline.xml";
	final String FRIEND_TL_URL = "http://api.wassr.jp/statuses/friends_timeline.xml";

	// ヒトコト更新のURL
	final String POST_URL = "http://api.wassr.jp/statuses/update.json";

	private String wid = "";
	private String wps = "";
	private String tlurl = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Intentからユーザ情報を取得する
		Intent intent = getIntent();
		if (intent != null){
			if(intent.hasExtra("ID")){
				wid = intent.getStringExtra("ID");
				tlurl = FRIEND_TL_URL + "?id=" +  wid ;

				TextView loginid = (TextView) findViewById(R.id.lblLoginid);
				loginid.setText(wid.subSequence(0, wid.length()));
			}
			if(intent.hasExtra("PS")){
				wps = intent.getStringExtra("PS");
			}
		}

		listViewTimeline = (ListView)findViewById(R.id.listViewTimeline);

		// タイムラインの取得
		getTimeline(tlurl);

		// アダプターの作成
		adapter = new WassrAdapter(this, R.layout.list, list);
		listViewTimeline.setAdapter(adapter);

	}

	// タイムラインの表示
	public void getTimeline(String url) {
		// XMLパースクラスにお任せ
		WassrParser parser = new WassrParser(url, url, url);
		this.list = parser.parse();

	}

	// ボタン押下
	public void onClickButton(View view) {

		if (view.getId() == R.id.button1) {

			// POST
			EditText textcontent = (EditText) findViewById(R.id.editText01);
			String content = textcontent.getText().toString().trim();

			if (content.length() > 0) {
				// POST
				HttpClient.doPost(POST_URL, wid, wps, content);
				textcontent.setText(null);
			}

			// タイムラインの更新
			getTimeline(tlurl);
			adapter.clear();
			adapter = new WassrAdapter(this, R.layout.list, list);
			listViewTimeline.setAdapter(adapter);
		}

	}


}