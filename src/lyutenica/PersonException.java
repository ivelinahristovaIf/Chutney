package lyutenica;

public class PersonException extends Exception {

	private static final long serialVersionUID = 2687770635038827809L;

	public PersonException() {
		super();
	}

	public PersonException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public PersonException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PersonException(String arg0) {
		super(arg0);
	}

	public PersonException(Throwable arg0) {
		super(arg0);
	}

}
