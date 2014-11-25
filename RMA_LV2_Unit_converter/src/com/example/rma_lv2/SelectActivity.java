package com.example.rma_lv2;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SelectActivity extends Activity {

	public Spinner FromUnitSpinner;
	public Spinner ToUnitSpiner;
	public PhysicalQuantity Quantity;
	public ArrayList<Unit> UnitsArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select);
		if (savedInstanceState == null)
			init();
	}

	private void init() {
		FromUnitSpinner = (Spinner) findViewById(R.id.fromSpinner);
		ToUnitSpiner = (Spinner) findViewById(R.id.toSpinner);
		if (!this.getIntent().getExtras().containsKey(Constants.EXTRA_QUANTITY_ID))
			return;
		int ViewID = this.getIntent().getExtras().getInt(Constants.EXTRA_QUANTITY_ID);
		UnitsArray = new ArrayList<Unit>();
		switch (ViewID) {
		case R.id.temperatureIcon:
			this.Quantity = new PhysicalQuantity(
					getString(R.string.temperature));
			try {
				UnitsArray.add(new Unit("K", 0, 1, Quantity));
				UnitsArray.add(new Unit("°C", -273.15F, 1, Quantity));
				UnitsArray.add(new Unit("°F", -459.67F, 1.8F, Quantity));
				UnitsArray.add(new Unit("°R", 0, 1.8F, Quantity));
				UnitsArray.add(new Unit("°De", 559.5F, -1.5F, Quantity));
			} catch (ZeroGradientException e2) {
				// never zero
			}
			break;
		case R.id.angleIcon:
			this.Quantity = new PhysicalQuantity(getString(R.string.angle));
			try {
				UnitsArray.add(new Unit("rad", 0, (float) (Math.PI / 180),
						Quantity));
				UnitsArray.add(new Unit("°", 0, 1F, Quantity));
				UnitsArray.add(new Unit("'", 0, 60F, Quantity));
				UnitsArray.add(new Unit("\"", 0, 3600F, Quantity));
				UnitsArray.add(new Unit("grad", 0, 10F / 9F, Quantity));
			} catch (ZeroGradientException e1) {
				// never zero
			}
			break;
		case R.id.powerIcon:
			this.Quantity = new PhysicalQuantity(getString(R.string.power));
			try {
				UnitsArray.add(new Unit("W", 0, 1F, Quantity));
				UnitsArray.add(new Unit("PS", 0, 1F/476F, Quantity));
				UnitsArray.add(new Unit("VA", 0, 1F, Quantity));
				UnitsArray.add(new Unit("kVA", 0, 0.001F, Quantity));
			} catch (ZeroGradientException e) {
				// never zero
			}
			break;
		case R.id.sizeIcon:
			this.Quantity = new PhysicalQuantity(getString(R.string.length));
			try {
				UnitsArray.add(new Unit("m", 0, 1, Quantity));
				UnitsArray.add(new Unit("km", 0, 000.1F, Quantity));
				UnitsArray.add(new Unit("dm", 0, 10F, Quantity));
				UnitsArray.add(new Unit("cm", 0, 100F, Quantity));
				UnitsArray.add(new Unit("mm", 0, 1000F, Quantity));

				UnitsArray.add(new Unit("inch", 0, 1F/0.0254F, Quantity));
				UnitsArray.add(new Unit("thou", 0, 1F/0.0000254F, Quantity));
				UnitsArray.add(new Unit("line", 0, 1F/0.0254F / 12F, Quantity));
				UnitsArray.add(new Unit("foot", 0, 1F/0.3048F, Quantity));
				UnitsArray.add(new Unit("yard", 0, 1F/0.9144F, Quantity));
				UnitsArray.add(new Unit("mile", 0, 1F/1609.344F, Quantity));

				UnitsArray.add(new Unit("nautical mile", 0, 1F/1852F, Quantity));
				UnitsArray.add(new Unit("fathom", 0, 1F/1.8288F, Quantity));
			} catch (ZeroGradientException e) {
				// never zero
			}
			break;
		default:
			break;
		}
		((TextView) this.findViewById(R.id.conversionMessage)).setText(String
				.format(getString(R.string.conversion_message), Quantity.Name));
		ArrayAdapter<Unit> Adapter = new ArrayAdapter<Unit>(
				getApplicationContext(), R.layout.spinner_item, UnitsArray);
		FromUnitSpinner.setAdapter(Adapter);
		ToUnitSpiner.setAdapter(Adapter);
	}
	
	public void onClick(View v){
		String input = ((EditText)findViewById(R.id.input)).getText().toString();
		float value;
		try{
			value = Float.parseFloat(input);
		}
		catch(NumberFormatException e){
			Toast.makeText(this, getString(R.string.format_exception), Toast.LENGTH_LONG).show();
			return;
		}
		Unit from = (Unit)FromUnitSpinner.getSelectedItem();
		Unit to = (Unit)ToUnitSpiner.getSelectedItem();
		Measurement m = new Measurement(value, from);
		Measurement res;
		try {
			res = m.toUnit(to);
		} catch (PhysicalQuantityMismatchException e) {
			//should not happen
			e.printStackTrace();
			return;
		}
		Intent i = new Intent();
		i.setClass(this, ResultActivity.class);
		i.putExtra(Constants.EXTRA_ORIGINAL_VALUE, m.Value);
		i.putExtra(Constants.EXTRA_CONVERTED_VALUE, res.Value);
		i.putExtra(Constants.EXTRA_OLD_UNIT_SYMBOL, from.Symbol);
		i.putExtra(Constants.EXTRA_NEW_UNIT_SYMBOL, to.Symbol);
		startActivity(i);
	}

}
