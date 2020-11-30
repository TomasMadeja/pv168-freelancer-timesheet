package cz.muni.fi.pv168.freelancertimesheet.backend.exceptions;

public class OpeningPDFReaderException extends Exception {

    public OpeningPDFReaderException() {
    }

    public OpeningPDFReaderException(String message) {
        super(message);
    }

    public OpeningPDFReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpeningPDFReaderException(Throwable cause) {
        super(cause);
    }

    public OpeningPDFReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
