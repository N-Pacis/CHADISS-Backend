package rw.chadiss.backend_service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.chadiss.backend_service.enums.ERole;
import rw.chadiss.backend_service.enums.EUserStatus;
import rw.chadiss.backend_service.models.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmailOrPhoneNumberOrNationalId(String email, String phoneNumber, String nationalId);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);

    @Query("SELECT u FROM User u WHERE (:status IS NULL OR u.status = :status) AND (:role IS NULL OR u.role = :role)  AND LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE CONCAT('%', LOWER(:name), '%')")
    Page<User> search(EUserStatus status, String name, ERole role, Pageable pageable);

}
