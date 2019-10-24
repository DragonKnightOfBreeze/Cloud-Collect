package com.windea.demo.cloudcollect.core.repository

import com.windea.demo.cloudcollect.core.domain.*
import org.springframework.data.jpa.repository.*

interface FooRepository : JpaRepository<Foo, Long>
