package org.mboyz.holidayplanner.user

import javax.persistence.*

@Entity
data class Role
@JvmOverloads
constructor(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "role_id", updatable = false, nullable = false)
        var id: Long = 0,
        var name: String = "",
        @ManyToMany(mappedBy = "roles")
        var users: MutableSet<User> = mutableSetOf(),

        @ManyToMany
        @JoinTable(
                name = "role_privilege",
                joinColumns = arrayOf(JoinColumn(name = "role_id", referencedColumnName = "role_id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id")))
        var privileges: MutableSet<Privilege> = mutableSetOf()

) {
    override fun toString(): String {
        return "Role(name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Role

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}