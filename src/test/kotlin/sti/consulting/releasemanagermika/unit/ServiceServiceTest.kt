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
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class ServiceServiceTest {

    @InjectMocks
    private lateinit var serviceDeployment: ServiceService

    @Mock
    private lateinit var serviceRepository: ServiceRepository

    @Mock
    private lateinit var systemVersionRepository: SystemVersionRepository

    private lateinit var testService: Service
    private lateinit var testSystemVersion: SystemVersion

    @BeforeEach
    fun setUp() {
        testService = Service(null, "test-service", 1L)
        testSystemVersion = SystemVersion(listOf(testService))
        testSystemVersion.version = 1L
    }

    @Test
    fun `test getSystemVersion when version exists`() {
        // Arrange
        `when`(testSystemVersion.version?.let { systemVersionRepository.findById(it) }).thenReturn(Optional.of(testSystemVersion))

        // Act
        val result = testSystemVersion.version?.let { serviceDeployment.getSystemVersion(it) }

        // Assert
        assertEquals(Optional.of(testSystemVersion), result)
    }

    @Test
    fun `test getSystemVersion when version does not exist`() {
        // Arrange
        `when`(testSystemVersion.version?.let { systemVersionRepository.findById(it) }).thenReturn(Optional.empty())

        // Act
        val result = testSystemVersion.version?.let { serviceDeployment.getSystemVersion(it) }

        // Assert
        assertEquals(Optional.empty<SystemVersion>(), result)
    }

}

