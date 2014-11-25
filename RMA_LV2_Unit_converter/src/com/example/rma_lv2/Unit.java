package com.example.rma_lv2;

/**
 * Immutable Unit representation
 * @author Hrvoje
 *
 */
public class Unit {
	
	/**
	 * Zero position of standard unit on this unit's scale
	 */
	public final float ZeroPosition;
	/**
	 * Gradient of standard unit on this unit's scale
	 */
	public final float Gradient;
	/**
	 * String representation of this unit.
	 * Never null.
	 */
	public final String Symbol;
	/**
	 * The physical quantity which is measured using this unit.
	 * Can be null.
	 */
	public final PhysicalQuantity Quantity;

	/**
	 * Creates a new Unit object.
	 * @param symbol String representation of unit.
	 * Converted from a 'standard unit' with the formula UnitValue = Gradient*StandarValue + ZeroPosition
	 * @param zeroPosition
	 * @param gradient
	 * @throws ZeroGradientException when gradient equals 0 
	 */
	public Unit(String symbol, float zeroPosition, float gradient, PhysicalQuantity quantity) throws ZeroGradientException {
		if(gradient == 0)
			throw new ZeroGradientException();
		if(symbol == null)
			symbol = "";
		this.Symbol = symbol;
		this.ZeroPosition = zeroPosition;
		this.Gradient = gradient;
		this.Quantity = quantity;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(Gradient);
		result = prime * result
				+ ((Quantity == null) ? 0 : Quantity.hashCode());
		result = prime * result + Float.floatToIntBits(ZeroPosition);
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
		Unit other = (Unit) obj;
		if (Float.floatToIntBits(Gradient) != Float
				.floatToIntBits(other.Gradient))
			return false;
		if (Quantity == null) {
			if (other.Quantity != null)
				return false;
		} else if (!Quantity.equals(other.Quantity))
			return false;
		if (Float.floatToIntBits(ZeroPosition) != Float
				.floatToIntBits(other.ZeroPosition))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Symbol;
	}

}
