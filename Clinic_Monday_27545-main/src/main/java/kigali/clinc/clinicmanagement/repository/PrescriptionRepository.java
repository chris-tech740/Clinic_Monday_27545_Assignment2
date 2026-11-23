package kigali.clinc.clinicmanagement.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.clinc.clinicmanagement.domain.Appointment;
import kigali.clinc.clinicmanagement.domain.Prescription;

@Repository
public interface PrescriptionRepository
        extends JpaRepository<Prescription, UUID> {

    // Quiz 2 - C2
    List<Prescription> findByAppointment(
            Appointment appointment);

    // Quiz 2 - C3
    @Query("SELECT p FROM Prescription p " +
           "WHERE p.appointment.patient.ssNumber = :ssNumber " +
           "ORDER BY p.issuedDate DESC")
    List<Prescription> findByPatientSsNumber(
            @Param("ssNumber") String ssNumber);

    // Quiz 2 - C4
    boolean existsByAppointment_Id(
            Long appointmentId);
}