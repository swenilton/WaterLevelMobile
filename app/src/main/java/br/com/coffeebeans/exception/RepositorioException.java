package br.com.coffeebeans.exception;


public class RepositorioException extends Exception {

    private Exception exception;

    public RepositorioException(Exception exception) {
        super("Exceçãoo encapsulada");
        this.exception = exception;
    }

    public RepositorioException(String message) {
    	super("Exceçãoo encapsulada");
	}

	public String getMessage() {
        return exception.getMessage();
    }

    public void printStackTrace() {
        exception.printStackTrace();
    }

}
