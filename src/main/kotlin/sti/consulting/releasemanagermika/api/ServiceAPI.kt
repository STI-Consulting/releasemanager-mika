package sti.consulting.releasemanagermika.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import sti.consulting.releasemanagermika.model.Service
import sti.consulting.releasemanagermika.model.SystemVersion
import sti.consulting.releasemanagermika.repository.ServiceRepository
import sti.consulting.releasemanagermika.repository.SystemVersionRepository
import sti.consulting.releasemanagermika.service.ServiceService
import kotlin.jvm.optionals.getOrNull

@RestController
class ServiceAPI {

    @Autowired
    lateinit var serviceService: ServiceService;

    @PostMapping("/deploy")
    fun deploy(@RequestBody service: Service): ResponseEntity<Any> {
        val serviceId = serviceService.deployService(service)
        return if (serviceId != null) {
            ResponseEntity.status(HttpStatus.CREATED).body(serviceId)
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(mapOf("message" to "Failed to deploy service"))
        }
    }

    @GetMapping("/services")
    fun services(@RequestParam("systemVersion") version: Long): ResponseEntity<Any> {
        val systemVersion = serviceService.getSystemVersion(version)
        return if (systemVersion.isPresent) {
            ResponseEntity.ok(systemVersion.get())
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(mapOf("message" to "System version not found for version $version"))
        }
    }



}