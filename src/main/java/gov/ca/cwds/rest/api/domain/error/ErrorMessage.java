package gov.ca.cwds.rest.api.domain.error;

public class ErrorMessage {
    private final ErrorType type;
    private final String message;
    private final String source;


    public ErrorType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getSource() {
        return source;
    }

    public ErrorMessage(ErrorType type, String message, String source){
        this.type = type;
        this.message = message;
        this.source = source;
    }

    public static enum ErrorType {
        VALIDATION("Validation Error"),
        BUSINESS("Buiness Rules"),
        DATA_ACCESS("Data Access");

        private final String label;

        private ErrorType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}
