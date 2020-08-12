package bg.verbo.footind.controller.error;

public class Error {
	private String errorName;
	private String errorDescription;

	public String getErrorName() {
		return errorName;
	}

	public Error setErrorName(String errorName) {
		this.errorName = errorName;
		return this;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public Error setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
		return this;
	}
}
