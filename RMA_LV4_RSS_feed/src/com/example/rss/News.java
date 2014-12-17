package com.example.rss;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

public class News {
	public String Title;
	public String Description;
	public String Category;
	public Uri Link;
	public Date Published;
	private Bitmap Image;
	private URL ImageUrl;
	public static Bitmap defaultImage;

	public News() {
	}

	public void setImageinView(final ImageView img) {
		if (Image == null) {
			if (ImageUrl == null) {
				Image = defaultImage;
				img.setImageBitmap(Image);
			} else {
				AsyncTask<String, Void, Bitmap> Task = new AsyncTask<String, Void, Bitmap>() {

					private Exception exception;

					@Override
					protected Bitmap doInBackground(String... ignore) {
						try {
							return BitmapFactory.decodeStream(ImageUrl
									.openStream());
						} catch (Exception e) {
							this.exception = e;
							return null;
						}
					}

					@Override
					protected void onPostExecute(Bitmap bmp) {
						if (exception != null) {
							Toast.makeText(img.getContext(),
									"Problems connecting to the internet",
									Toast.LENGTH_LONG).show();
							exception.printStackTrace();
							return;
						}
						Image = bmp;
						img.setImageBitmap(bmp);
					}
				};
				Task.execute();
			}
		} else
			img.setImageBitmap(Image);
	}

	public void setImageByUrl(String url) throws MalformedURLException {
		this.ImageUrl = new URL(url);
	}

	public String getImageUrl() {
		if (ImageUrl == null)
			return null;
		return this.ImageUrl.toString();
	}
}
