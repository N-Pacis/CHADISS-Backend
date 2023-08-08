package rw.chadiss.backend_service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.chadiss.backend_service.enums.ELocationType;
import rw.chadiss.backend_service.models.LocationAddress;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILocationAddressRepository extends JpaRepository<LocationAddress, Long> {

    Page<LocationAddress> findAllByParentId(LocationAddress parentLocation, Pageable pageable);

    Page<LocationAddress> findAllByLocationType(ELocationType locationType, Pageable pageable);

    List<LocationAddress> findAllByLocationType(ELocationType locationType);

    Page<LocationAddress> findAllByParentIdAndLocationType(LocationAddress parentId,
                                                           ELocationType locationType,Pageable pageable);

    List<LocationAddress> findAllByParentIdAndLocationType(LocationAddress province, ELocationType district);

    @Query("SELECT l FROM LocationAddress l WHERE (LOWER(l.name) LIKE CONCAT('%', LOWER(:village), '%')) AND (LOWER(l.parentId.name) LIKE CONCAT('%', LOWER(:cell), '%') ) AND (LOWER(l.parentId.parentId.name) LIKE CONCAT('%', LOWER(:sector), '%') ) AND (LOWER(l.parentId.parentId.parentId.name) LIKE CONCAT('%', LOWER(:district), '%') )")
    Optional<LocationAddress> findVillageFromNames(String village, String cell, String sector, String district);

}
