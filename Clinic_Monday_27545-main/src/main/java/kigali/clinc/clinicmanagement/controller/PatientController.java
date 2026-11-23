package kigali.clinc.clinicmanagement.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.clinc.clinicmanagement.domain.Patient;
import kigali.clinc.clinicmanagement.service.PatientService;

@RestController
@RequestMapping(value = "/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping(
            value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> savePatient(
            @RequestBody Patient patient) {

        String returnedMessage =
                patientService.savePatient(patient);

        if (returnedMessage.equalsIgnoreCase(
                "Patient is saved Successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.CREATED);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllPatients() {

        return new ResponseEntity<>(
                patientService.findAll(),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getPatientById(
            @PathVariable Long id) {

        Optional<Patient> patient =
                patientService.findById(id);

        if (patient.isEmpty()) {

            return new ResponseEntity<>(
                    "Patient not found",
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                patient.get(),
                HttpStatus.OK);
    }

    @PutMapping(
            value = "/update/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePatient(
            @PathVariable Long id,
            @RequestBody Patient patient) {

        String returnedMessage =
                patientService.updatePatient(
                        id,
                        patient);

        if (returnedMessage.equalsIgnoreCase(
                "Patient is updated Successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deletePatient(
            @PathVariable Long id) {

        String returnedMessage =
                patientService.deletePatient(id);

        if (returnedMessage.equalsIgnoreCase(
                "Patient is deleted Successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.NOT_FOUND);
    }

    // Quiz 1 - A1
    @GetMapping(value = "/by-last-name")
    public ResponseEntity<?> getByLastName(
            @RequestParam String lastName) {

        return new ResponseEntity<>(
                patientService
                        .findByLastName(lastName),
                HttpStatus.OK);
    }

    // Quiz 1 - B4
    @GetMapping(value = "/of-doctor/{doctorId}")
    public ResponseEntity<?> getPatientsOfDoctor(
            @PathVariable Long doctorId) {

        Object result =
                patientService
                        .findPatientsOfDoctor(
                                doctorId);

        if (result instanceof String) {

            return new ResponseEntity<>(
                    result,
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                result,
                HttpStatus.OK);
    }

    // Quiz 1 - C2
    @GetMapping(value = "/frequent")
    public ResponseEntity<?> getFrequent(
            @RequestParam long min) {

        return new ResponseEntity<>(
                patientService
                        .findFrequentPatients(min),
                HttpStatus.OK);
    }

    // Quiz 2 - D2
    @GetMapping(value = "/{ssNumber}/upcoming")
    public ResponseEntity<?> getUpcomingAppointments(
            @PathVariable String ssNumber) {

        Object result =
                patientService
                        .findUpcomingAppointments(
                                ssNumber);

        if (result instanceof String) {

            return new ResponseEntity<>(
                    result,
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                result,
                HttpStatus.OK);
    }
}