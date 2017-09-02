package org.mboyz.holidayplanner.user.persistence

import javax.persistence.*

@Entity
@Table(name = "privilege")
data class PrivilegeEntity
@JvmOverloads
constructor(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "privilege_id", updatable = false, nullable = false)
        var id: Long = 0,
        var name: String = "",
        @ManyToMany(mappedBy = "privileges")
        var roles: MutableSet<RoleEntity> = mutableSetOf())
{
    override fun toString(): String {
        return "PrivilegeEntity(name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PrivilegeEntity

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}