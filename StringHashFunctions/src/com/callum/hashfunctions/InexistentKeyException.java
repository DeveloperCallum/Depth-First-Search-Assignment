package com.callum.hashfunctions;

public class InexistentKeyException extends RuntimeException {
	public InexistentKeyException(String message) {
		super (message);
	}
}