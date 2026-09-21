import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  Ophthalmic Patient Ingestion & MySQL Pipeline  ");
        System.out.println("=================================================\n");

        // 1. Determine input file path from command line arguments
        if (args.length == 0 || args[0].trim().isEmpty()) {
            System.err.println("Error: No input file specified.");
            System.err.println("Usage: ./run.sh <path-to-patient-file> [mysql-password]");
            System.exit(1);
            return;
        }

        Path dataFile = Paths.get(args[0].trim());
        if (!Files.exists(dataFile) || !Files.isRegularFile(dataFile)) {
            System.err.println("Error: Input file does not exist or is not a file: " + dataFile.toAbsolutePath());
            System.exit(1);
            return;
        }

        System.out.println("[1/3] Reading and parsing data file: " + dataFile.toAbsolutePath());
        List<Patient> patients;
        try {
            patients = PatientFileReader.readPatients(dataFile);
            System.out.printf("Successfully parsed %d patient record(s):%n%n", patients.size());
            for (int i = 0; i < patients.size(); i++) {
                System.out.printf("  #%02d: %s%n", i + 1, patients.get(i));
            }
            System.out.println();
        } catch (IOException e) {
            System.err.println("Failed to read file: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
            return;
        }

        // 2. Load database configuration strictly from db.properties
        System.out.println("[2/3] Loading database configuration from db.properties...");
        Path configPath = Paths.get("db.properties");
        if (!Files.exists(configPath)) {
            System.err.println("Error: Configuration file 'db.properties' not found.");
            System.exit(1);
            return;
        }

        Properties dbProps = new Properties();
        try (InputStream in = new FileInputStream(configPath.toFile())) {
            dbProps.load(in);
        } catch (IOException e) {
            System.err.println("Error: Failed to read 'db.properties': " + e.getMessage());
            System.exit(1);
            return;
        }

        String dbUrl = dbProps.getProperty("db.url");
        if (dbUrl == null || dbUrl.trim().isEmpty()) {
            System.err.println("Error: Missing database URL. Please configure 'db.url' in db.properties.");
            System.exit(1);
            return;
        }

        String dbUser = dbProps.getProperty("db.user");
        if (dbUser == null || dbUser.trim().isEmpty()) {
            System.err.println("Error: Missing database user. Please configure 'db.user' in db.properties.");
            System.exit(1);
            return;
        }

        String dbPass = dbProps.getProperty("db.password", "");
        // Allow overriding password via command line: ./run.sh <file> <password>
        if (args.length > 1) {
            dbPass = args[1];
        }

        System.out.printf("Connecting to database: %s (user: %s)%n", dbUrl, dbUser);

        // 3. Connect and perform batch insertion
        System.out.println("[3/3] Inserting records into MySQL using JDBC driver...");
        PatientDao dao = new PatientDao();
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
            System.out.println("Connection established successfully.");

            dao.initTable(conn);
            System.out.println("Table 'patient_biometry' verified/created.");

            int inserted = dao.insertPatients(conn, patients);
            System.out.printf("Successfully inserted %d record(s) into 'patient_biometry' table.%n", inserted);

            long totalCount = dao.countPatients(conn);
            System.out.printf("Total records currently in database: %d%n%n", totalCount);
            System.out.println("=================================================");
            System.out.println("  Import completed successfully!                 ");
            System.out.println("=================================================");
        } catch (SQLException e) {
            System.err.println("\nDatabase error: " + e.getMessage());
            if (e.getErrorCode() == 1045) {
                System.err.println("Authentication failed. Please update db.password in 'db.properties' or pass the password as an argument:");
                System.err.println("  ./run.sh " + args[0] + " <your_mysql_password>");
            }
            e.printStackTrace();
            System.exit(1);
        }
    }
}
