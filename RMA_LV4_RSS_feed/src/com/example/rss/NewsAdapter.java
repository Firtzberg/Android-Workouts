package com.example.rss;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {
	Context ctx;
	private String CategoryFilter = null;
	ArrayList<News> All;
	ArrayList<News> Filtered;
	public static final SimpleDateFormat outputFormat = new SimpleDateFormat("dd. MM. yyyy.", Locale.US);
	public static String AllCategories = "All Cateogries";

	public NewsAdapter(Context ctx) {
		super();
		this.ctx = ctx;
		this.All = new ArrayList<News>();
		this.Filtered = this.All;
	}
	
	public void addRange(ArrayList<News> range){
		this.All.addAll(range);
		filter();
	}
	
	public void clear(){
		this.All.clear();
		filter();
	}

	@Override
	public int getCount() {
		return this.Filtered.size();
	}

	@Override
	public Object getItem(int position) {
		return Filtered.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(ctx, R.layout.list_item_news, null);
		}
		News current = Filtered.get(position);
		ImageView ivPoster = (ImageView) convertView
				.findViewById(R.id.news_item_image);
		TextView tvTitle = (TextView) convertView
				.findViewById(R.id.news_item_title);
		TextView tvCategory = (TextView) convertView
				.findViewById(R.id.news_item_category);
		TextView tvPublished = (TextView) convertView
				.findViewById(R.id.news_item_published);
		TextView tvDescription = (TextView) convertView
				.findViewById(R.id.news_item_description);
		current.setImageinView(ivPoster);
		tvTitle.setText(current.Title);
		tvCategory.setText(current.Category);
		tvPublished.setText(outputFormat.format(current.Published));
		tvDescription.setText(current.Description);
		return convertView;
	}
	
	public ArrayList<String> getCategoryList(){
		ArrayList<String> result = new ArrayList<String>();
		result.add(AllCategories);
		for(News news : All) {
			if(result.contains(news.Category))
				continue;
			result.add(news.Category);
		}
		return result;
	}
	
	private void filter(){
		if(this.CategoryFilter == null|| this.CategoryFilter == AllCategories){
			Filtered = All;
			this.notifyDataSetInvalidated();
			return;
		}
		Filtered = new ArrayList<News>();
		for(News news : All){
			if(news.Category == this.CategoryFilter)
				Filtered.add(news);
		}
		this.notifyDataSetInvalidated();
	}
	
	public void setCategoryFilter(String filter){
		this.CategoryFilter = filter;
		filter();
	}
	
	public String ToXML(){
	      StringBuilder sb = new StringBuilder();
	      XMLHandler.NewsListToXML(sb, All);
	      return sb.toString();
	}
}