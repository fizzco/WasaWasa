package net.fizzco.wassrclient.wasawasa;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@SuppressWarnings("rawtypes")
public class WassrAdapter extends ArrayAdapter{

	private ArrayList items;
	LayoutInflater inflater;

	// レイアウトファイルのIDを宣言
	int layoutId;

	@SuppressWarnings("unchecked")
	public WassrAdapter(Context context, int textViewResourceId, ArrayList items) {
		super(context, textViewResourceId, items);
		this.items = items;
		this.inflater = (LayoutInflater) context
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// レイアウトファイルのIDを取得
		layoutId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		WassrViewHolder holder;

		// ビューを受け取る
		View view = convertView;
		if ( view == null ){
			// 受け取ったビューがnullなら新しくビューを生成
			view = inflater.inflate(layoutId, null);

			// 背景画像をセットする
			//view.setBackgroundResource(R.drawble.back);

			TextView screenName = (TextView)view.findViewById(R.id.TextViewName);
			screenName.setTypeface(Typeface.DEFAULT_BOLD);

			TextView text = (TextView)view.findViewById(R.id.TextViewText);
			//ImageView imageView = (ImageView)view.findViewById(R.id.ImageViewIcon);

			holder = new WassrViewHolder();
			holder.name = screenName;
			holder.text = text;
			//holder.iconImg = imageView;

			view.setTag(holder);

		} else {
			// nullでないなら再利用
			holder = (WassrViewHolder)view.getTag();
		}


		// 表示すべきデータの取得
//	    TwitterStatus item = (TwitterStatus)items.get(position);
		WassrStatus item = (WassrStatus)items.get(position);
		holder.name.setText(item.getScreenName());
		holder.text.setText(item.getText());

//		//画像がない場合は取得しにいく
//		String imageUrl = item.getProfileImageUrl();
//		Bitmap image = ImageCache.getImage(imageUrl);
//		if (image == null){
//			DownloadTask task = new DownloadTask(holder.iconImg);
//			task.execute(imageUrl);
//		}

		return view;
	}

}
