(docker compose up -d, ./gradlew bootRun)
Endpoint-lər

POST /api/authors Müəllif yarat (eyni ad+soyad varsa – xəta)
GET /api/authors/{id} Müəllif
GET /api/authors?country=Azerbaijan Hamısı; country verilsə – filter
GET /api/authors/without-books Kitabı olmayan müəlliflər
GET /api/authors/{id}/books Müəllifin kitabları
PUT / DELETE /api/authors/{id} Yenilə / sil (kitablar qalmalıdır)
POST /api/books Kitab yarat (title unikal yoxlanılır)
GET /api/books/{id} Müəlliflər ilə birlikdə
GET /api/books?title=java Hamısı; title verilsə – axtarış
(ContainingIgnoreCase)

GET /api/books/cheaper-than?price=20 Qiyməti verilən məbləğə bərabər və ya aşağı

olanlar (qiymətə görə artan sıra)

PUT / DELETE /api/books/{id} Yenilə / sil

POST /api/books/{bookId}/authors/{authorId} Kitaba müəllif bağla (AuthorBook yarat)
DELETE /api/books/{bookId}/authors/{authorId} Əlaqəni sil


POST /api/students Tələbə yarat

GET /api/students/{id} Kurslar ilə birlikdə (left join fetch)
GET /api/students Hamısı
GET /api/students/without-courses Heç bir kursa yazılmayanlar (s.courses is

empty)

PUT / DELETE /api/students/{id} Yenilə / sil
POST /api/students/{studentId}/courses/{courseId} Kursa yaz (enroll)
DELETE /api/students/{studentId}/courses/{courseId} Kursdan çıxar (unenroll)
POST /api/courses Kurs yarat (title unikal)
GET /api/courses, /api/courses/{id} Hamısı / biri
GET /api/courses/student/{studentId} Tələbənin kursları
GET /api/courses/{id}/students Kursun tələbələri
GET /api/courses/{id}/students/count Kursdakı tələbə sayı
PUT / DELETE /api/courses/{id} Yenilə / sil




POST /api/departments Department yarat (ad unikal)
GET /api/departments/{id} İşçilər ilə birlikdə (JOIN FETCH)
GET /api/departments Hamısı (işçilərsiz)

GET /api/departments/without-employees İşçisi olmayan departamentlər
PUT /api/departments/{id} Yenilə (ad başqasında varsa – xəta)
DELETE /api/departments/{id} Sil – işçisi varsa silmək OLMAZ
POST /api/employees İşçi yarat (departmentId request-də gəlir)
GET /api/employees/{id} İşçi
GET /api/employees/department/{departmentId} Departamentin işçiləri
GET /api/employees/salary-range?min=500&amp;max=2000 Maaş aralığına görə

GET /api/employees/department/{id}/average- salary Orta maaş (JPQL avg)
PUT /api/employees/{id} Yenilə (departament dəyişə bilər)
DELETE /api/employees/{id} İşçini sil (departament qalmalıdır)




POST /api/users User yarat. Email artıq varsa – xəta
GET /api/users/{id} User-i passport ilə birlikdə qaytar (JOIN FETCH)
GET /api/users/by-email?email=... Email ilə axtarış
GET /api/users?gender=MALE Hamısı; gender verilsə – filter
GET /api/users/without-passport Passportu olmayan user-lər
PUT /api/users/{id} Yenilə. Email başqa user-də varsa – xəta (existsByEmailAndIdNot)
DELETE /api/users/{id} Sil (cascade ilə passport da silinir)
POST /api/users/{id}/passport Passport əlavə et
GET /api/users/{id}/passport User-in passportu
DELETE /api/users/{id}/passport Passportu sil – user.setPassport(null) (orphanRemoval işləməlidir)






