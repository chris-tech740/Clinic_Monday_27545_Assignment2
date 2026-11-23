package kigali.clinc.clinicmanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kigali.clinc.clinicmanagement.domain.Appointment;
import kigali.clinc.clinicmanagement.domain.AppointmentStatus;
import kigali.clinc.clinicmanagement.domain.Doctor;
import kigali.clinc.clinicmanagement.domain.Patient;
import kigali.clinc.clinicmanagement.repository.AppointmentRepository;
import kigali.clinc.clinicmanagement.repository.DoctorRepository;
import kigali.clinc.clinicmanagement.repository.PatientRepository;
import kigali.clinc.clinicmanagement.repository.PrescriptionRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PrescriptionRepository prescriptionRepo;

    // Quiz 1 - A4
    // Quiz 2 - B1
    public String saveAppointment(Appointment appointment) {

        if (appointment.getPatient() == null ||
                appointment.getPatient().getId() == null) {

            return "Patient is required";
        }

        if (appointment.getDoctor() == null ||
                appointment.getDoctor().getId() == null) {

            return "Doctor is required";
        }

        Optional<Patient> patient =
                patientRepo.findById(
                        appointment.getPatient().getId());

        if (patient.isEmpty()) {
            return "Patient not found";
        }

        Optional<Doctor> doctor =
                doctorRepo.findById(
                        appointment.getDoctor().getId());

        if (doctor.isEmpty()) {
            return "Doctor not found";
        }

        // Quiz 2 - B1
        if (appointment.getAppointmentDate() != null &&
                appointment.getAppointmentDate()
                        .isBefore(LocalDate.now())) {

            return "Appointment date cannot be in the past";
        }

        // Quiz 1 - A4
        if (appointment.getAppointmentDate() != null &&
                appointmentRepo
                        .existsByDoctor_IdAndAppointmentDateAndStatusNot(
                                doctor.get().getId(),
                                appointment.getAppointmentDate(),
                                AppointmentStatus.CANCELLED)) {

            return "Doctor is already booked on that date";
        }

        appointment.setPatient(patient.get());
        appointment.setDoctor(doctor.get());

        // Quiz 2 - B1
        appointment.setStatus(
                AppointmentStatus.SCHEDULED);

        appointmentRepo.save(appointment);

        return "Appointment is saved Successfully";
    }

    public List<Appointment> findAll() {
        return appointmentRepo.findAll();
    }

    public Optional<Appointment> findById(Long id) {
        return appointmentRepo.findById(id);
    }

    public String updateAppointment(
            Long id,
            Appointment appointment) {

        Optional<Appointment> existing =
                appointmentRepo.findById(id);

        if (existing.isEmpty()) {
            return "Appointment not found";
        }

        Appointment dbAppointment =
                existing.get();

        dbAppointment.setAppointmentDate(
                appointment.getAppointmentDate());

        dbAppointment.setReason(
                appointment.getReason());

        // Do not update status here.
        // Quiz 2 - B2 controls status changes.

        if (appointment.getPatient() != null &&
                appointment.getPatient().getId() != null) {

            Optional<Patient> patient =
                    patientRepo.findById(
                            appointment.getPatient().getId());

            if (patient.isEmpty()) {
                return "Patient not found";
            }

            dbAppointment.setPatient(
                    patient.get());
        }

        if (appointment.getDoctor() != null &&
                appointment.getDoctor().getId() != null) {

            Optional<Doctor> doctor =
                    doctorRepo.findById(
                            appointment.getDoctor().getId());

            if (doctor.isEmpty()) {
                return "Doctor not found";
            }

            dbAppointment.setDoctor(
                    doctor.get());
        }

        appointmentRepo.save(dbAppointment);

        return "Appointment is updated Successfully";
    }

    // Quiz 2 - C4
    public String deleteAppointment(Long id) {

        if (!appointmentRepo.existsById(id)) {

            return "Appointment not found";
        }

        if (prescriptionRepo.existsByAppointment_Id(id)) {

            return "That appointment has prescriptions, delete them first";
        }

        appointmentRepo.deleteById(id);

        return "Appointment is deleted Successfully";
    }

    // Quiz 2 - B2
    public String changeStatus(
            Long id,
            AppointmentStatus newStatus) {

        Optional<Appointment> appointmentOptional =
                appointmentRepo.findById(id);

        if (appointmentOptional.isEmpty()) {

            return "The appointment with that id does not exist";
        }

        Appointment appointment =
                appointmentOptional.get();

        AppointmentStatus currentStatus =
                appointment.getStatus();

        boolean allowed = false;

        if (currentStatus ==
                AppointmentStatus.SCHEDULED) {

            if (newStatus ==
                    AppointmentStatus.CONFIRMED ||
                    newStatus ==
                    AppointmentStatus.CANCELLED) {

                allowed = true;
            }
        }

        if (currentStatus ==
                AppointmentStatus.CONFIRMED) {

            if (newStatus ==
                    AppointmentStatus.COMPLETED ||
                    newStatus ==
                    AppointmentStatus.CANCELLED) {

                allowed = true;
            }
        }

        if (!allowed) {

            return "Cannot change status from "
                    + currentStatus
                    + " to "
                    + newStatus;
        }

        appointment.setStatus(newStatus);

        appointmentRepo.save(appointment);

        return "Appointment status updated successfully";
    }

    // Quiz 2 - B3
    public String rescheduleAppointment(
            Long id,
            LocalDate newDate) {

        Optional<Appointment> appointmentOptional =
                appointmentRepo.findById(id);

        if (appointmentOptional.isEmpty()) {

            return "The appointment with that id does not exist";
        }

        Appointment appointment =
                appointmentOptional.get();

        if (appointment.getStatus() ==
                AppointmentStatus.COMPLETED) {

            return "A COMPLETED appointment cannot be rescheduled";
        }

        if (appointment.getStatus() ==
                AppointmentStatus.CANCELLED) {

            return "A CANCELLED appointment cannot be rescheduled";
        }

        if (newDate.isBefore(LocalDate.now())) {

            return "Appointment date cannot be in the past";
        }

        Long doctorId =
                appointment.getDoctor().getId();

        boolean doctorBooked =
                appointmentRepo
                        .existsByDoctor_IdAndAppointmentDateAndStatusNotAndIdNot(
                                doctorId,
                                newDate,
                                AppointmentStatus.CANCELLED,
                                id);

        if (doctorBooked) {

            return "Doctor is already booked on that date";
        }

        appointment.setAppointmentDate(newDate);

        appointment.setStatus(
                AppointmentStatus.SCHEDULED);

        appointmentRepo.save(appointment);

        return "Appointment rescheduled successfully";
    }

    // Quiz 1 - A2
    public List<Appointment> findByStatus(
            AppointmentStatus status) {

        return appointmentRepo
                .findByStatusOrderByAppointmentDateAsc(
                        status);
    }

    // Quiz 1 - A3
    public List<Appointment> findBetweenDates(
            LocalDate start,
            LocalDate end) {

        return appointmentRepo
                .findByAppointmentDateBetweenOrderByAppointmentDateAsc(
                        start,
                        end);
    }

    // Quiz 1 - C1
    public List<Object[]> countByStatus() {

        return appointmentRepo.countByStatus();
    }

    // Quiz 1 - C4
    @Transactional
    public String cancelDoctorDay(
            Long doctorId,
            LocalDate date) {

        if (!doctorRepo.existsById(doctorId)) {

            return "Doctor not found";
        }

        int numberOfAppointments =
                appointmentRepo
                        .cancelAppointmentsForDoctorOnDate(
                                doctorId,
                                date);

        return numberOfAppointments
                + " appointments cancelled";
    }
}