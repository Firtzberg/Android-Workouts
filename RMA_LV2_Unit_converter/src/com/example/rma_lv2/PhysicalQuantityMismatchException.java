package com.example.rma_lv2;

/**
 * Thrown only when trying to convert between Units of different physical quantities
 * @author Hrvoje
 *
 */
public class PhysicalQuantityMismatchException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1067166425639064667L;

	public PhysicalQuantityMismatchException(PhysicalQuantity fromQuantity,
			PhysicalQuantity toQuantity) {
		super("Cannot convert from "
				+ (fromQuantity == null ? "none" : fromQuantity.Name) + " to "
				+ (toQuantity == null ? "none" : toQuantity.Name) + ".");
	}
}
