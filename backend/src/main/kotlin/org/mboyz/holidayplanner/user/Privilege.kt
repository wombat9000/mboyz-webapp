package org.mboyz.holidayplanner.user

import javax.persistence.*

@Entity
data class Privilege
@JvmOverloads
constructor(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "privilege_id", updatable = false, nullable = false)
        var id: Long = 0,
        var name: String = "",
        @ManyToMany(mappedBy = "privileges")
        var roles: MutableSet<Role> = mutableSetOf())
{
    override fun toString(): String {
        return "Privilege(name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Privilege

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}