package kigali.clinc.clinicmanagement.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kigali.clinc.clinicmanagement.domain.Office;
import kigali.clinc.clinicmanagement.service.OfficeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/offices")
public class OfficeController {

    @Autowired
    private OfficeService officeServes;

    @PostMapping(
            value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveOffice(
            @RequestBody Office office) {

        String returnedMessage =
                officeServes.saveOffice(office);

        if (returnedMessage.equalsIgnoreCase(
                "Office is saved Successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.CREATED);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllOffices() {

        return new ResponseEntity<>(
                officeServes.findAll(),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getOfficeById(
            @PathVariable Long id) {

        Optional<Office> office =
                officeServes.findById(id);

        if (office.isEmpty()) {

            return new ResponseEntity<>(
                    "Office not found",
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                office.get(),
                HttpStatus.OK);
    }

    @PutMapping(
            value = "/update/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateOffice(
            @PathVariable Long id,
            @RequestBody Office office) {

        String returnedMessage =
                officeServes.updateOffice(id, office);

        if (returnedMessage.equalsIgnoreCase(
                "Office is updated Successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK);
        }

        if (returnedMessage.equalsIgnoreCase(
                "Office not found")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.CONFLICT);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteOffice(
            @PathVariable Long id) {

        String returnedMessage =
                officeServes.deleteOffice(id);

        if (returnedMessage.equalsIgnoreCase(
                "Office is deleted Successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.NOT_FOUND);
    }

    // Quiz 1 - C3
    @GetMapping(value = "/busiest")
    public ResponseEntity<?> getBusiest() {

        Object result =
                officeServes.findBusiestOffice();

        return new ResponseEntity<>(
                result,
                HttpStatus.OK);
    }

    // Quiz 2 - A3
    @GetMapping(value = "/free")
    public ResponseEntity<?> getFreeOffices() {

        return new ResponseEntity<>(
                officeServes.findFreeOffices(),
                HttpStatus.OK);
    }
}