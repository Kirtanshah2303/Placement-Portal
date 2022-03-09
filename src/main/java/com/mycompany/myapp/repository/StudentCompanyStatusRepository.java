package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.StudentCompanyStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StudentCompanyStatus entity.
 */
@Repository
public interface StudentCompanyStatusRepository
    extends JpaRepository<StudentCompanyStatus, Long>, JpaSpecificationExecutor<StudentCompanyStatus> {
    default Optional<StudentCompanyStatus> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<StudentCompanyStatus> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<StudentCompanyStatus> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct studentCompanyStatus from StudentCompanyStatus studentCompanyStatus left join fetch studentCompanyStatus.student",
        countQuery = "select count(distinct studentCompanyStatus) from StudentCompanyStatus studentCompanyStatus"
    )
    Page<StudentCompanyStatus> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct studentCompanyStatus from StudentCompanyStatus studentCompanyStatus left join fetch studentCompanyStatus.student"
    )
    List<StudentCompanyStatus> findAllWithToOneRelationships();

    @Query(
        "select studentCompanyStatus from StudentCompanyStatus studentCompanyStatus left join fetch studentCompanyStatus.student where studentCompanyStatus.id =:id"
    )
    Optional<StudentCompanyStatus> findOneWithToOneRelationships(@Param("id") Long id);
}
