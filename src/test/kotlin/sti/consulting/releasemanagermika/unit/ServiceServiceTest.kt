package sti.consulting.releasemanagermika.unit

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import sti.consulting.releasemanagermika.model.Service
import sti.consulting.releasemanagermika.model.SystemVersion
import sti.consulting.releasemanagermika.repository.ServiceRepository
import sti.consulting.releasemanagermika.repository.SystemVersionRepository
import sti.consulting.releasemanagermika.service.ServiceService

@ExtendWith(MockitoExtension::class)
internal class ServiceServiceTest {

    @InjectMocks
    private lateinit var serviceDeployment: ServiceService;

    @Mock
    private lateinit var serviceRepository: ServiceRepository

    @Mock
    private lateinit var systemVersionRepository: SystemVersionRepository

    private lateinit var testService: Service
    private lateinit var testSystemVersion: SystemVersion

    @BeforeEach
    fun setUp() {
        testService = Service( null,"test-service", 1L)
        testSystemVersion = SystemVersion(listOf(testService))
        testSystemVersion.version=1L;
    }

    @Test
    fun `test deployService when service is already deployed`() {
        `when`(serviceRepository.findLatestVersionOfServiceByName(testService.name)).thenReturn(testService)
        `when`(systemVersionRepository.findLatestVersion()).thenReturn(testSystemVersion)

        val result = serviceDeployment.deployService(testService)

        assertEquals(testSystemVersion.version, result)
    }

    @Test
    fun `test deployService when service is not deployed`() {
        `when`(serviceRepository.findLatestVersionOfServiceByName(testService.name)).thenReturn(null)
        `when`(serviceRepository.save(testService)).thenReturn(testService)
        `when`(serviceRepository.findLatestVersionsOfAllServices()).thenReturn(listOf(testService))
        `when`(systemVersionRepository.save(any(SystemVersion::class.java))).thenReturn(testSystemVersion)

        val result = serviceDeployment.deployService(testService)

        assertEquals(testSystemVersion.version, result)
    }

}
