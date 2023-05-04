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

    @Test
    fun `should_return_created_status_and_id_when_deploying_service`() {
        val service = Service(name = "Test Service", version = 1L)
        `when`(serviceService.deployService(service)).thenReturn(1L)

        val response = serviceAPI.deploy(service)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(1L, response.body)
    }

    @Test
    fun `should_return_internal_server_error_when_deploy_service_fails`() {
        val service = Service(name = "Test Service", version = 1L)
        `when`(serviceService.deployService(service)).thenReturn(null)

        val response = serviceAPI.deploy(service)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
    }

    @Test
    fun `should_return_system_version_when_given_system_version_id_exists`() {
        val systemVersion = SystemVersion(listOf(Service(name = "Test Service", version = 1L)))
        `when`(serviceService.getSystemVersion(1L)).thenReturn(Optional.of(systemVersion))

        val response = serviceAPI.services(1L)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(systemVersion, response.body)
    }

    @Test
    fun `should_return_not_found_status_when_given_system_version_id_does_not_exist`() {
        `when`(serviceService.getSystemVersion(1L)).thenReturn(Optional.empty())

        val response = serviceAPI.services(1L)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }
}
