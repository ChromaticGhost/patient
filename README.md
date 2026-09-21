# Ophthalmic Patient Biometry Ingestion Pipeline

A Java application that reads delimited ophthalmic biometry examination records, constructs strongly typed `Patient` objects with 33 demographic and clinical measurement fields, and batch-inserts the records into a MySQL database via JDBC.

## Features
- **33 Biometric Fields**: Parses Demographics, Axial Length (AL OD/OS), Keratometry 1–3 (K1, K2, Axis for OD & OS), Anterior Chamber Depth (ACD OD/OS), and White-to-White corneal diameter (WTW 1–3 for OD & OS).
- **Data Sanitization**: Automatically handles European comma decimals (`25,53` -> `25.53`), placeholder values (`---` -> `null`), and `MM/dd/yyyy` dates.
- **Transactional Batch Ingestion**: Uses `PreparedStatement` batching (`addBatch` / `executeBatch`) with commit and rollback handling.
- **Self-Initializing**: Table schema is automatically created if not already present.

## Quick Start

### 1. Configure Database
Copy `db.properties.example` to `db.properties` and add your MySQL credentials:
```bash
cp db.properties.example db.properties
```

### 2. Compile
```bash
./compile.sh
```

### 3. Run
```bash
./run.sh test.txt
```
Or pass the password as a command-line argument:
```bash
./run.sh test.txt <your_mysql_password>
```
