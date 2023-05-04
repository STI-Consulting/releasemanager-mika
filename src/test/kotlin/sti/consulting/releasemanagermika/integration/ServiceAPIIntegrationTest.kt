package sti.consulting.releasemanagermika.integration

import com.fasterxml.jackson.databind.ObjectMapper
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
    fun `should_create_new_deployment_and_return_its_id`() {
        val service = Service(name = "Test Service", version = 1L)

        mockMvc.post("/deploy") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(service)
        }.andExpect {
            status { isCreated() }
            content {json("1") }
        }
    }

    @Test
    fun `should_return_list_of_services_for_given_system_version`() {
        val service = Service(name = "Test Service", version = 1L)

        mockMvc.post("/deploy") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(service)
        }.andExpect {
            status { isCreated() }
            content { json("1") }
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
