package net.fizzco.wassrclient.wasawasa;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

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

	/**
	 *  初期表示処理
	 */
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
		getTimeline(tlurl,wid,wps);
	}

	/**
	 * TL取得表示
	 * @param url
	 */
//	public void getTimeline(String url) {
	public void getTimeline(String url,String id, String ps){


		// いまの方法ではかぎっ子は拾えない。


		// XMLパースクラスにお任せ
		WassrParser parser = new WassrParser(url, id, ps);
		this.list = parser.parse();

		// アダプターの作成
		if (adapter != null){
			adapter.clear();
		}
		adapter = new WassrAdapter(this, R.layout.list, list);
		listViewTimeline.setAdapter(adapter);
	}

	/**
	 * ボタン押下
	 * @param view
	 */
	public void onClickButton(View view) {
//		if (view.getId() == R.id.post) {
			// タイムラインの更新
			getTimeline(tlurl,wid,wps);
//		}
	}


}