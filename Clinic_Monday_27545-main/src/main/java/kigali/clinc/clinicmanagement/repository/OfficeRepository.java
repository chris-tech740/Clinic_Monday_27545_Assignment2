package kigali.clinc.clinicmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kigali.clinc.clinicmanagement.domain.Office;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {

    Office findByOfficeNumber(int officeNumber);

    Optional<Office> findByNameAndOfficeNumber(
            String name,
            int officeNumber);

    Boolean existsByOfficeNumber(int officeNumber);

    // Quiz 2 - A3
    @Query("SELECT o FROM Office o " +
           "WHERE NOT EXISTS " +
           "(SELECT d FROM Doctor d WHERE d.office = o) " +
           "ORDER BY o.officeNumber ASC")
    List<Office> findFreeOffices();
}