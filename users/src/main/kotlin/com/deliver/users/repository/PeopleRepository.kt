package com.deliver.users.repository

import com.deliver.users.domain.People
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PeopleRepository : JpaRepository<People, UUID> {
}