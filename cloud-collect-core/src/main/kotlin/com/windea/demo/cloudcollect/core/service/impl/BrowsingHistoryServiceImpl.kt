package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import javax.transaction.*

@Service
class BrowsingHistoryServiceImpl(
	private val historyRepository: BrowsingHistoryRepository
) : BrowsingHistoryService {
	@Transactional
	override fun create(history: BrowsingHistory, user: User): BrowsingHistory {
		//首先要删除之前的相同收藏的浏览记录
		historyRepository.deleteByCollectIdAndUserId(history.collect.id, user.id)
		
		val newHistory = history.copy(
			user = user
		)
		return historyRepository.save(newHistory)
	}
	
	@Transactional
	override fun deleteById(id: Long) {
		historyRepository.deleteById(id)
	}
	
	@Transactional
	override fun deleteAllByUserId(userId: Long) {
		historyRepository.deleteAllByUserId(userId)
	}
	
	override fun findAllByUserId(userId: Long, pageable: Pageable): Page<BrowsingHistory> {
		return historyRepository.findAllByUserIdOrderByIdDesc(userId, pageable)
	}
}
