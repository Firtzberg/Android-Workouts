package com.example.rma_lv2;

public class ZeroGradientException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4950567016281923015L;

	public ZeroGradientException() {
		super("The value of a gradient in class Unit must not be 0");
		// TODO Auto-generated constructor stub
	}
}
