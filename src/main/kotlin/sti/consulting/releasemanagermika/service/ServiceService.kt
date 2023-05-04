package sti.consulting.releasemanagermika.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import sti.consulting.releasemanagermika.model.SystemVersion
import sti.consulting.releasemanagermika.repository.ServiceRepository
import sti.consulting.releasemanagermika.repository.SystemVersionRepository
import java.util.*

@Service
class ServiceService {

    @Autowired
    lateinit var serviceRepository: ServiceRepository;

    @Autowired
    lateinit var systemVersionRepository: SystemVersionRepository;

    fun deployService(service: sti.consulting.releasemanagermika.model.Service): Long? {
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

    fun getSystemVersion(version: Long): Optional<SystemVersion> {
        return systemVersionRepository.findById(version);
    }

}