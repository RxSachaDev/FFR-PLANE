package errors;

/**
 * The ErreurFormatLigne class represents a custom exception thrown when a line in a file does not conform to the expected format.
 */
public class FileFormatError extends NumberFormatException {
    private int line;
    private String fileName;

    /**
     * Constructs a new ErreurFormatLigne with the specified line number and file name.
     *
     * @param lineInFile the line number where the error occurred
     * @param fileName the name of the file where the error occurred
     */
    public FileFormatError (int lineInFile, String fileName) {
        this.line = lineInFile;
        this.fileName = fileName;
    }


    
    /**
     * Returns a string representation of the ErreurFormatLigne exception.
     *
     * @return a string representation of the ErreurFormatLigne exception
     */
    @Override
    public String toString() {
        return String.format("Non conformité de la ligne %d du fichier '%s'!", line, fileName);
    }
}

