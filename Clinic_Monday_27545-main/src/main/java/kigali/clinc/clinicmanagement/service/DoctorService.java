package kigali.clinc.clinicmanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kigali.clinc.clinicmanagement.domain.AppointmentStatus;
import kigali.clinc.clinicmanagement.domain.Doctor;
import kigali.clinc.clinicmanagement.domain.Office;
import kigali.clinc.clinicmanagement.domain.Specialization;
import kigali.clinc.clinicmanagement.repository.AppointmentRepository;
import kigali.clinc.clinicmanagement.repository.DoctorRepository;
import kigali.clinc.clinicmanagement.repository.OfficeRepository;
import kigali.clinc.clinicmanagement.repository.SpecializationRepository;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private OfficeRepository officeRepo;

    @Autowired
    private SpecializationRepository specializationRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    public String saveDoctor(Doctor doctor) {

        if (doctor.getOffice() != null &&
                doctor.getOffice().getId() != null) {

            Optional<Office> office =
                    officeRepo.findById(
                            doctor.getOffice().getId());

            if (office.isEmpty()) {
                return "Office not found";
            }

            List<Doctor> existingDoctors =
                    doctorRepo.findAll();

            for (Doctor existingDoctor : existingDoctors) {

                if (existingDoctor.getOffice() != null &&
                        existingDoctor.getOffice().getId()
                                .equals(office.get().getId())) {

                    return "That office already has a doctor";
                }
            }

            doctor.setOffice(office.get());
        }

        doctorRepo.save(doctor);

        return "Doctor is saved Successfully";
    }

    public List<Doctor> findAll() {
        return doctorRepo.findAll();
    }

    public Optional<Doctor> findById(Long id) {
        return doctorRepo.findById(id);
    }

    public String updateDoctor(
            Long id,
            Doctor doctor) {

        Optional<Doctor> existingDoctor =
                doctorRepo.findById(id);

        if (existingDoctor.isEmpty()) {
            return "Doctor not found";
        }

        Doctor dbDoctor =
                existingDoctor.get();

        dbDoctor.setFirstName(
                doctor.getFirstName());

        dbDoctor.setLastName(
                doctor.getLastName());

        dbDoctor.setDateOfBirth(
                doctor.getDateOfBirth());

        if (doctor.getOffice() != null &&
                doctor.getOffice().getId() != null) {

            Optional<Office> office =
                    officeRepo.findById(
                            doctor.getOffice().getId());

            if (office.isEmpty()) {
                return "Office not found";
            }

            dbDoctor.setOffice(office.get());
        }

        doctorRepo.save(dbDoctor);

        return "Doctor is updated Successfully";
    }

    @Transactional
    public String deleteDoctor(Long id) {

        Optional<Doctor> existingDoctor =
                doctorRepo.findById(id);

        if (existingDoctor.isEmpty()) {
            return "Doctor not found";
        }

        Doctor doctor =
                existingDoctor.get();

        Office office =
                doctor.getOffice();

        if (office != null) {

            doctor.setOffice(null);
            office.setDoctor(null);

            officeRepo.save(office);
            doctorRepo.save(doctor);
        }

        doctorRepo.delete(doctor);

        return "Doctor is deleted Successfully";
    }

    // Quiz 2 - A1
    public String addSpecialization(
            Long doctorId,
            Long specializationId) {

        Optional<Doctor> doctorOptional =
                doctorRepo.findById(doctorId);

        if (doctorOptional.isEmpty()) {

            return "The doctor with that id does not exist";
        }

        Optional<Specialization> specializationOptional =
                specializationRepo.findById(
                        specializationId);

        if (specializationOptional.isEmpty()) {

            return "That specialization does not exist";
        }

        Doctor doctor =
                doctorOptional.get();

        Specialization specialization =
                specializationOptional.get();

        if (doctor.getSpecializations()
                .contains(specialization)) {

            return "The doctor already has that specialization";
        }

        doctor.getSpecializations()
                .add(specialization);

        doctorRepo.save(doctor);

        return "Specialization added to the doctor";
    }

    // Quiz 2 - A2
    public String removeSpecialization(
            Long doctorId,
            Long specializationId) {

        Optional<Doctor> doctorOptional =
                doctorRepo.findById(doctorId);

        if (doctorOptional.isEmpty()) {

            return "The doctor with that id does not exist";
        }

        Optional<Specialization> specializationOptional =
                specializationRepo.findById(
                        specializationId);

        if (specializationOptional.isEmpty()) {

            return "That specialization does not exist";
        }

        Doctor doctor =
                doctorOptional.get();

        Specialization specialization =
                specializationOptional.get();

        if (!doctor.getSpecializations()
                .contains(specialization)) {

            return "The doctor does not have that specialization";
        }

        doctor.getSpecializations()
                .remove(specialization);

        doctorRepo.save(doctor);

        return "Specialization removed from the doctor";
    }

    // Quiz 2 - A4
    public String changeOffice(
            Long doctorId,
            int officeNumber) {

        Optional<Doctor> doctorOptional =
                doctorRepo.findById(doctorId);

        if (doctorOptional.isEmpty()) {

            return "The doctor with that id does not exist";
        }

        Office office =
                officeRepo.findByOfficeNumber(
                        officeNumber);

        if (office == null) {

            return "The office with that office number does not exist";
        }

        Doctor doctor =
                doctorOptional.get();

        if (doctor.getOffice() != null &&
                doctor.getOffice().getOfficeNumber()
                        == officeNumber) {

            return "Office changed successfully";
        }

        boolean officeTaken =
                doctorRepo
                        .existsByOffice_OfficeNumber(
                                officeNumber);

        if (officeTaken) {

            return "That office is taken by other doctor";
        }

        doctor.setOffice(office);

        doctorRepo.save(doctor);

        return "Office changed successfully";
    }

    // Quiz 1 - B1
    public List<Doctor> findBySpecializationName(
            String name) {

        return doctorRepo
                .findBySpecializationName(name);
    }

    // Quiz 1 - B2
    public List<Doctor> findDoctorsWithoutOffice() {

        return doctorRepo
                .findDoctorsWithoutOffice();
    }

    // Quiz 2 - D1
    public Object findDoctorAgenda(
            Long doctorId,
            LocalDate date) {

        if (!doctorRepo.existsById(doctorId)) {

            return "The doctor with that id does not exist";
        }

        return appointmentRepo
                .findByDoctor_IdAndAppointmentDateAndStatusNot(
                        doctorId,
                        date,
                        AppointmentStatus.CANCELLED);
    }

    // Quiz 2 - D3
    public List<Doctor> searchDoctors(
            String name) {

        return doctorRepo.searchByName(name);
    }
}