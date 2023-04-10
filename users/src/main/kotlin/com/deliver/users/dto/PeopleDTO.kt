package com.deliver.users.dto

import com.deliver.users.domain.PeopleType
import java.util.*

data class PeopleDTO(
    var id: UUID,
    var login: String,
    var userName: String,
    var type: PeopleType,
    var active: Boolean
) {

}
