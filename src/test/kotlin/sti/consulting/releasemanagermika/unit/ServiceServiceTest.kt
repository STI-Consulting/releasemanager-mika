package sti.consulting.releasemanagermika.unit

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import sti.consulting.releasemanagermika.model.Service
import sti.consulting.releasemanagermika.model.SystemVersion
import sti.consulting.releasemanagermika.repository.ServiceRepository
import sti.consulting.releasemanagermika.repository.SystemVersionRepository
import sti.consulting.releasemanagermika.service.ServiceService
import java.util.Optional

class ServiceServiceTest {

    @Mock
    lateinit var serviceRepository: ServiceRepository

    @Mock
    lateinit var systemVersionRepository: SystemVersionRepository

    @InjectMocks
    lateinit var serviceService: ServiceService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testDeployService() {
        val service = Service(name = "Test Service", version = 1L)
        `when`(serviceRepository.findLatestVersionOfServiceByName(service.name)).thenReturn(service)
        `when`(systemVersionRepository.findLatestVersion()).thenReturn(SystemVersion(listOf(service)))

        val result = serviceService.deployService(service)
        assertEquals(1L, result)
    }

    @Test
    fun testDeployServiceNewVersion() {
        val service = Service(name = "Test Service", version = 2L)
        `when`(serviceRepository.findLatestVersionOfServiceByName(service.name)).thenReturn(Service(name = "Test Service", version = 1L))
        `when`(serviceRepository.save(service)).thenReturn(service)
        `when`(serviceRepository.findLatestVersionsOfAllServices()).thenReturn(listOf(service))
        `when`(systemVersionRepository.save(any(SystemVersion::class.java))).thenReturn(SystemVersion(listOf(service)))
        `when`(systemVersionRepository.findLatestVersion()).thenReturn(SystemVersion(listOf(service)))

        val result = serviceService.deployService(service)
        assertEquals(1L, result);
    }

    @Test
    fun testGetSystemVersion() {
        val systemVersion = SystemVersion(listOf(Service(name = "Test Service", version = 1L)))
        `when`(systemVersionRepository.findById(1L)).thenReturn(Optional.of(systemVersion))

        val result = serviceService.getSystemVersion(1L)
        assertTrue(result.isPresent)
        assertEquals(systemVersion, result.get())
    }

    @Test
    fun testGetSystemVersionNotFound() {
        `when`(systemVersionRepository.findById(1L)).thenReturn(Optional.empty())

        val result = serviceService.getSystemVersion(1L)
        assertTrue(result.isEmpty)
    }
}