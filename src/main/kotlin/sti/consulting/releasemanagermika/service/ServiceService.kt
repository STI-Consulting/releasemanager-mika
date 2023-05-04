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

        if (serviceAlreadyDeployed(service)) {
            return systemVersionRepository.findLatestVersion()?.version
        }

        serviceRepository.save(service)

        return getLatestDeployedVersionOfService();
    }


    fun getSystemVersion(version: Long): Optional<SystemVersion> {
        return systemVersionRepository.findById(version);
    }

    private fun getLatestDeployedVersionOfService(): Long? {
        return systemVersionRepository.save(SystemVersion(serviceRepository.findAll()
                .groupBy { it.name }.mapValues { it.value.last() }.values.toList())).version
    }

    private fun serviceAlreadyDeployed(service: sti.consulting.releasemanagermika.model.Service): Boolean {
        // Get the latest deployed service
        // Not max version because lower version could be deployed because higher version might be buggy
        val latestService = serviceRepository.findTopByOrderByIdDesc()
        return latestService != null && latestService == service
    }

}