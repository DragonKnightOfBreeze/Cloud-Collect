package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import javax.transaction.*

@Service
@CacheConfig(cacheNames = ["history"])
class HistoryServiceImpl(
	private val historyRepository: HistoryRepository
) : HistoryService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun create(history: History) {
		//相对于最后浏览记录，不要重复添加浏览记录
		val lastHistory = historyRepository.findFirstByUserIdOrderByIdDesc(history.user.id)
		if(lastHistory != null && history.collect.id == lastHistory.collect.id) return
		
		//首先要删除之前的重复的浏览记录
		if(lastHistory != null) historyRepository.deleteByCollectIdAndUserId(history.collect.id, history.user.id)
		
		historyRepository.save(history)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun deleteById(id: Long) {
		historyRepository.deleteById(id)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun deleteAllByUserId(userId: Long) {
		historyRepository.deleteAllByUserId(userId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<History> {
		return historyRepository.findAllByUserIdOrderByIdDesc(userId, pageable)
	}
}
