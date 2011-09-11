package net.fizzco.wassrclient.wasawasa;

import java.io.StringReader;
import java.util.ArrayList;

import net.fizzco.wassrclient.wasawasa.util.HttpClient;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class WassrParser {
	private static final String STATUSES = "statuses";
    private static final String STATUS = "status";
    private static final String TEXT = "text";
    private static final String SCREEN_NAME = "screen_name";
    private static final String PROFILE_IMAGE_URL = "profile_image_url";

    private String url;
    private String id;
    private String ps;

    public WassrParser(String url,String id, String ps) {
//    public WassrParser(String urlStr){
        this.url = url;
        this.id = id;
        this.ps = ps;
    }

    /**
     *
     * これだと鍵っ子が見えない。
     *
     * @return
     */
    public ArrayList<WassrStatus> parse() {
        ArrayList<WassrStatus> list = null;
        XmlPullParser parser = Xml.newPullParser();
        try {


//        	HttpClient.doPost("http://api.wassr.jp/footmark/recent.json",
//					wasid, wasps , "");

        	byte[] data = HttpClient.getByteArrayFromURL(url);



            parser.setInput(new StringReader(new String(data, "UTF-8")));
            int eventType = parser.getEventType();
            WassrStatus currentStatus = null;
            boolean isFinished = false;

            while (eventType != XmlPullParser.END_DOCUMENT && !isFinished) {
                String name = null;
                switch (eventType) {

                case XmlPullParser.START_DOCUMENT:

                	list = new ArrayList<WassrStatus>();
                    break;

                case XmlPullParser.START_TAG:
                    name = parser.getName();

                    if (name.equalsIgnoreCase(STATUS)) {

                    	currentStatus = new WassrStatus();

                    } else if (currentStatus != null) {

                        if (name.equalsIgnoreCase(TEXT)) {
                            currentStatus.setText(parser.nextText());

                        } else if (name.equalsIgnoreCase(SCREEN_NAME)) {
                            currentStatus.setScreenName(parser.nextText());

                        } else if (name.equalsIgnoreCase(PROFILE_IMAGE_URL)) {
                            // アイコンURLを取得
                            currentStatus.setProfileImageUrl(parser.nextText());
                        }
                    }
                    break;

                case XmlPullParser.END_TAG:

                    name = parser.getName();
                    if (name.equalsIgnoreCase(STATUS) && currentStatus != null) {
                        list.add(currentStatus);

                    } else if (name.equalsIgnoreCase(STATUSES)) {
                        isFinished = true;

                    }
                    break;
                }

                eventType = parser.next();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}