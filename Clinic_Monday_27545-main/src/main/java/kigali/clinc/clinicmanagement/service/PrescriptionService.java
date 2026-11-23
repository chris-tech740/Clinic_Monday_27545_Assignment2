package kigali.clinc.clinicmanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinc.clinicmanagement.domain.Appointment;
import kigali.clinc.clinicmanagement.domain.AppointmentStatus;
import kigali.clinc.clinicmanagement.domain.Prescription;
import kigali.clinc.clinicmanagement.repository.AppointmentRepository;
import kigali.clinc.clinicmanagement.repository.PrescriptionRepository;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    // Quiz 2 - C1
    public String savePrescription(
            Prescription prescription,
            Long appointmentId) {

        Optional<Appointment> appointmentOptional =
                appointmentRepo.findById(
                        appointmentId);

        if (appointmentOptional.isEmpty()) {

            return "The appointment with that id does not exist";
        }

        Appointment appointment =
                appointmentOptional.get();

        if (appointment.getStatus() !=
                AppointmentStatus.COMPLETED) {

            return "Prescriptions are only for COMPLETED appointments";
        }

        prescription.setIssuedDate(
                LocalDate.now());

        prescription.setAppointment(
                appointment);

        prescriptionRepo.save(
                prescription);

        return "Prescription saved successfully";
    }

    // Quiz 2 - C2
    public List<Prescription> findByAppointment(
            Long appointmentId) {

        Optional<Appointment> appointmentOptional =
                appointmentRepo.findById(
                        appointmentId);

        if (appointmentOptional.isEmpty()) {
            return null;
        }

        return prescriptionRepo
                .findByAppointment(
                        appointmentOptional.get());
    }

    // Quiz 2 - C3
    public List<Prescription> findByPatient(
            String ssNumber) {

        return prescriptionRepo
                .findByPatientSsNumber(
                        ssNumber);
    }

    // Quiz 2 - C4
    public String updatePrescription(
            UUID id,
            Prescription prescription) {

        Optional<Prescription> existing =
                prescriptionRepo.findById(id);

        if (existing.isEmpty()) {

            return "The prescription with that id does not exist";
        }

        Prescription dbPrescription =
                existing.get();

        dbPrescription.setMedicineName(
                prescription.getMedicineName());

        dbPrescription.setDosage(
                prescription.getDosage());

        dbPrescription.setDurationDays(
                prescription.getDurationDays());

        prescriptionRepo.save(
                dbPrescription);

        return "Prescription updated successfully";
    }

    // Quiz 2 - C4
    public String deletePrescription(
            UUID id) {

        if (!prescriptionRepo.existsById(id)) {

            return "The prescription with that id does not exist";
        }

        prescriptionRepo.deleteById(id);

        return "Prescription deleted successfully";
    }
}