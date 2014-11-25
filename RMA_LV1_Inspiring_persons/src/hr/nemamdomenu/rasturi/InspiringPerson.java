package hr.nemamdomenu.rasturi;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InspiringPerson extends RelativeLayout implements View.OnClickListener{
	public final static int pictureSize = 85;
	private final TextView nameView;
	private Date dateOfBirth;
	private Date dateOfDeath;
	private java.text.DateFormat dateFormat;
	private final TextView datesView;
	private final TextView CVView;
	private final ImageView imageView;
	private static int freeId = 63428;
	public final ArrayList<String> citations = new ArrayList<String>();
	private static final Random random = new Random();

	public InspiringPerson(Context context) {
		super(context);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		this.setLayoutParams(lp);
		this.setPadding(0, 7, 0, 0);
		this.setOnClickListener(this);
		
		//initialize views with layout params
		RelativeLayout.LayoutParams rlp;
			//initialize imageView
			this.imageView = new ImageView(context);
			this.imageView.setId(freeId++);
			this.imageView.setAdjustViewBounds(true);
			this.imageView.setScaleType(ScaleType.FIT_CENTER);
				//adding to layout
				rlp = new RelativeLayout.LayoutParams(pictureSize, LayoutParams.WRAP_CONTENT);
				rlp.addRule(RelativeLayout.ALIGN_LEFT);
				rlp.addRule(RelativeLayout.ALIGN_TOP);
				rlp.setMargins(0, 0, 5, 0);
				this.addView(this.imageView, rlp);

			//initialize nameView
			this.nameView = new TextView(context);
			this.nameView.setId(freeId++);
			this.nameView.setTypeface(null, Typeface.BOLD);
			this.nameView.setTextColor(Color.BLACK);
				//adding to layout
				rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				rlp.addRule(RelativeLayout.RIGHT_OF, this.imageView.getId());
				rlp.addRule(RelativeLayout.ALIGN_RIGHT);
				rlp.addRule(RelativeLayout.ALIGN_TOP);
				this.addView(this.nameView, rlp);

			//initialize datesView
			this.datesView = new TextView(context);
			this.datesView.setId(freeId++);
				//adding to layout
				rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				rlp.addRule(RelativeLayout.RIGHT_OF, this.imageView.getId());
				rlp.addRule(RelativeLayout.ALIGN_RIGHT);
				rlp.addRule(RelativeLayout.BELOW, this.nameView.getId());
				this.addView(this.datesView, rlp);

			//initialize CVView
			this.CVView = new TextView(context);
			this.CVView.setId(freeId++);
			this.CVView.setTextColor(Color.BLACK);
				//adding to layout
				rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				rlp.addRule(RelativeLayout.RIGHT_OF, this.imageView.getId());
				rlp.addRule(RelativeLayout.ALIGN_RIGHT);
				rlp.addRule(RelativeLayout.BELOW, this.datesView.getId());
				this.addView(this.CVView, rlp);
		
		this.dateFormat = java.text.DateFormat.getDateInstance();
	}

	public String getName() {
		return (String) nameView.getText();
	}

	public void setName(String name) {
		this.nameView.setText(name);
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		this.refreshDatesView();
	}

	public Date getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(Date dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
		this.refreshDatesView();
	}

	private void refreshDatesView() {
		String s = "";
		if(this.dateOfBirth != null)
			s+= this.dateFormat.format(this.dateOfBirth);
		else s += "...";
		s += " - ";
		if(this.dateOfDeath != null)
			s+= this.dateFormat.format(this.dateOfDeath);
		else s += "...";
		this.datesView.setText(s);
	}

	public String getCV() {
		return (String) CVView.getText();
	}

	public void setCV(String cV) {
		CVView.setText(cV);
	}
	
	public Drawable getImage(){
		return this.imageView.getDrawable();
	}
	
	public void setImage(Drawable drawable){
		this.imageView.setImageDrawable(drawable);
	}

	@Override
	public void onClick(View v) {
		if(this.citations.size() < 1)return;
		Toast.makeText(this.getContext(), this.citations.get(random.nextInt(this.citations.size())), Toast.LENGTH_SHORT).show();
	}

}
