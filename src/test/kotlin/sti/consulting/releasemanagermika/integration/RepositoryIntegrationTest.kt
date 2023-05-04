package sti.consulting.releasemanagermika.integration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import sti.consulting.releasemanagermika.model.Service
import sti.consulting.releasemanagermika.model.SystemVersion
import sti.consulting.releasemanagermika.repository.ServiceRepository
import sti.consulting.releasemanagermika.repository.SystemVersionRepository

@DataJpaTest
class RepositoryIntegrationTest {

    @Autowired
    lateinit var serviceRepository: ServiceRepository

    @Autowired
    lateinit var systemVersionRepository: SystemVersionRepository

    @Test
    fun testServiceRepository() {
        val service = Service(name = "Test Service", version = 1L)
        serviceRepository.save(service)

        val latestService = serviceRepository.findLatestVersionOfServiceByName(service.name)
        assertNotNull(latestService)
        assertEquals(service, latestService)
    }

    @Test
    fun testSystemVersionRepository() {
        val service = Service(name = "Test Service", version = 1L)
        serviceRepository.save(service)

        val systemVersion = SystemVersion(listOf(service))
        systemVersionRepository.save(systemVersion)

        val latestVersion = systemVersionRepository.findLatestVersion()
        assertNotNull(latestVersion)
        assertEquals(systemVersion, latestVersion)
    }
}
