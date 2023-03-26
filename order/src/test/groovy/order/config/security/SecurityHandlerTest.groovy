package order.config.security

import com.deliver.order.dto.OrderDTO
import com.deliver.order.security.SecurityHandler
import com.deliver.order.security.validators.TrackedValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.security.test.context.support.WithMockUser
import spock.lang.Specification

@SpringBootTest(classes = [SecurityHandler, TrackedValidator],
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SecurityHandlerTest extends Specification {

    @Autowired
    private SecurityHandler handler

    @WithMockUser(username = "user")
    def 'is "#username" has permission - #permission'() {
        given:
        def order = new OrderDTO(createdBy: username)

        expect:
        handler.hasPermission(ResponseEntity.ok(order)) == permission

        where:
        username | permission
        "user"   | true
        "anon"   | false
    }
}
