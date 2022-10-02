package com.bidpoint.backend.user.service

import com.bidpoint.backend.role.entity.Role
import com.bidpoint.backend.role.repository.RoleRepository
import com.bidpoint.backend.user.entity.User
import com.bidpoint.backend.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import java.util.stream.Collectors

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

    def "test createUser"() {
        given:
        Role adminRole = new Role(null,"admin",new LinkedHashSet<>())
        Role sellerRole = new Role(null,"seller",new LinkedHashSet<>())
        Role bidderRole = new Role(null,"bidder",new LinkedHashSet<>())

        String password = "1234"
        String hashedPassword = "hashed1234"

        User user = new User(
                null,
                "",
                "",
                "",
                password,
                false,
                "",
                "",
                "",
                "",
                new LinkedHashSet<>(),
                new LinkedHashSet<>()
        )

        and:
        passwordEncoder.encode("1234") >> hashedPassword
        userRepository.save(user) >> user
        roleRepository.findByName("admin") >> adminRole
        roleRepository.findByName("seller") >> sellerRole
        roleRepository.findByName("bidder") >> bidderRole
        roleRepository.findByName("") >> null

        when:
        User returnedUser = userService.createUser(user, roles)

        then:
        returnedUser.getPassword() == hashedPassword
        returnedUser.getRoles().stream().map({ r -> r.getName() }).collect(Collectors.toList()) == roles || roles.reverse()
        returnedUser.isApproved() == approved

        where:
        testId | roles               | approved
        0      | ["seller", "admin"] | true
        1      | ["admin", "seller"] | true
        2      | ["seller"]          | false
        3      | ["admin"]           | true
        4      | []                  | false
        5      | ["bidder"]          | false
    }

    def "test approveUser"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
    }

    def "test isApproved"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
    }

    def "test addRoleToUser"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
    }

    def "test removeRoleFromUser"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
    }

    def "test getUser"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
    }

    def "test getUsers"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
    }

    def "test getUserByApproved"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
    }
}
