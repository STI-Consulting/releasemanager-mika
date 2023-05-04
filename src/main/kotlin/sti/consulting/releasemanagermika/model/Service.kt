package sti.consulting.releasemanagermika.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Service(
        @Id @GeneratedValue val id: Long? = null,
        val name: String,
        val version: Long
) {
    override fun equals(other: Any?): Boolean {
        return name == (other as Service).name && version == other.version;
    }

    override fun hashCode(): Int {
        return name.hashCode() + version.hashCode();
    }
}
