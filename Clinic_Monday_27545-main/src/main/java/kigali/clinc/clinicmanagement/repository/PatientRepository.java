package kigali.clinc.clinicmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.clinc.clinicmanagement.domain.Patient;

@Repository
public interface PatientRepository
        extends JpaRepository<Patient, Long> {

    // Quiz 1 - A1
    List<Patient> findByLastNameIgnoreCaseOrderByFirstNameAsc(
            String lastName);

    // Quiz 1 - B4
    @Query("SELECT DISTINCT a.patient FROM Appointment a " +
           "WHERE a.doctor.id = :doctorId")
    List<Patient> findDistinctPatientsByDoctorId(
            @Param("doctorId") Long doctorId);

    // Quiz 1 - C2
    @Query("SELECT a.patient FROM Appointment a " +
           "GROUP BY a.patient " +
           "HAVING COUNT(a) >= :min " +
           "ORDER BY COUNT(a) DESC")
    List<Patient> findFrequentPatients(
            @Param("min") long min);

    // Quiz 2 - D2
    boolean existsBySsNumber(
            String ssNumber);
}