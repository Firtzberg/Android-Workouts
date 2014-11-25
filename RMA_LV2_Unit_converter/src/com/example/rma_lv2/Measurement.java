package com.example.rma_lv2;

/**
 * Value and Unit combination
 * 
 * @author Hrvoje
 * 
 */
public class Measurement {
	public float Value;
	public final Unit Unit;

	/**
	 * 
	 * @param value Value of measurement in the specified unit
	 * @param unit Unit of the measurement
	 * @throws NullPointerException when Unit equals null
	 */
	public Measurement(float value, Unit unit) {
		if(unit == null)
			throw new NullPointerException("unit must not be null.");
		this.Value = value;
		this.Unit = unit;
	}

	/**
	 * Converts the measurement to another unit.
	 * @param unit Unit of returned measurement
	 * @return Measurement in specified unit representing the same physical quantity and amount
	 * @throws PhysicalQuantityMismatchException when trying to convert to unit of different physical quantity
	 */
	public Measurement toUnit(Unit unit)
			throws PhysicalQuantityMismatchException {
		if(Unit == null)
			throw new NullPointerException("unit must not be null.");
		if (this.Unit.Quantity == null) {
			if (unit.Quantity != null)
				throw new PhysicalQuantityMismatchException(this.Unit.Quantity,
						unit.Quantity);
		} else {
			if (!this.Unit.Quantity.equals(unit.Quantity))
				throw new PhysicalQuantityMismatchException(this.Unit.Quantity,
						unit.Quantity);
		}
		return Measurement.fromStandardUnitValue(this.toStandardUnitValue(),
				unit);
	}

	private final float toStandardUnitValue() {
		return (this.Value - this.Unit.ZeroPosition) / this.Unit.Gradient;

	}

	private final static Measurement fromStandardUnitValue(
			float standardUnitValue, Unit toUnit) {
		if(toUnit == null)
			throw new NullPointerException("unit must not be null.");
		return new Measurement(standardUnitValue * toUnit.Gradient
				+ toUnit.ZeroPosition, toUnit);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Unit == null) ? 0 : Unit.hashCode());
		result = prime * result + Float.floatToIntBits(Value);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Measurement other = (Measurement) obj;
		if (Unit == null) {
			if (other.Unit != null)
				return false;
		} else if (!Unit.equals(other.Unit))
			return false;
		if (Value != other.Value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Value + " " + Unit;
	}

}
