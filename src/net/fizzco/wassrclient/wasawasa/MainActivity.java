package net.fizzco.wassrclient.wasawasa;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * メイン画面
 * @author fizzco
 *
 */
public class MainActivity extends Activity {

	private ArrayList<WassrStatus> list = null;
	private ListView listViewTimeline = null;
	// アダプターの宣言
	private WassrAdapter adapter = null;
	// パブリックタイムラインのURL
	final String FRIEND_TL_URL = "http://api.wassr.jp/statuses/friends_timeline.xml";

	private String wid = "";
	private String wps = "";

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
			if(intent.hasExtra("WID")){
				wid = intent.getStringExtra("WID");
				TextView loginid = (TextView) findViewById(R.id.lblLoginid);
				loginid.setText("id:"+ wid.subSequence(0, wid.length()));
			}
			if(intent.hasExtra("WPW")){
				wps = intent.getStringExtra("WPW");
			}
		}
		listViewTimeline = (ListView)findViewById(R.id.listViewTimeline);
		// タイムラインの取得
		getTimeline();
	}

	/**
	 * TL取得表示
	 * @param url
	 */
	public void getTimeline(){

		// XMLパースクラスにお任せ
		WassrParser parser = new WassrParser(FRIEND_TL_URL,wid, wps);
		this.list = parser.parse();

		// アダプターの作成
		if (adapter != null){
			adapter.clear();
		}
		adapter = new WassrAdapter(this, R.layout.list, list);
		listViewTimeline.setAdapter(adapter);
		Toast.makeText(this, "refresh now.", Toast.LENGTH_SHORT);
	}

	/**
	 * ボタン押下
	 * @param view
	 */
	public void onClickButton(View view) {
		
		if (view.getId() == R.id.refresh) {
			// タイムラインの更新
			getTimeline();
		}
	}
	/**
	 * menuボタン押下時
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 0, 0, "post");
		menu.add(Menu.NONE, 1, 1, "...");
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * menu	選択時
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 0:
			Intent intent =new Intent(getApplicationContext(), PostActivity.class);
			intent.putExtra("WID", wid); 
			intent.putExtra("WPW", wps);
			startActivity(intent);
			getTimeline();
			break;
		case 1:
			Toast.makeText(this, "…", Toast.LENGTH_SHORT).show();
			break;
		}
		return true;
	}
}