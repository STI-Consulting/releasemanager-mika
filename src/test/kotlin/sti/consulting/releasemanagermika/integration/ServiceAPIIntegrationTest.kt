package sti.consulting.releasemanagermika.integration

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.get
import sti.consulting.releasemanagermika.model.Service

@SpringBootTest
@AutoConfigureMockMvc
class ServiceAPIIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun testDeployIntegration() {
        val service = Service(name = "Test Service", version = 1L)

        mockMvc.post("/deploy") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(service)
        }.andExpect {
            status { isCreated() }
            jsonPath("$.id") { value(1L) }
        }
    }

    @Test
    fun testServicesIntegration() {
        val service = Service(name = "Test Service", version = 1L)

        mockMvc.post("/deploy") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(service)
        }.andExpect {
            status { isCreated() }
            jsonPath("$.id") { value(1L) }
        }

        mockMvc.get("/services?systemVersion=1") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.services[0].name") { value(service.name) }
            jsonPath("$.services[0].version") { value(service.version.toInt()) }
        }
    }
}
