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

import kigali.clinc.clinicmanagement.domain.Appointment;
import kigali.clinc.clinicmanagement.domain.AppointmentStatus;
import kigali.clinc.clinicmanagement.service.AppointmentService;

@RestController
@RequestMapping(value = "/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Quiz 1 - A4
    // Quiz 2 - B1
    @PostMapping(
            value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveAppointment(
            @RequestBody Appointment appointment) {

        String returnedMessage =
                appointmentService
                        .saveAppointment(appointment);

        if (returnedMessage.equals(
                "Appointment is saved Successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.CREATED);
        }

        if (returnedMessage.equals(
                "Appointment date cannot be in the past")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.BAD_REQUEST);
        }

        if (returnedMessage.equals(
                "Doctor is already booked on that date")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllAppointments() {

        return new ResponseEntity<>(
                appointmentService.findAll(),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getAppointmentById(
            @PathVariable Long id) {

        Optional<Appointment> appointment =
                appointmentService.findById(id);

        if (appointment.isEmpty()) {

            return new ResponseEntity<>(
                    "Appointment not found",
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                appointment.get(),
                HttpStatus.OK);
    }

    @PutMapping(
            value = "/update/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAppointment(
            @PathVariable Long id,
            @RequestBody Appointment appointment) {

        String returnedMessage =
                appointmentService
                        .updateAppointment(
                                id,
                                appointment);

        if (returnedMessage.equals(
                "Appointment is updated Successfully")) {

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
    public ResponseEntity<?> deleteAppointment(
            @PathVariable Long id) {

        String returnedMessage =
                appointmentService
                        .deleteAppointment(id);

        if (returnedMessage.equals(
                "Appointment is deleted Successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK);
        }

        if (returnedMessage.equals(
                "That appointment has prescriptions, delete them first")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.NOT_FOUND);
    }

    // Quiz 2 - B2
    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<?> changeStatus(
            @PathVariable Long id,
            @RequestParam AppointmentStatus status) {

        String returnedMessage =
                appointmentService
                        .changeStatus(id, status);

        if (returnedMessage.equals(
                "Appointment status updated successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK);
        }

        if (returnedMessage.startsWith(
                "Cannot change status from")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.NOT_FOUND);
    }

    // Quiz 2 - B3
    @PatchMapping(value = "/{id}/reschedule")
    public ResponseEntity<?> rescheduleAppointment(
            @PathVariable Long id,
            @RequestParam String newDate) {

        LocalDate appointmentDate =
                LocalDate.parse(newDate);

        String returnedMessage =
                appointmentService
                        .rescheduleAppointment(
                                id,
                                appointmentDate);

        if (returnedMessage.equals(
                "Appointment rescheduled successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK);
        }

        if (returnedMessage.equals(
                "Doctor is already booked on that date")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.CONFLICT);
        }

        if (returnedMessage.equals(
                "The appointment with that id does not exist")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.BAD_REQUEST);
    }

    // Quiz 1 - A2
    @GetMapping(value = "/by-status")
    public ResponseEntity<?> getByStatus(
            @RequestParam AppointmentStatus status) {

        return new ResponseEntity<>(
                appointmentService
                        .findByStatus(status),
                HttpStatus.OK);
    }

    // Quiz 1 - A3
    @GetMapping(value = "/between")
    public ResponseEntity<?> getBetweenDates(
            @RequestParam String start,
            @RequestParam String end) {

        LocalDate startDate =
                LocalDate.parse(start);

        LocalDate endDate =
                LocalDate.parse(end);

        return new ResponseEntity<>(
                appointmentService
                        .findBetweenDates(
                                startDate,
                                endDate),
                HttpStatus.OK);
    }

    // Quiz 1 - C1
    @GetMapping(value = "/stats/by-status")
    public ResponseEntity<?> countByStatus() {

        return new ResponseEntity<>(
                appointmentService.countByStatus(),
                HttpStatus.OK);
    }

    // Quiz 1 - C4
    @PatchMapping(value = "/cancel-day")
    public ResponseEntity<?> cancelDoctorDay(
            @RequestParam Long doctorId,
            @RequestParam String date) {

        LocalDate appointmentDate =
                LocalDate.parse(date);

        String returnedMessage =
                appointmentService
                        .cancelDoctorDay(
                                doctorId,
                                appointmentDate);

        if (returnedMessage.equals(
                "Doctor not found")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.OK);
    }
}