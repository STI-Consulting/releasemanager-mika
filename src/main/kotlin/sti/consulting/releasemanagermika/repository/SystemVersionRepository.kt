package sti.consulting.releasemanagermika.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import sti.consulting.releasemanagermika.model.SystemVersion

@Repository
interface SystemVersionRepository : JpaRepository<SystemVersion, Long> {
    @Query("SELECT sv FROM SystemVersion sv WHERE sv.version = (SELECT MAX(s.version) FROM SystemVersion s)")
    fun findLatestVersion(): SystemVersion?
}