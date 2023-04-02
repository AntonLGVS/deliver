package com.deliver.order.web.controller

import com.deliver.order.config.security.SecurityConfig
import com.deliver.order.dto.OrderDTO
import com.deliver.order.security.SecurityHandler
import com.deliver.order.security.validators.TrackedValidator
import com.deliver.order.service.order.OrderService
import com.deliver.order.web.API
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@WebMvcTest(controllers = [OrderController])
@Import([SecurityConfig, SecurityHandler, TrackedValidator])
class OrderEntityControllerTest extends Specification {

    @Autowired
    protected MockMvc mvc

    @SpringBean
    OrderService orderService = Mock()

    def '#username (roles #roles) got order with status #status'() {
        given:
        def url = "${API.V1}/order/${UUID.randomUUID()}"
        def userAuth = user(username).roles(roles)

        and:
        orderService.getOrder(_ as UUID) >> Optional.of(new OrderDTO(createdBy: "user"))

        when:
        def res = mvc.perform(get(url).with(userAuth)).andReturn().response

        then:
        res.status == status

        where:
        username | roles   | status
        "user"   | "USER"  | HttpStatus.OK.value()
        "admin"  | "ADMIN" | HttpStatus.OK.value()
        "anon"   | "ANON"  | HttpStatus.FORBIDDEN.value()
    }
}
