import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents an ophthalmic patient examination record with all 33 biometric fields.
 */
public class Patient {
    // Demographics & Exam Info (5 fields)
    private String lastName;
    private String firstName;
    private LocalDate dateOfBirth;
    private LocalDate examDate;
    private String idNumber;

    // Axial Length (2 fields)
    private BigDecimal alOd;
    private BigDecimal alOs;

    // Keratometry OD - Right Eye (9 fields)
    private BigDecimal ker1OdK1;
    private BigDecimal ker1OdK2;
    private Integer ker1OdAx;

    private BigDecimal ker2OdK1;
    private BigDecimal ker2OdK2;
    private Integer ker2OdAx;

    private BigDecimal ker3OdK1;
    private BigDecimal ker3OdK2;
    private Integer ker3OdAx;

    // Keratometry OS - Left Eye (9 fields)
    private BigDecimal ker1OsK1;
    private BigDecimal ker1OsK2;
    private Integer ker1OsAx;

    private BigDecimal ker2OsK1;
    private BigDecimal ker2OsK2;
    private Integer ker2OsAx;

    private BigDecimal ker3OsK1;
    private BigDecimal ker3OsK2;
    private Integer ker3OsAx;

    // Anterior Chamber Depth (2 fields)
    private BigDecimal acdOd;
    private BigDecimal acdOs;

    // White to White OD (3 fields)
    private BigDecimal wtw1Od;
    private BigDecimal wtw2Od;
    private BigDecimal wtw3Od;

    // White to White OS (3 fields)
    private BigDecimal wtw1Os;
    private BigDecimal wtw2Os;
    private BigDecimal wtw3Os;

    public Patient() {}

    // Getters and Setters
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }

    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

    public BigDecimal getAlOd() { return alOd; }
    public void setAlOd(BigDecimal alOd) { this.alOd = alOd; }

    public BigDecimal getAlOs() { return alOs; }
    public void setAlOs(BigDecimal alOs) { this.alOs = alOs; }

    public BigDecimal getKer1OdK1() { return ker1OdK1; }
    public void setKer1OdK1(BigDecimal ker1OdK1) { this.ker1OdK1 = ker1OdK1; }

    public BigDecimal getKer1OdK2() { return ker1OdK2; }
    public void setKer1OdK2(BigDecimal ker1OdK2) { this.ker1OdK2 = ker1OdK2; }

    public Integer getKer1OdAx() { return ker1OdAx; }
    public void setKer1OdAx(Integer ker1OdAx) { this.ker1OdAx = ker1OdAx; }

    public BigDecimal getKer2OdK1() { return ker2OdK1; }
    public void setKer2OdK1(BigDecimal ker2OdK1) { this.ker2OdK1 = ker2OdK1; }

    public BigDecimal getKer2OdK2() { return ker2OdK2; }
    public void setKer2OdK2(BigDecimal ker2OdK2) { this.ker2OdK2 = ker2OdK2; }

    public Integer getKer2OdAx() { return ker2OdAx; }
    public void setKer2OdAx(Integer ker2OdAx) { this.ker2OdAx = ker2OdAx; }

    public BigDecimal getKer3OdK1() { return ker3OdK1; }
    public void setKer3OdK1(BigDecimal ker3OdK1) { this.ker3OdK1 = ker3OdK1; }

    public BigDecimal getKer3OdK2() { return ker3OdK2; }
    public void setKer3OdK2(BigDecimal ker3OdK2) { this.ker3OdK2 = ker3OdK2; }

    public Integer getKer3OdAx() { return ker3OdAx; }
    public void setKer3OdAx(Integer ker3OdAx) { this.ker3OdAx = ker3OdAx; }

    public BigDecimal getKer1OsK1() { return ker1OsK1; }
    public void setKer1OsK1(BigDecimal ker1OsK1) { this.ker1OsK1 = ker1OsK1; }

    public BigDecimal getKer1OsK2() { return ker1OsK2; }
    public void setKer1OsK2(BigDecimal ker1OsK2) { this.ker1OsK2 = ker1OsK2; }

    public Integer getKer1OsAx() { return ker1OsAx; }
    public void setKer1OsAx(Integer ker1OsAx) { this.ker1OsAx = ker1OsAx; }

    public BigDecimal getKer2OsK1() { return ker2OsK1; }
    public void setKer2OsK1(BigDecimal ker2OsK1) { this.ker2OsK1 = ker2OsK1; }

    public BigDecimal getKer2OsK2() { return ker2OsK2; }
    public void setKer2OsK2(BigDecimal ker2OsK2) { this.ker2OsK2 = ker2OsK2; }

    public Integer getKer2OsAx() { return ker2OsAx; }
    public void setKer2OsAx(Integer ker2OsAx) { this.ker2OsAx = ker2OsAx; }

    public BigDecimal getKer3OsK1() { return ker3OsK1; }
    public void setKer3OsK1(BigDecimal ker3OsK1) { this.ker3OsK1 = ker3OsK1; }

    public BigDecimal getKer3OsK2() { return ker3OsK2; }
    public void setKer3OsK2(BigDecimal ker3OsK2) { this.ker3OsK2 = ker3OsK2; }

    public Integer getKer3OsAx() { return ker3OsAx; }
    public void setKer3OsAx(Integer ker3OsAx) { this.ker3OsAx = ker3OsAx; }

    public BigDecimal getAcdOd() { return acdOd; }
    public void setAcdOd(BigDecimal acdOd) { this.acdOd = acdOd; }

    public BigDecimal getAcdOs() { return acdOs; }
    public void setAcdOs(BigDecimal acdOs) { this.acdOs = acdOs; }

    public BigDecimal getWtw1Od() { return wtw1Od; }
    public void setWtw1Od(BigDecimal wtw1Od) { this.wtw1Od = wtw1Od; }

    public BigDecimal getWtw2Od() { return wtw2Od; }
    public void setWtw2Od(BigDecimal wtw2Od) { this.wtw2Od = wtw2Od; }

    public BigDecimal getWtw3Od() { return wtw3Od; }
    public void setWtw3Od(BigDecimal wtw3Od) { this.wtw3Od = wtw3Od; }

    public BigDecimal getWtw1Os() { return wtw1Os; }
    public void setWtw1Os(BigDecimal wtw1Os) { this.wtw1Os = wtw1Os; }

    public BigDecimal getWtw2Os() { return wtw2Os; }
    public void setWtw2Os(BigDecimal wtw2Os) { this.wtw2Os = wtw2Os; }

    public BigDecimal getWtw3Os() { return wtw3Os; }
    public void setWtw3Os(BigDecimal wtw3Os) { this.wtw3Os = wtw3Os; }

    @Override
    public String toString() {
        return String.format(
            "Patient[%s, %s | DOB: %s | Exam: %s | ID: %s | AL(OD): %s | AL(OS): %s | KER1(OD): K1=%s, K2=%s, Ax=%s]",
            lastName, firstName, dateOfBirth, examDate,
            idNumber != null ? idNumber : "N/A",
            alOd != null ? alOd : "---",
            alOs != null ? alOs : "---",
            ker1OdK1, ker1OdK2, ker1OdAx
        );
    }
}
