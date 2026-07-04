# Knowledge Management System (KMS) Putusan Pengadilan Narkotika

Aplikasi **Knowledge Management System (KMS)** berbasis **Java** dengan arsitektur **Model-View-Controller (MVC)** untuk mengelola data Putusan Pengadilan Narkotika.

Project ini dibuat sebagai Tugas Besar Mata Kuliah **Pemrograman Berorientasi Objek (PBO)** Semester Genap 2025/2026.

---

## Deskripsi Project

Aplikasi ini digunakan untuk mengelola dan menganalisis data putusan pengadilan narkotika.

Sistem dibangun menggunakan konsep-konsep Object Oriented Programming (OOP), antara lain:

- Encapsulation
- Inheritance
- Polymorphism
- Method Overloading
- Method Overriding
- Static Field & Method
- Exception Handling
- Interface
- MVC Architecture

Dataset berasal dari dokumen putusan pengadilan dalam format PDF dan dapat diproses menjadi objek Java.

---

## Fitur

### Data Management
- Load dataset PDF secara otomatis
- Tambah data secara manual
- Import data CSV/TXT
- Hapus data
- Menampilkan seluruh data

### Pencarian & Filter
- Cari berdasarkan Nomor Perkara
- Cari berdasarkan Nama Terdakwa
- Filter berdasarkan:
  - Jenis Narkotika
  - Pengadilan
  - Rentang Vonis

### Statistik
- Total Data Putusan
- Rata-rata Vonis
- Rata-rata Denda
- Jenis Narkotika Terbanyak
- Distribusi Peran Terdakwa

### Export
- Export laporan statistik ke file TXT

### Validasi
- Exception Handling
- Validasi seluruh input angka
- Program tidak crash ketika input tidak valid

---

# Teknologi

- Java JDK 11+
- JavaFX
- Apache PDFBox
- MVC Architecture
- Git & GitHub

---

# Struktur Project

```
kms_java
│
├── app
│   └── Main.java
│
├── controller
│   └── KnowledgeController.java
│
├── model
│   ├── DokumenHukum.java
│   ├── Putusan.java
│   ├── KnowledgeRepository.java
│   └── StatistikPutusan.java
│
├── util
│   ├── InputHandler.java
│   └── PdfReader.java
│
└── view
    ├── JavaFXView.java
    ├── ConsoleView.java
    └── IViewLayer.java
```

---

# Cara Compile

Pastikan telah menginstall:

- Java JDK 11 atau lebih baru
- JavaFX SDK
- Apache PDFBox

Compile project menggunakan IDE seperti:

- IntelliJ IDEA
- NetBeans
- Eclipse

Atau melalui terminal:

```bash
javac --module-path "path/to/javafx/lib" --add-modules javafx.controls,javafx.fxml -cp ".;lib/*" kms_java/app/Main.java
```

---

# Cara Menjalankan

Jalankan class:

```
kms_java.app.Main
```

Atau melalui terminal:

```bash
java --module-path "path/to/javafx/lib" --add-modules javafx.controls,javafx.fxml -cp ".;lib/*" kms_java.app.Main
```

---

# Cara Menggunakan

1. Jalankan aplikasi.
2. Load dataset PDF dari folder `pdf-putusan`.
3. Atau tambahkan data secara manual.
4. Gunakan fitur:
   - Search
   - Filter
   - Statistik
   - Export TXT
5. Data akan ditampilkan pada tabel JavaFX.

---

# Implementasi OOP

Project ini menerapkan:

- ✔ Encapsulation
- ✔ Inheritance
- ✔ Interface
- ✔ Method Overloading
- ✔ Method Overriding
- ✔ Static Variable
- ✔ Static Method
- ✔ Comparable
- ✔ Exception Handling
- ✔ ArrayList
- ✔ MVC Pattern

---

# Repository

Repository GitHub:

https://github.com/SECTAAN/pbo-km-narkotika-alieninvasion
---
# Video Demo

Video Demo:

https://youtu.be/J44eJ-FwQZo
---

# Anggota Kelompok

| Nama | NIM |
|------|-----|
| Septian Eka Cahya Tamonob | 202510370110173 |
| Fajar Rizky Ramadhan | 202510370110238 |
| Muhammad Irfan Assiddiq | 202510370110200 |

---

# Lisensi

Project ini dibuat untuk keperluan akademik sebagai Tugas Besar Mata Kuliah Pemrograman Berorientasi Objek (PBO) Universitas Muhammadiyah Malang.
