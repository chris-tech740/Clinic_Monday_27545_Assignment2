package kigali.clinc.clinicmanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.clinc.clinicmanagement.domain.Appointment;
import kigali.clinc.clinicmanagement.domain.AppointmentStatus;

@Repository
public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    // Quiz 1 - A2
    List<Appointment> findByStatusOrderByAppointmentDateAsc(
            AppointmentStatus status);

    // Quiz 1 - A3
    List<Appointment> findByAppointmentDateBetweenOrderByAppointmentDateAsc(
            LocalDate start,
            LocalDate end);

    // Quiz 1 - A4
    boolean existsByDoctor_IdAndAppointmentDateAndStatusNot(
            Long doctorId,
            LocalDate appointmentDate,
            AppointmentStatus status);

    // Quiz 2 - B3
    boolean existsByDoctor_IdAndAppointmentDateAndStatusNotAndIdNot(
            Long doctorId,
            LocalDate appointmentDate,
            AppointmentStatus status,
            Long id);

    // Quiz 2 - D1
    List<Appointment> findByDoctor_IdAndAppointmentDateAndStatusNot(
            Long doctorId,
            LocalDate appointmentDate,
            AppointmentStatus status);

    // Quiz 2 - D2
    List<Appointment> findByPatient_SsNumberAndAppointmentDateGreaterThanEqualAndStatusInOrderByAppointmentDateAsc(
            String ssNumber,
            LocalDate appointmentDate,
            List<AppointmentStatus> statuses);

    // Quiz 2 - D4
    long countByAppointmentDate(
            LocalDate appointmentDate);

    // Quiz 2 - D4
    long countByStatus(
            AppointmentStatus status);

    // Quiz 1 - C1
    @Query("SELECT a.status, COUNT(a) " +
           "FROM Appointment a " +
           "GROUP BY a.status")
    List<Object[]> countByStatus();

    // Quiz 1 - C3
    @Query("SELECT o.name, o.officeNumber, COUNT(a) " +
           "FROM Appointment a " +
           "JOIN a.doctor d " +
           "JOIN d.office o " +
           "GROUP BY o.id, o.name, o.officeNumber " +
           "ORDER BY COUNT(a) DESC")
    List<Object[]> findBusiestOffice();

    // Quiz 1 - C4
    @Modifying
    @Query("UPDATE Appointment a " +
           "SET a.status = kigali.clinc.clinicmanagement.domain.AppointmentStatus.CANCELLED " +
           "WHERE a.doctor.id = :doctorId " +
           "AND a.appointmentDate = :date " +
           "AND a.status <> kigali.clinc.clinicmanagement.domain.AppointmentStatus.COMPLETED")
    int cancelAppointmentsForDoctorOnDate(
            @Param("doctorId") Long doctorId,
            @Param("date") LocalDate date);
}