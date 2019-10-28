package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.exceptions.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.data.repository.*
import org.springframework.stereotype.*
import javax.transaction.*

@Service
@CacheConfig(cacheNames = ["notice"])
class NoticeServiceImpl(
	private val noticeRepository: NoticeRepository,
	private val userRepository: UserRepository
) : NoticeService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun create(notice: Notice, user: User) {
		val newNotice = notice.copy(
			user = user
		)
		noticeRepository.save(newNotice)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun read(notice: Notice) {
		notice.readStatus = true
		noticeRepository.save(notice)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun deleteById(id: Long) {
		noticeRepository.deleteById(id)
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
	override fun findAllByUserIdAndRead(userId: Long, readStatus: Boolean, pageable: Pageable): Page<Notice> {
		return noticeRepository.findAllByUserIdAndReadStatus(userId, readStatus, pageable)
	}
}
