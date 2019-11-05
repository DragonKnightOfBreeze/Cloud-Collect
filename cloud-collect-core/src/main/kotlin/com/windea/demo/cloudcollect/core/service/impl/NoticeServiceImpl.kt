package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import javax.transaction.*

@Service
@CacheConfig(cacheNames = ["notice"])
class NoticeServiceImpl(
	private val noticeRepository: NoticeRepository
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
	override fun deleteById(id: Long) {
		noticeRepository.deleteById(id)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun deleteAllByUserId(userId: Long) {
		noticeRepository.deleteAllByUserId(userId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<Notice> {
		return noticeRepository.findAllByUserIdOrderByIdDesc(userId, pageable)
	}
}
