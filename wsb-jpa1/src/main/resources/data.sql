-- Wstawianie danych do tabeli doctor
INSERT INTO doctor (doctor_number, email, first_name, last_name, specialization, telephone_number) VALUES
('LEK001', 'lekarz1@przyklad.com', 'Jan', 'Kowalski', 'KARDIOLOGIA', '123-456-789'),
('LEK002', 'lekarz2@przyklad.com', 'Anna', 'Nowak', 'NEUROLOGIA', '987-654-321'),
('LEK003', 'lekarz3@przyklad.com', 'Piotr', 'Wiśniewski', 'ORTOPEDIA', '555-123-456'),
('LEK004', 'lekarz4@przyklad.com', 'Ewa', 'Wójcik', 'DERMATOLOGIA', '555-654-321');

-- Wstawianie danych do tabeli patient
INSERT INTO patient (date_of_birth, email, first_name, last_name, patient_number, telephone_number, date_of_registration) VALUES
('1985-05-15', 'pacjent1@przyklad.com', 'Maria', 'Kwiatkowska', 'PAC001', '555-111-222', '2024-12-01'),
('1992-08-20', 'pacjent2@przyklad.com', 'Krzysztof', 'Kaminski', 'PAC002', '555-333-444', '2024-12-02'),
('1978-12-30', 'pacjent3@przyklad.com', 'Barbara', 'Lewandowska', 'PAC003', '555-555-666', '2024-12-03'),
('2000-03-10', 'pacjent4@przyklad.com', 'Tomasz', 'Zieliński', 'PAC004', '555-777-888', '2024-12-04');

-- Wstawianie danych do tabeli address
INSERT INTO address (address_line1, address_line2, city, postal_code, doctor_id, patient_id) VALUES
('ul. Główna 1', 'm. 1', 'Warszawa', '00-001', 1, NULL),
('ul. Słoneczna 2', 'm. 2', 'Warszawa', '00-002', NULL, 1),
('ul. Zielona 3', 'm. 3', 'Kraków', '30-001', 2, NULL),
('ul. Polna 4', 'm. 4', 'Kraków', '30-002', NULL, 2),
('ul. Kwiatowa 5', 'm. 5', 'Gdańsk', '80-001', 3, NULL),
('ul. Leśna 6', 'm. 6', 'Gdańsk', '80-002', NULL, 3),
('ul. Krótka 7', 'm. 7', 'Wrocław', '50-001', 4, NULL),
('ul. Długa 8', 'm. 8', 'Wrocław', '50-002', NULL, 4);

-- Wstawianie danych do tabeli visit
INSERT INTO visit (description, time, doctor_id, patient_id) VALUES
('Pierwsza konsultacja', '2024-12-01 10:00:00', 1, 1),
('Kontrola po leczeniu', '2024-12-02 11:00:00', 2, 2),
('Konsultacja ortopedyczna', '2024-12-03 12:00:00', 3, 3),
('Badanie dermatologiczne', '2024-12-04 13:00:00', 4, 4),
('Kontrola kardiologiczna', '2024-12-05 14:00:00', 1, 2),
('Konsultacja neurologiczna', '2024-12-06 15:00:00', 2, 3),
('Kontrola dermatologiczna', '2024-12-07 16:00:00', 4, 1),
('Badanie ortopedyczne', '2024-12-08 17:00:00', 3, 4);

-- Wstawianie danych do tabeli medical_treatment
INSERT INTO medical_treatment (description, type, visit_id) VALUES
('Badanie USG jamy brzusznej', 'USG', 1),
('Elektrokardiogram (EKG)', 'EKG', 2),
('RTG klatki piersiowej', 'RTG', 3),
('MRI mózgu', 'MRI', 4),
('Badanie krwi na cholesterol', 'BADANIE_KRWI', 5),
('Badanie USG serca', 'USG', 6),
('Szczepienie przeciw grypie', 'SZCZEPIENIE', 7),
('EKG podczas testu wysiłkowego', 'EKG', 8),