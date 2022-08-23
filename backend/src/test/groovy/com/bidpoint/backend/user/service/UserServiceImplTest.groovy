package com.bidpoint.backend.user.service

import com.bidpoint.backend.item.entity.Bid
import com.bidpoint.backend.role.entity.Role
import com.bidpoint.backend.role.repository.RoleRepository
import com.bidpoint.backend.user.entity.User
import com.bidpoint.backend.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import spock.lang.Unroll

class UserServiceImplTest extends Specification {
    UserRepository userRepository
    RoleRepository roleRepository
    PasswordEncoder passwordEncoder
    UserService userService
    def setup(){
        userRepository = Mock(UserRepository)
        roleRepository = Mock(RoleRepository)
        passwordEncoder = Mock(PasswordEncoder)
        userService = new UserServiceImpl(
                userRepository,
                roleRepository,
                passwordEncoder
        )
    }

    def "LoadUserByUsername"() {
    }

    @Unroll
    def "CreateUser with #roles and should be approved=#approved"() {
        given:
        User user = new User(
                null,
                "",
                "",
                "",
                "1234",
                false,
                "",
                "",
                "",
                "",
                new LinkedHashSet<>(),
                new LinkedHashSet<>()
        )
        and:
        passwordEncoder.encode("1234") >> "7888"
        userRepository.save(user) >> user

        when:
        User returnedUser = userService.createUser(user, roles)

        then:
        returnedUser.getPassword() == "7888"
        returnedUser.isApproved() == approved

        where:
        roles << [["admin","visitor"], ["visitor"], ["admin"], [""]]
        approved << [true, false, true, false]

    }

    def "ApproveUser"() {
    }

    def "IsApproved"() {
    }

    def "AddRoleToUser"() {
    }

    def "RemoveRoleFromUser"() {
    }

    def "GetUser"() {
    }

    def "GetUsers"() {
    }

    def "GetUserByApproved"() {
    }
}
