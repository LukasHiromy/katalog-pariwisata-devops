# ==========================================
# Tahap 1: Builder
# Menggunakan JDK untuk mengkompilasi kode sumber
# ==========================================
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Menyalin seluruh berkas proyek ke dalam kontainer
COPY . .

# Mengeksekusi kompilasi Maven
# Flag -DskipTests ditambahkan agar proses build tidak gagal saat
# Spring Boot mencoba memuat konteks pengujian tanpa koneksi DB/Redis lokal
RUN ./mvnw clean package -DskipTests

# ==========================================
# Tahap 2: Runner
# Menggunakan JRE Alpine yang sangat ringan untuk menjalankan aplikasi
# ==========================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Menyalin berkas JAR hasil kompilasi dari tahap builder
COPY --from=builder /app/target/*.jar app.jar

# Mengekspos porta 8080 sesuai konfigurasi server
EXPOSE 8080

# Mengeksekusi aplikasi dengan dukungan injeksi variabel lingkungan (JAVA_OPTS)
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]