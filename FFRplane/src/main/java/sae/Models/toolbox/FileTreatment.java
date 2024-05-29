package sae.Models.toolbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import sae.Models.airports.*;
import sae.Models.flights.*;
import sae.Models.errors.*;

/**
 * The FileTreatment class provides methods for reading and processing files containing flight and airport data.
 */
public class FileTreatment {
    /**
     * Fills the list of flights with data from a file.
     *
     * @param filePath the path to the file
     * @param flightsCatalog the catalog of flights
     * @param airportsCatalog the catalog of airports
     * @return true if the operation is successful, false otherwise
     * @throws FileNotFoundException if the file is not found
     */
    public static boolean fillFlightList(String filePath, FlightCatalog flightsCatalog, AirportCatalog airportsCatalog) throws FileNotFoundException {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(";");
                flightsCatalog.addFlight(new Flight(values[0], airportsCatalog.getAirport(values[1]), airportsCatalog.getAirport(values[2]),
                        Integer.parseInt(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5])));
            }
            scanner.close();
        } catch (FileNotFoundException error) {
            throw new FileNotFoundException();
        }
        return true;
    }

    /**
     * Fills the list of airports with data from a file.
     *
     * @param filePath the path to the file
     * @param airportsCatalog the catalog of airports
     * @return true if the operation is successful, false otherwise
     * @throws FileNotFoundException if the file is not found
     * @throws FileFormatError if there is a format error in the file
     */
    public static boolean fillAirportList(String filePath, AirportCatalog airportsCatalog) throws FileNotFoundException, FileFormatError {
        int lineCount = 1;
        try {
            FileReader file = new FileReader(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(";");
                airportsCatalog.addAirport(new Airport(values[0], values[1],
                        Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]), values[5].charAt(0),
                        Integer.parseInt(values[6]), Integer.parseInt(values[7]), Integer.parseInt(values[8]), values[9].charAt(0)));
                lineCount++;
            }
            scanner.close();
        } catch (FileNotFoundException error) {
            throw new FileNotFoundException();
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException error) {
            throw new FileFormatError(lineCount, filePath);
        }
        return true;
    }
}

