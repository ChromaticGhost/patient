import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads ophthalmic biometry text files and constructs Patient objects.
 */
public class PatientFileReader {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Reads a semicolon-delimited patient examination file.
     *
     * @param filePath Path to the file (e.g. test.txt)
     * @return List of parsed Patient objects
     * @throws IOException If file cannot be read
     */
    public static List<Patient> readPatients(Path filePath) throws IOException {
        List<Patient> patients = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                return patients;
            }

            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                try {
                    Patient patient = parseLine(line);
                    if (patient != null) {
                        patients.add(patient);
                    }
                } catch (Exception e) {
                    System.err.printf("Warning: Skipping malformed line %d: %s (Reason: %s)%n",
                            lineNumber, line, e.getMessage());
                }
            }
        }

        return patients;
    }

    private static Patient parseLine(String line) {
        // Use limit -1 to preserve trailing empty tokens
        String[] tokens = line.split(";", -1);

        String lastName = cleanString(get(tokens, 0));
        String firstName = cleanString(get(tokens, 1));
        if (lastName == null || firstName == null) {
            return null; // Ignore lines missing basic patient identification
        }

        Patient p = new Patient();
        p.setLastName(lastName);
        p.setFirstName(firstName);
        p.setDateOfBirth(parseDate(get(tokens, 2)));
        p.setExamDate(parseDate(get(tokens, 3)));
        p.setIdNumber(cleanString(get(tokens, 4)));

        // Axial Length
        p.setAlOd(parseBigDecimal(get(tokens, 5)));
        p.setAlOs(parseBigDecimal(get(tokens, 6)));

        // Keratometry OD
        p.setKer1OdK1(parseBigDecimal(get(tokens, 7)));
        p.setKer1OdK2(parseBigDecimal(get(tokens, 8)));
        p.setKer1OdAx(parseInteger(get(tokens, 9)));

        p.setKer2OdK1(parseBigDecimal(get(tokens, 10)));
        p.setKer2OdK2(parseBigDecimal(get(tokens, 11)));
        p.setKer2OdAx(parseInteger(get(tokens, 12)));

        p.setKer3OdK1(parseBigDecimal(get(tokens, 13)));
        p.setKer3OdK2(parseBigDecimal(get(tokens, 14)));
        p.setKer3OdAx(parseInteger(get(tokens, 15)));

        // Keratometry OS
        p.setKer1OsK1(parseBigDecimal(get(tokens, 16)));
        p.setKer1OsK2(parseBigDecimal(get(tokens, 17)));
        p.setKer1OsAx(parseInteger(get(tokens, 18)));

        p.setKer2OsK1(parseBigDecimal(get(tokens, 19)));
        p.setKer2OsK2(parseBigDecimal(get(tokens, 20)));
        p.setKer2OsAx(parseInteger(get(tokens, 21)));

        p.setKer3OsK1(parseBigDecimal(get(tokens, 22)));
        p.setKer3OsK2(parseBigDecimal(get(tokens, 23)));
        p.setKer3OsAx(parseInteger(get(tokens, 24)));

        // Anterior Chamber Depth
        p.setAcdOd(parseBigDecimal(get(tokens, 25)));
        p.setAcdOs(parseBigDecimal(get(tokens, 26)));

        // White to White OD
        p.setWtw1Od(parseBigDecimal(get(tokens, 27)));
        p.setWtw2Od(parseBigDecimal(get(tokens, 28)));
        p.setWtw3Od(parseBigDecimal(get(tokens, 29)));

        // White to White OS
        p.setWtw1Os(parseBigDecimal(get(tokens, 30)));
        p.setWtw2Os(parseBigDecimal(get(tokens, 31)));
        p.setWtw3Os(parseBigDecimal(get(tokens, 32)));

        return p;
    }

    private static String get(String[] tokens, int index) {
        if (index < 0 || index >= tokens.length) {
            return null;
        }
        return tokens[index];
    }

    private static String cleanString(String s) {
        if (s == null) return null;
        String trimmed = s.trim();
        if (trimmed.isEmpty() || trimmed.equals("---")) {
            return null;
        }
        return trimmed;
    }

    private static LocalDate parseDate(String s) {
        String clean = cleanString(s);
        if (clean == null) return null;
        return LocalDate.parse(clean, DATE_FORMATTER);
    }

    private static BigDecimal parseBigDecimal(String s) {
        String clean = cleanString(s);
        if (clean == null) return null;
        // Convert European decimal commas (e.g. 25,53 -> 25.53)
        clean = clean.replace(',', '.');
        return new BigDecimal(clean);
    }

    private static Integer parseInteger(String s) {
        String clean = cleanString(s);
        if (clean == null) return null;
        return Integer.parseInt(clean);
    }
}
