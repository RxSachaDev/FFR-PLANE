package sae.models.errors;

/**
 * The FileFormatError class represents a custom exception thrown when a line in a file does not conform to the expected format.
 */
public class FileFormatError extends NumberFormatException {
    private int line;
    private String file;

    /**
     * Constructs a new FileFormatError with the specified line number and file name.
     *
     * @param lineInFile the line number where the error occurred
     * @param fileName the name of the file where the error occurred
     */
    public FileFormatError(int lineInFile, String fileName) {
        this.line = lineInFile;
        this.file = fileName;
    }


    /**
     * Returns a string representation of the FileFormatError exception.
     *
     * @return a string representation of the FileFormatError exception
     */
    @Override
    public String toString() {
        return String.format("Non-conformity in line %d of file '%s'!", line, file);
    }
}
