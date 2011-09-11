package net.fizzco.wassrclient.wasawasa.util;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class DownloadTask extends AsyncTask<String, Void, Bitmap> {
    // アイコンを表示するビュー
    private ImageView imageView;

    // コンストラクタ
    public DownloadTask(ImageView imageView) {
        this.imageView = imageView;
    }

    // バックグラウンドで実行する処理
    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap image = ImageCache.getImage(urls[0]);
        if (image == null) {
            image = HttpClient.getImage(urls[0]);
            ImageCache.setImage(urls[0], image);
        }
        return image;
    }

    // メインスレッドで実行する処理
    @Override
    protected void onPostExecute(Bitmap result) {
        this.imageView.setImageBitmap(result);
    }
}