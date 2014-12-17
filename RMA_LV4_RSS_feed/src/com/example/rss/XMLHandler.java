package com.example.rss;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import android.net.Uri;

public class XMLHandler {
	public static final String NEWS_ITEM_TAG = "item";
	public static final String NEWS_CHANNEL_TAG = "channel";
	public static final String NEWS_TITLE_TAG = "title";
	public static final String NEWS_CATEGORY_TAG = "category";
	public static final String NEWS_LINK_TAG = "link";
	public static final String NEWS_PUBLISHED_TAG = "pubDate";
	public static final String NEWS_DESCRIPTION_TAG = "description";
	public static final String NEWS_IMAGE_TAG = "enclosure";
	public static final SimpleDateFormat inputFormat = new SimpleDateFormat(
			"EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);

	public static ArrayList<News> parseNewsList(InputStream input) {
		ArrayList<News> newsList = new ArrayList<News>();
		News news = null;
		try {
			XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory
					.newInstance();
			XmlPullParser parser = xmlFactoryObject.newPullParser();
			parser.setInput(input, null);
			int event = parser.next();
			while (event != XmlPullParser.START_TAG
					|| !parser.getName().equals(NEWS_CHANNEL_TAG)) {
				if (event == XmlPullParser.END_DOCUMENT)
					return null;
				event = parser.next();
			}

			for (event = parser.next(); !(event == XmlPullParser.END_TAG && parser
					.getName().equals(NEWS_CHANNEL_TAG)); event = parser.next()) {
				if (event == XmlPullParser.START_TAG
						&& parser.getName().equals(NEWS_ITEM_TAG)) {
					news = XMLHandler.parseNews(parser);
					if (news != null) {
						newsList.add(news);
					}
				}
				if (parser.getEventType() == XmlPullParser.END_DOCUMENT)
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newsList;
	}

	private static News parseNews(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		if (parser == null)
			throw new NullPointerException();
		if (parser.getEventType() != XmlPullParser.START_TAG
				|| !parser.getName().equals(NEWS_ITEM_TAG))
			return null;
		News news = new News();
		int event;
		for (event = parser.next(); !(event == XmlPullParser.END_TAG && parser
				.getName().equals(NEWS_ITEM_TAG)); event = parser.next()) {
			if (event == XmlPullParser.START_TAG) {
				if (parser.getName().equals(NEWS_TITLE_TAG))
					news.Title = parser.nextText();
				if (parser.getName().equals(NEWS_DESCRIPTION_TAG))
					news.Description = parser.nextText();
				if (parser.getName().equals(NEWS_CATEGORY_TAG))
					news.Category = parser.nextText();
				if (parser.getName().equals(NEWS_LINK_TAG))
					news.Link = Uri.parse(parser.nextText());
				if (parser.getName().equals(NEWS_PUBLISHED_TAG))
					try {
						news.Published = inputFormat.parse(parser.nextText());
					} catch (ParseException e) {
						e.printStackTrace();
					}
				if (parser.getName().equals(NEWS_IMAGE_TAG)) {
					news.setImageByUrl(parser.getAttributeValue(null, "url"));
				}
			}
			if (parser.getEventType() == XmlPullParser.END_DOCUMENT)
				break;
		}
		return news;
	}

	public static void NewsListToXML(StringBuilder output,
			ArrayList<News> newsList) {
		appendTagStart(output, NEWS_CHANNEL_TAG);
		for (News news : newsList) {
			NewsToXML(output, news);
		}
		appendTagEnd(output, NEWS_CHANNEL_TAG);
	}

	public static void NewsToXML(StringBuilder output, News news) {
		appendTagStart(output, NEWS_ITEM_TAG);
		appendTag(output, NEWS_TITLE_TAG, news.Title);
		appendTag(output, NEWS_LINK_TAG, news.Link.toString());
		appendTag(output, NEWS_CATEGORY_TAG, news.Category);
		appendTag(output, NEWS_DESCRIPTION_TAG, news.Description);
		appendTag(output, NEWS_PUBLISHED_TAG,
				inputFormat.format(news.Published));
		appendTag(output, NEWS_PUBLISHED_TAG,
				inputFormat.format(news.Published));
		if (news.getImageUrl() != null) {
			output.append('<').append(NEWS_IMAGE_TAG).append(" url=\"")
					.append(news.getImageUrl()).append("\">");
			appendTagEnd(output, NEWS_IMAGE_TAG);
		}
		appendTagEnd(output, NEWS_ITEM_TAG);
	}

	private static void appendTag(StringBuilder output, String tag,
			String content) {
		appendTagStart(output, tag);
		output.append(content);
		appendTagEnd(output, tag);
	}

	private static void appendTagStart(StringBuilder output, String tag) {
		output.append('<').append(tag).append(">");
	}

	private static void appendTagEnd(StringBuilder output, String tag) {
		output.append("</").append(tag).append(">");
	}
}
