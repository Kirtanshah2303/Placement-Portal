package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CompanyProfile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CompanyProfile entity.
 */
@Repository
public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, Long>, JpaSpecificationExecutor<CompanyProfile> {
    @Query("select companyProfile from CompanyProfile companyProfile where companyProfile.user.login = ?#{principal.username}")
    List<CompanyProfile> findByUserIsCurrentUser();

    default Optional<CompanyProfile> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CompanyProfile> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CompanyProfile> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct companyProfile from CompanyProfile companyProfile left join fetch companyProfile.user",
        countQuery = "select count(distinct companyProfile) from CompanyProfile companyProfile"
    )
    Page<CompanyProfile> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct companyProfile from CompanyProfile companyProfile left join fetch companyProfile.user")
    List<CompanyProfile> findAllWithToOneRelationships();

    @Query("select companyProfile from CompanyProfile companyProfile left join fetch companyProfile.user where companyProfile.id =:id")
    Optional<CompanyProfile> findOneWithToOneRelationships(@Param("id") Long id);
}
