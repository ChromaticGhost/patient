-- Database and Table Creation for Patient Biometry Data

CREATE DATABASE IF NOT EXISTS eye_clinic;
USE eye_clinic;

CREATE TABLE IF NOT EXISTS patient_biometry (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    last_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    exam_date DATE NOT NULL,
    id_number VARCHAR(50),
    
    -- Axial Length (OD = Right Eye, OS = Left Eye)
    al_od DECIMAL(5,2),
    al_os DECIMAL(5,2),
    
    -- Keratometry OD (Right Eye)
    ker1_od_k1 DECIMAL(5,2),
    ker1_od_k2 DECIMAL(5,2),
    ker1_od_ax INT,
    ker2_od_k1 DECIMAL(5,2),
    ker2_od_k2 DECIMAL(5,2),
    ker2_od_ax INT,
    ker3_od_k1 DECIMAL(5,2),
    ker3_od_k2 DECIMAL(5,2),
    ker3_od_ax INT,
    
    -- Keratometry OS (Left Eye)
    ker1_os_k1 DECIMAL(5,2),
    ker1_os_k2 DECIMAL(5,2),
    ker1_os_ax INT,
    ker2_os_k1 DECIMAL(5,2),
    ker2_os_k2 DECIMAL(5,2),
    ker2_os_ax INT,
    ker3_os_k1 DECIMAL(5,2),
    ker3_os_k2 DECIMAL(5,2),
    ker3_os_ax INT,
    
    -- Anterior Chamber Depth
    acd_od DECIMAL(5,2),
    acd_os DECIMAL(5,2),
    
    -- White to White (WTW) OD
    wtw1_od DECIMAL(5,2),
    wtw2_od DECIMAL(5,2),
    wtw3_od DECIMAL(5,2),
    
    -- White to White (WTW) OS
    wtw1_os DECIMAL(5,2),
    wtw2_os DECIMAL(5,2),
    wtw3_os DECIMAL(5,2),
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_patient_name (last_name, first_name),
    INDEX idx_exam_date (exam_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
