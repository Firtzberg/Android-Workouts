package hr.nemamdomenu.rasturi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class HandleXML {
	public static final String inspiringPersonsTagName = "inspiringPersons";
	public static final String inspiringPersonTagName = "inspiringPerson";
	public static final String nameTagName = "name";
	public static final String imageTagName = "image";
	public static final String dateOfBirthTagName = "dateOfBirth";
	public static final String dateOfDeathTagName = "dateOfDeath";
	public static final String yearTagName = "year";
	public static final String monthTagName = "month";
	public static final String dayTagName = "day";
	public static final String CVTagName = "CV";
	public static final String citationsTagName = "citations";
	public static final String citationTagName = "citation";

	public static ArrayList<InspiringPerson> getInspiringPersons(
			InputStream input, Context context) {
		ArrayList<InspiringPerson> ips = new ArrayList<InspiringPerson>();
		InspiringPerson ip;
		try {
			XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory
					.newInstance();
			XmlPullParser parser = xmlFactoryObject.newPullParser();
			parser.setInput(input, null);
			int event = parser.next();
			if (event != XmlPullParser.START_TAG
					|| !parser.getName().equals(inspiringPersonsTagName))
				return null;
			event = parser.nextTag();
			while (!(event == XmlPullParser.END_TAG && parser.getName().equals(
					inspiringPersonsTagName))) {
				ip = HandleXML.getInspiringPerson(parser, context);
				if (ip != null)
					ips.add(ip);
				if (parser.getEventType() == XmlPullParser.END_DOCUMENT)
					break;
				event = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ips;
	}

	private static InspiringPerson getInspiringPerson(XmlPullParser parser,
			Context context) throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG
				|| !parser.getName().equals(inspiringPersonTagName))
			return null;
		InspiringPerson ip = new InspiringPerson(context);
		int event = parser.next();
		while (!(event == XmlPullParser.END_TAG && parser.getName().equals(
				inspiringPersonTagName))) {
			if (event == XmlPullParser.START_TAG) {
				if (parser.getName().equals(dateOfBirthTagName))
					ip.setDateOfBirth(getDate(parser));
				if (parser.getName().equals(dateOfDeathTagName))
					ip.setDateOfBirth(getDate(parser));
				if (parser.getName().equals(nameTagName))
					ip.setName(nextText(parser));
				if (parser.getName().equals(CVTagName))
					ip.setCV(nextText(parser));
				if (parser.getName().equals(citationsTagName))
					ip.citations.addAll(getCittations(parser));
				if (parser.getName().equals(imageTagName)) {
					try {
						ip.setImage(Drawable.createFromStream(context
								.getAssets().open(nextText(parser)), null));
					} catch (IOException e) {
					}
				}
			}
			if (parser.getEventType() == XmlPullParser.END_DOCUMENT)
				break;
			event = parser.next();
		}
		return ip;
	}

	private static ArrayList<String> getCittations(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		if (!parser.getName().equals(citationsTagName)
				|| parser.getEventType() != XmlPullParser.START_TAG)
			return null;
		int event = parser.next();
		ArrayList<String> citations = new ArrayList<String>();
		while (!(event == XmlPullParser.END_TAG && parser.getName().equals(
				citationsTagName))) {
			if (event == XmlPullParser.START_TAG
					&& parser.getName().equals(citationTagName))
				citations.add(nextText(parser));
			if (parser.getEventType() == XmlPullParser.END_DOCUMENT)
				break;
			event = parser.next();
		}
		return citations;
	}

	private static String nextText(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG)
			return "";
		String s = "";
		String tagName = parser.getName();
		int event = parser.next();
		if (event == XmlPullParser.TEXT)
			s = parser.getText();
		while (!(event == XmlPullParser.END_TAG && parser.getName().equals(
				tagName))) {
			if (parser.getEventType() == XmlPullParser.END_DOCUMENT)
				break;
			else
				event = parser.next();
		}
		return s;
	}

	private static Date getDate(XmlPullParser parser)
			throws XmlPullParserException, IOException, NumberFormatException {
		if (parser.getEventType() != XmlPullParser.START_TAG)
			return null;
		String endTagName = parser.getName();
		Integer year = null, month = null, day = null;
		String s = "";
		int event = parser.next();
		while (!(event == XmlPullParser.END_TAG && parser.getName().equals(
				endTagName))) {
			switch (event) {
			case XmlPullParser.TEXT:
				s = parser.getText();
				break;
			case XmlPullParser.END_TAG:
				if (parser.getName().equals(yearTagName))
					year = Integer.parseInt(s);
				else if (parser.getName().equals(monthTagName))
					month = Integer.parseInt(s);
				else if (parser.getName().equals(dayTagName))
					day = Integer.parseInt(s);
				break;
			}
			if (parser.getEventType() == XmlPullParser.END_DOCUMENT)
				break;
			event = parser.next();
		}
		if (year == null || month == null || day == null)
			return null;
		Calendar c = Calendar.getInstance();
		c.set(year, month, day);
		return c.getTime();
	}

}
