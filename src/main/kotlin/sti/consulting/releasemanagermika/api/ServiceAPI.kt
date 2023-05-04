package sti.consulting.releasemanagermika.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import sti.consulting.releasemanagermika.model.Service
import sti.consulting.releasemanagermika.model.SystemVersion
import sti.consulting.releasemanagermika.repository.ServiceRepository
import sti.consulting.releasemanagermika.repository.SystemVersionRepository

@RestController
class ServiceAPI {

    @Autowired
    lateinit var serviceRepository: ServiceRepository;

    @Autowired
    lateinit var systemVersionRepository: SystemVersionRepository;

    @PostMapping("/deploy")
    fun deploy(@RequestBody service: Service): Long? {
        val storedService = serviceRepository.findLatestVersionOfServiceByName(service.name);
        return if (storedService == service) {
            systemVersionRepository.findLatestVersion()?.version;
        } else {
            serviceRepository.save(service);

            val services = serviceRepository.findLatestVersionsOfAllServices();
            val systemVersion = SystemVersion(services);

            systemVersionRepository.save(systemVersion);

            systemVersionRepository.findLatestVersion()?.version;
        }
    }

    @GetMapping("/services")
    fun services(@RequestParam("systemVersion") version: Long): SystemVersion {
        return systemVersionRepository.findById(version).get();
    }


}