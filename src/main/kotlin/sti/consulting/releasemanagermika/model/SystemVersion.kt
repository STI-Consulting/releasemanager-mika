package sti.consulting.releasemanagermika.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.springframework.beans.factory.annotation.Autowired

@Entity
@Table(name="system_version")
class SystemVersion() {

    constructor(services: List<Service>) : this() {
        this.services = services;
    }

    constructor(services: List<Service>?, version: Long) : this() {
        this.version = version;
    }

    @Id @GeneratedValue
    var version: Long? = null;

    @ManyToMany
    var services: List<Service>? = null;
}
