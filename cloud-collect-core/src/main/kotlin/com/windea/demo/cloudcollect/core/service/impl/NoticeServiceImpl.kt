package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.exception.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import javax.transaction.*

@Service
open class NoticeServiceImpl(
	private val repository: NoticeRepository,
	private val userRepository: UserRepository
) : NoticeService {
	@Transactional
	override fun create(notice: Notice, user: User): Notice {
		notice.user = user
		return repository.save(notice)
	}
	
	override fun sendToAll(notice: Notice) {
		val userList = userRepository.findAll()
		for(user in userList) {
			val newNotice = Notice()
			newNotice.user = user
			newNotice.title = notice.title
			newNotice.content = notice.content
			newNotice.type = notice.type
			repository.save(notice)
		}
	}
	
	@Transactional
	override fun delete(id: Long) {
		repository.deleteById(id)
	}
	
	@Transactional
	override fun read(id: Long): Notice {
		val notice = get(id)
		notice.readStatus = true
		return repository.save(notice)
	}
	
	@Cacheable("notice")
	override fun get(id: Long): Notice {
		return repository.findById(id).orElseThrow { NotFoundException() }
	}
	
	@Cacheable("noticePage")
	override fun findAll(pageable: Pageable): Page<Notice> {
		return repository.findAll(pageable)
	}
	
	@Cacheable("noticePage.byUser")
	override fun findByUser(userId: Long, pageable: Pageable): Page<Notice> {
		return repository.findByUserId(userId, pageable)
	}
	
	@Cacheable("noticePage.byUserAndRead")
	override fun findByUserAndReadStatus(userId: Long, readStatus: Boolean, pageable: Pageable): Page<Notice> {
		return repository.findByUserIdAndReadStatus(userId, readStatus, pageable)
	}
}
