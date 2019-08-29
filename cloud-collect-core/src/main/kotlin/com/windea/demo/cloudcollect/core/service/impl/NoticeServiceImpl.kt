package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.exception.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.data.repository.*
import org.springframework.stereotype.*
import javax.transaction.*

@Service
@CacheConfig(cacheNames = ["notice"])
open class NoticeServiceImpl(
	private val noticeRepository: NoticeRepository,
	private val userRepository: UserRepository
) : NoticeService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun create(notice: Notice, user: User): Notice {
		notice.user = user
		return noticeRepository.save(notice)
	}
	
	override fun sendToAll(notice: Notice) {
		val userList = userRepository.findAll()
		for(user in userList) {
			val savedNotice = Notice(
				title = notice.title,
				content = notice.content,
				type = notice.type,
				user = user
			)
			noticeRepository.save(savedNotice)
		}
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun delete(id: Long) {
		noticeRepository.deleteById(id)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun read(id: Long): Notice {
		val savedNotice = findById(id)
		savedNotice.isRead = true
		return noticeRepository.save(savedNotice)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findById(id: Long): Notice {
		return noticeRepository.findByIdOrNull(id) ?: throw NotFoundException()
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAll(pageable: Pageable): Page<Notice> {
		return noticeRepository.findAll(pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<Notice> {
		return noticeRepository.findAllByUserId(userId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserIdAndRead(userId: Long, isRead: Boolean, pageable: Pageable): Page<Notice> {
		return noticeRepository.findAllByUserIdAndRead(userId, isRead, pageable)
	}
}
