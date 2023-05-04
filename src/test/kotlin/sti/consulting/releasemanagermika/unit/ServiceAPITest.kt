package sti.consulting.releasemanagermika.unit

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import sti.consulting.releasemanagermika.api.ServiceAPI
import sti.consulting.releasemanagermika.model.Service
import sti.consulting.releasemanagermika.model.SystemVersion
import sti.consulting.releasemanagermika.service.ServiceService
import java.util.Optional

class ServiceAPITest {

    @Mock
    lateinit var serviceService: ServiceService

    @InjectMocks
    lateinit var serviceAPI: ServiceAPI

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    //This test case checks whether the deploy() method of ServiceAPI returns a ResponseEntity with a CREATED status and the correct service ID when a new service is deployed successfully.
    @Test
    fun testDeploy() {
        val service = Service(name = "Test Service", version = 1L)
        `when`(serviceService.deployService(service)).thenReturn(1L)

        val response = serviceAPI.deploy(service)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(1L, response.body)
    }

    @Test
    fun testDeployFailure() {
        val service = Service(name = "Test Service", version = 1L)
        `when`(serviceService.deployService(service)).thenReturn(null)

        val response = serviceAPI.deploy(service)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
    }

    @Test
    fun testServices() {
        val systemVersion = SystemVersion(listOf(Service(name = "Test Service", version = 1L)))
        `when`(serviceService.getSystemVersion(1L)).thenReturn(Optional.of(systemVersion))

        val response = serviceAPI.services(1L)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(systemVersion, response.body)
    }

    @Test
    fun testServicesNotFound() {
        `when`(serviceService.getSystemVersion(1L)).thenReturn(Optional.empty())

        val response = serviceAPI.services(1L)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }
}
