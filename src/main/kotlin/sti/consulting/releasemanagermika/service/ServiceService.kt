package sti.consulting.releasemanagermika.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import sti.consulting.releasemanagermika.model.SystemVersion
import sti.consulting.releasemanagermika.repository.ServiceRepository
import sti.consulting.releasemanagermika.repository.SystemVersionRepository
import java.util.*

@Service
class ServiceService(val serviceRepository: ServiceRepository, val systemVersionRepository: SystemVersionRepository) {

    fun deployService(service: sti.consulting.releasemanagermika.model.Service): Long? {
        serviceRepository.findLatestVersionOfServiceByName(service.name)?.let {
            if (it == service) {
                return systemVersionRepository.findLatestVersion()?.version
            }
        }

        serviceRepository.save(service)

        return systemVersionRepository.save(SystemVersion(serviceRepository.findLatestVersionsOfAllServices())).version;
    }


    fun getSystemVersion(version: Long): Optional<SystemVersion> {
        return systemVersionRepository.findById(version);
    }

}