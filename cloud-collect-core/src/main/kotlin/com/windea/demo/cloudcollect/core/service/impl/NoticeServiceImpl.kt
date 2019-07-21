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
@CacheConfig(cacheNames = ["notice"])
open class NoticeServiceImpl(
	private val repository: NoticeRepository,
	private val userRepository: UserRepository
) : NoticeService {
	@Transactional
	@CacheEvict(allEntries = true)
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
	@CacheEvict(allEntries = true)
	override fun delete(id: Long) {
		repository.deleteById(id)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun read(id: Long): Notice {
		val notice = findById(id)
		notice.isRead = true
		return repository.save(notice)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findById(id: Long): Notice {
		return repository.findById(id).orElseThrow { NotFoundException() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAll(pageable: Pageable): Page<Notice> {
		return repository.findAll(pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<Notice> {
		return repository.findAllByUserId(userId, pageable)
	}
	
	override fun countByUserId(userId: Long): Long {
		return repository.countByUserId(userId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserIdAndRead(userId: Long, isRead: Boolean, pageable: Pageable): Page<Notice> {
		return repository.findAllByUserIdAndRead(userId, isRead, pageable)
	}
	
	override fun countByUserIdAndRead(userId: Long, isRead: Boolean): Long {
		return repository.countByUserIdAndRead(userId, isRead)
	}
}
