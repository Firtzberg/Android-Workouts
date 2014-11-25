package com.example.rma_lv2;

/**
 * Immutable representation of a physical quantity
 * @author Hrvoje
 *
 */
public class PhysicalQuantity {
	
	/**
	 * String representation of this quantity.
	 * Never null.
	 */
	public final String Name;

	/**
	 * Creates a new instance of a physical quantity
	 * @param name Name of the quantity
	 */
	public PhysicalQuantity(String name) {
		if(name == null)
			name = "";
		this.Name = name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
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
		PhysicalQuantity other = (PhysicalQuantity) obj;
		if (!Name.equals(other.Name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Name;
	}
}
