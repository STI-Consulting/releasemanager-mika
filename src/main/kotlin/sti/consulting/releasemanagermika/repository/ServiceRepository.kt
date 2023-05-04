package sti.consulting.releasemanagermika.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import sti.consulting.releasemanagermika.model.Service

@Repository
interface ServiceRepository : JpaRepository<Service, String> {

    fun findTopByOrderByIdDesc(): Service?

    fun findDistinctTopByOrderByIdDesc(): List<Service>


    @Query("SELECT s FROM Service s WHERE s.version IN (SELECT MAX(sv.version) FROM Service sv GROUP BY sv.name)")
    fun findLatestVersionsOfAllServices(): List<Service>

    @Query("SELECT s FROM Service s WHERE s.name = :serviceName AND s.version = (SELECT MAX(s2.version) FROM Service s2 WHERE s2.name = :serviceName)")
    fun findLatestVersionOfServiceByName(serviceName: String): Service?

}