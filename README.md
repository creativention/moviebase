# Moviebase

[![heskadon](https://circleci.com/gh/creativention/moviebase.svg?style=svg)](https://circleci.com/gh/creativention/moviebase)

## Submission Capstone Akhir sebagai syarat lulus kelas "Menjadi Android Developer Expert"

Berikut kriteria submission yang dipenuhi:

    Menerapkan Continuous Integration.
    Syarat:
        Tool yang digunakan : CircleCI
        Melakukan test dan build APK dengan sukses (pass) pada proses terakhirnya. 
[![heskadon](https://circleci.com/gh/creativention/moviebase.svg?style=shield)](https://circleci.com/gh/creativention/moviebase)

    Memiliki performa yang baik
    Syarat:
        Menerapkan Leak Canary  dan tidak ada memory leaks saat dianalisa.

        Tidak ada issue terkait performance saat dilakukan Inspect Code.

    Menerapkan Security
    Syarat:
        Menerapkan obfuscation dengan ProGuard
        Lokasi : build.gradle(app), build.gradle(core), consumer-rules.pro(core), proguard-rules.pro(app)
        
        Menerapkan encryption pada database.
        Lokasi : CoreModule.kt
        
        Menerapkan certificate pinning untuk koneksi ke server.
        Lokasi : CoreModule.kt
      

    Mempertahankan syarat yang ada pada submission sebelumnya.
