package kigali.clinc.clinicmanagement.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.clinc.clinicmanagement.domain.Doctor;
import kigali.clinc.clinicmanagement.service.DoctorService;

@RestController
@RequestMapping(value = "/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping(
            value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveDoctor(
            @RequestBody Doctor doctor) {

        String returnedMessage =
                doctorService.saveDoctor(doctor);

        if (returnedMessage.equalsIgnoreCase(
                "Doctor is saved Successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.CREATED);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllDoctors() {

        return new ResponseEntity<>(
                doctorService.findAll(),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDoctorById(
            @PathVariable Long id) {

        Optional<Doctor> doctor =
                doctorService.findById(id);

        if (doctor.isEmpty()) {

            return new ResponseEntity<>(
                    "Doctor not found",
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                doctor.get(),
                HttpStatus.OK);
    }

    @PutMapping(
            value = "/update/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDoctor(
            @PathVariable Long id,
            @RequestBody Doctor doctor) {

        String returnedMessage =
                doctorService.updateDoctor(
                        id,
                        doctor);

        if (returnedMessage.equalsIgnoreCase(
                "Doctor is updated Successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK);
        }

        if (returnedMessage.equalsIgnoreCase(
                "Doctor not found")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.CONFLICT);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteDoctor(
            @PathVariable Long id) {

        String returnedMessage =
                doctorService.deleteDoctor(id);

        if (returnedMessage.equalsIgnoreCase(
                "Doctor is deleted Successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.NOT_FOUND);
    }

    // Quiz 2 - A1
    @PutMapping(
            value = "/{doctorId}/specializations/{specializationId}")
    public ResponseEntity<?> addSpecialization(
            @PathVariable Long doctorId,
            @PathVariable Long specializationId) {

        String returnedMessage =
                doctorService.addSpecialization(
                        doctorId,
                        specializationId);

        if (returnedMessage.equals(
                "Specialization added to the doctor")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK);
        }

        if (returnedMessage.equals(
                "The doctor already has that specialization")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.NOT_FOUND);
    }

    // Quiz 2 - A2
    @DeleteMapping(
            value = "/{doctorId}/specializations/{specializationId}")
    public ResponseEntity<?> removeSpecialization(
            @PathVariable Long doctorId,
            @PathVariable Long specializationId) {

        String returnedMessage =
                doctorService.removeSpecialization(
                        doctorId,
                        specializationId);

        if (returnedMessage.equals(
                "Specialization removed from the doctor")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK);
        }

        if (returnedMessage.equals(
                "The doctor does not have that specialization")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.NOT_FOUND);
    }

    // Quiz 2 - A4
    @PatchMapping(value = "/{doctorId}/office")
    public ResponseEntity<?> changeOffice(
            @PathVariable Long doctorId,
            @RequestParam int officeNumber) {

        String returnedMessage =
                doctorService.changeOffice(
                        doctorId,
                        officeNumber);

        if (returnedMessage.equals(
                "Office changed successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK);
        }

        if (returnedMessage.equals(
                "That office is taken by other doctor")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.NOT_FOUND);
    }

    // Quiz 2 - D1
    @GetMapping(value = "/{doctorId}/agenda")
    public ResponseEntity<?> getDoctorAgenda(
            @PathVariable Long doctorId,
            @RequestParam String date) {

        LocalDate agendaDate =
                LocalDate.parse(date);

        Object result =
                doctorService.findDoctorAgenda(
                        doctorId,
                        agendaDate);

        if (result instanceof String) {

            return new ResponseEntity<>(
                    result,
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                result,
                HttpStatus.OK);
    }

    // Quiz 2 - D3
    @GetMapping(value = "/search")
    public ResponseEntity<?> searchDoctors(
            @RequestParam String name) {

        return new ResponseEntity<>(
                doctorService.searchDoctors(name),
                HttpStatus.OK);
    }

    // Quiz 1 - B1
    @GetMapping(value = "/by-specialization")
    public ResponseEntity<?> getBySpecialization(
            @RequestParam String name) {

        return new ResponseEntity<>(
                doctorService
                        .findBySpecializationName(name),
                HttpStatus.OK);
    }

    // Quiz 1 - B2
    @GetMapping(value = "/without-office")
    public ResponseEntity<?> getWithoutOffice() {

        return new ResponseEntity<>(
                doctorService
                        .findDoctorsWithoutOffice(),
                HttpStatus.OK);
    }
}