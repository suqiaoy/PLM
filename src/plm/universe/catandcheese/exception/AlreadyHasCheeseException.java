package plm.universe.catandcheese.exception;

import plm.core.PLMException;

public class AlreadyHasCheeseException extends PLMException {

	private static final long serialVersionUID = -8684053474919074710L;

	public AlreadyHasCheeseException(String msg) {
		super(msg);
	}
}
