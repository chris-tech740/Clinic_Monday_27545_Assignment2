package kigali.clinc.clinicmanagement.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinc.clinicmanagement.domain.AppointmentStatus;
import kigali.clinc.clinicmanagement.repository.AppointmentRepository;
import kigali.clinc.clinicmanagement.repository.DoctorRepository;
import kigali.clinc.clinicmanagement.repository.OfficeRepository;
import kigali.clinc.clinicmanagement.repository.PatientRepository;
import kigali.clinc.clinicmanagement.repository.PrescriptionRepository;

@Service
public class DashboardService {

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private OfficeRepository officeRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private PrescriptionRepository prescriptionRepo;

    public Map<String, Long> getSummary() {

        Map<String, Long> summary =
                new LinkedHashMap<>();

        long patients =
                patientRepo.count();

        long doctors =
                doctorRepo.count();

        long freeOffices =
                officeRepo
                        .findFreeOffices()
                        .size();

        long appointmentsToday =
                appointmentRepo
                        .countByAppointmentDate(
                                LocalDate.now());

        long scheduled =
                appointmentRepo
                        .countByStatus(
                                AppointmentStatus.SCHEDULED);

        long prescriptions =
                prescriptionRepo.count();

        summary.put(
                "patients",
                patients);

        summary.put(
                "doctors",
                doctors);

        summary.put(
                "freeOffices",
                freeOffices);

        summary.put(
                "appointmentsToday",
                appointmentsToday);

        summary.put(
                "scheduled",
                scheduled);

        summary.put(
                "prescriptions",
                prescriptions);

        return summary;
    }
}