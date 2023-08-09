package rw.chadiss.backend_service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.chadiss.backend_service.enums.EFileStatus;
import rw.chadiss.backend_service.models.File;

import java.util.List;
import java.util.UUID;

public interface IFileRepository extends JpaRepository<File, UUID> {
    Page<File> findAllByStatus(Pageable pageable, EFileStatus status);

    List<File> findByPathContainsAndNameContains(String path, String name);

}
