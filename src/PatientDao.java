import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.List;

/**
 * Data Access Object (DAO) for persisting Patient biometric records into MySQL.
 */
public class PatientDao {

    private static final String CREATE_TABLE_SQL = """
        CREATE TABLE IF NOT EXISTS patient_biometry (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            last_name VARCHAR(100) NOT NULL,
            first_name VARCHAR(100) NOT NULL,
            date_of_birth DATE NOT NULL,
            exam_date DATE NOT NULL,
            id_number VARCHAR(50),
            
            al_od DECIMAL(5,2),
            al_os DECIMAL(5,2),
            
            ker1_od_k1 DECIMAL(5,2),
            ker1_od_k2 DECIMAL(5,2),
            ker1_od_ax INT,
            ker2_od_k1 DECIMAL(5,2),
            ker2_od_k2 DECIMAL(5,2),
            ker2_od_ax INT,
            ker3_od_k1 DECIMAL(5,2),
            ker3_od_k2 DECIMAL(5,2),
            ker3_od_ax INT,
            
            ker1_os_k1 DECIMAL(5,2),
            ker1_os_k2 DECIMAL(5,2),
            ker1_os_ax INT,
            ker2_os_k1 DECIMAL(5,2),
            ker2_os_k2 DECIMAL(5,2),
            ker2_os_ax INT,
            ker3_os_k1 DECIMAL(5,2),
            ker3_os_k2 DECIMAL(5,2),
            ker3_os_ax INT,
            
            acd_od DECIMAL(5,2),
            acd_os DECIMAL(5,2),
            
            wtw1_od DECIMAL(5,2),
            wtw2_od DECIMAL(5,2),
            wtw3_od DECIMAL(5,2),
            
            wtw1_os DECIMAL(5,2),
            wtw2_os DECIMAL(5,2),
            wtw3_os DECIMAL(5,2),
            
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            INDEX idx_patient_name (last_name, first_name),
            INDEX idx_exam_date (exam_date)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
        """;

    private static final String INSERT_SQL = """
        INSERT INTO patient_biometry (
            last_name, first_name, date_of_birth, exam_date, id_number,
            al_od, al_os,
            ker1_od_k1, ker1_od_k2, ker1_od_ax,
            ker2_od_k1, ker2_od_k2, ker2_od_ax,
            ker3_od_k1, ker3_od_k2, ker3_od_ax,
            ker1_os_k1, ker1_os_k2, ker1_os_ax,
            ker2_os_k1, ker2_os_k2, ker2_os_ax,
            ker3_os_k1, ker3_os_k2, ker3_os_ax,
            acd_od, acd_os,
            wtw1_od, wtw2_od, wtw3_od,
            wtw1_os, wtw2_os, wtw3_os
        ) VALUES (
            ?, ?, ?, ?, ?,
            ?, ?,
            ?, ?, ?,
            ?, ?, ?,
            ?, ?, ?,
            ?, ?, ?,
            ?, ?, ?,
            ?, ?, ?,
            ?, ?,
            ?, ?, ?,
            ?, ?, ?
        );
        """;

    /**
     * Ensures that the patient_biometry table exists before inserting.
     */
    public void initTable(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE_SQL);
        }
    }

    /**
     * Batch-inserts a list of patients using a single transaction.
     *
     * @param conn Database connection
     * @param patients List of Patient records
     * @return Number of inserted records
     * @throws SQLException If database insertion fails
     */
    public int insertPatients(Connection conn, List<Patient> patients) throws SQLException {
        if (patients == null || patients.isEmpty()) {
            return 0;
        }

        boolean originalAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);

        try (PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL)) {
            for (Patient p : patients) {
                bindPatient(pstmt, p);
                pstmt.addBatch();
            }

            int[] batchResults = pstmt.executeBatch();
            conn.commit();
            return batchResults.length;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(originalAutoCommit);
        }
    }

    /**
     * Queries and prints the total number of records in patient_biometry.
     */
    public long countPatients(Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) FROM patient_biometry";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        }
    }

    private void bindPatient(PreparedStatement ps, Patient p) throws SQLException {
        // Demographics
        setNullableString(ps, 1, p.getLastName());
        setNullableString(ps, 2, p.getFirstName());
        setNullableDate(ps, 3, p.getDateOfBirth());
        setNullableDate(ps, 4, p.getExamDate());
        setNullableString(ps, 5, p.getIdNumber());

        // Axial Length
        setNullableBigDecimal(ps, 6, p.getAlOd());
        setNullableBigDecimal(ps, 7, p.getAlOs());

        // Keratometry OD
        setNullableBigDecimal(ps, 8, p.getKer1OdK1());
        setNullableBigDecimal(ps, 9, p.getKer1OdK2());
        setNullableInteger(ps, 10, p.getKer1OdAx());

        setNullableBigDecimal(ps, 11, p.getKer2OdK1());
        setNullableBigDecimal(ps, 12, p.getKer2OdK2());
        setNullableInteger(ps, 13, p.getKer2OdAx());

        setNullableBigDecimal(ps, 14, p.getKer3OdK1());
        setNullableBigDecimal(ps, 15, p.getKer3OdK2());
        setNullableInteger(ps, 16, p.getKer3OdAx());

        // Keratometry OS
        setNullableBigDecimal(ps, 17, p.getKer1OsK1());
        setNullableBigDecimal(ps, 18, p.getKer1OsK2());
        setNullableInteger(ps, 19, p.getKer1OsAx());

        setNullableBigDecimal(ps, 20, p.getKer2OsK1());
        setNullableBigDecimal(ps, 21, p.getKer2OsK2());
        setNullableInteger(ps, 22, p.getKer2OsAx());

        setNullableBigDecimal(ps, 23, p.getKer3OsK1());
        setNullableBigDecimal(ps, 24, p.getKer3OsK2());
        setNullableInteger(ps, 25, p.getKer3OsAx());

        // Anterior Chamber Depth
        setNullableBigDecimal(ps, 26, p.getAcdOd());
        setNullableBigDecimal(ps, 27, p.getAcdOs());

        // White to White OD
        setNullableBigDecimal(ps, 28, p.getWtw1Od());
        setNullableBigDecimal(ps, 29, p.getWtw2Od());
        setNullableBigDecimal(ps, 30, p.getWtw3Od());

        // White to White OS
        setNullableBigDecimal(ps, 31, p.getWtw1Os());
        setNullableBigDecimal(ps, 32, p.getWtw2Os());
        setNullableBigDecimal(ps, 33, p.getWtw3Os());
    }

    private void setNullableString(PreparedStatement ps, int index, String val) throws SQLException {
        if (val != null) {
            ps.setString(index, val);
        } else {
            ps.setNull(index, Types.VARCHAR);
        }
    }

    private void setNullableDate(PreparedStatement ps, int index, LocalDate val) throws SQLException {
        if (val != null) {
            ps.setDate(index, Date.valueOf(val));
        } else {
            ps.setNull(index, Types.DATE);
        }
    }

    private void setNullableBigDecimal(PreparedStatement ps, int index, BigDecimal val) throws SQLException {
        if (val != null) {
            ps.setBigDecimal(index, val);
        } else {
            ps.setNull(index, Types.DECIMAL);
        }
    }

    private void setNullableInteger(PreparedStatement ps, int index, Integer val) throws SQLException {
        if (val != null) {
            ps.setInt(index, val);
        } else {
            ps.setNull(index, Types.INTEGER);
        }
    }
}
