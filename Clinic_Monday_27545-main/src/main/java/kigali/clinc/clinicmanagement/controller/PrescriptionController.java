package kigali.clinc.clinicmanagement.controller;

import java.util.List;
import java.util.UUID;

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

import kigali.clinc.clinicmanagement.domain.Prescription;
import kigali.clinc.clinicmanagement.service.PrescriptionService;

@RestController
@RequestMapping(value = "/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;


    // Quiz 2 - C1
    @PostMapping(
            value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> savePrescription(
            @RequestBody Prescription prescription,
            @RequestParam Long appointmentId) {

        String returnedMessage =
                prescriptionService
                        .savePrescription(
                                prescription,
                                appointmentId);

        if (returnedMessage.equals(
                "Prescription saved successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.CREATED);
        }

        if (returnedMessage.equals(
                "Prescriptions are only for COMPLETED appointments")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.NOT_FOUND);
    }

    // Quiz 2 - C2
    @GetMapping(value = "/appointment/{appointmentId}")
    public ResponseEntity<?> getByAppointment(
            @PathVariable Long appointmentId) {

        List<Prescription> prescriptions =
                prescriptionService
                        .findByAppointment(
                                appointmentId);

        if (prescriptions == null) {

            return new ResponseEntity<>(
                    "The appointment with that id does not exist",
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                prescriptions,
                HttpStatus.OK);
    }

    // Quiz 2 - C3
    @GetMapping(value = "/patient/{ssNumber}")
    public ResponseEntity<?> getByPatient(
            @PathVariable String ssNumber) {

        return new ResponseEntity<>(
                prescriptionService
                        .findByPatient(ssNumber),
                HttpStatus.OK);
    }

    // Quiz 2 - C4
    @PutMapping(
            value = "/update/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePrescription(
            @PathVariable UUID id,
            @RequestBody Prescription prescription) {

        String returnedMessage =
                prescriptionService
                        .updatePrescription(
                                id,
                                prescription);

        if (returnedMessage.equals(
                "Prescription updated successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.NOT_FOUND);
    }

    // Quiz 2 - C4
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deletePrescription(
            @PathVariable UUID id) {

        String returnedMessage =
                prescriptionService
                        .deletePrescription(id);

        if (returnedMessage.equals(
                "Prescription deleted successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.NOT_FOUND);
    }
}