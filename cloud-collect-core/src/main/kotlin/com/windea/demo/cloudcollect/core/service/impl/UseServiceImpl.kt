package com.windea.demo.cloudcollect.core.service.impl

import com.windea.commons.kotlin.extension.*
import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.domain.model.*
import com.windea.demo.cloudcollect.core.domain.view.*
import com.windea.demo.cloudcollect.core.exception.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.security.authentication.*
import org.springframework.security.core.context.*
import org.springframework.security.crypto.password.*
import org.springframework.stereotype.*
import javax.transaction.*

@Service
open class UseServiceImpl(
	private val repository: UserRepository,
	private val collectRepository: CollectRepository,
	private val categoryRepository: CollectCategoryRepository,
	private val noticeRepository: NoticeRepository,
	private val emailService: EmailService,
	private val passwordEncoder: PasswordEncoder,
	private val authenticationManager: AuthenticationManager
) : UserService {
	override fun loginByUsernameAndPassword(view: UsernamePasswordLoginView): User {
		val authentication = UsernamePasswordAuthenticationToken(view.username, view.password)
		val validAuthentication = authenticationManager.authenticate(authentication)
		SecurityContextHolder.getContext().authentication = validAuthentication
		
		return (validAuthentication.principal as JwtUserDetails).delegateUser
	}
	
	@Transactional
	override fun registerByEmail(view: EmailRegisterView): User {
		val user = User()
		user.nickname = view.nickname
		user.username = view.username
		user.email = view.email
		user.password = passwordEncoder.encode(view.password)
		val result = repository.save(user)
		
		emailService.sendActivateEmail()
		return result
	}
	
	@Transactional
	override fun activate(user: User): User {
		user.activateStatus = true
		val result = repository.save(user)
		
		emailService.sendHelloEmail()
		return result
	}
	
	@Transactional
	override fun resetPassword(user: User, newPassword: String): User {
		user.password = passwordEncoder.encode(newPassword)
		return repository.save(user)
	}
	
	@Transactional
	override fun update(id: Long, user: User): User {
		val rawUser = get(id)
		rawUser.nickname = user.nickname
		rawUser.introduce = user.introduce
		rawUser.avatarUrl = user.avatarUrl
		rawUser.backgroundUrl = user.backgroundUrl
		return repository.save(user)
	}
	
	@Cacheable("user")
	override fun get(id: Long): User {
		return repository.findById(id).orElseThrow { NotFoundException() }
	}
	
	override fun getByRandom(): User {
		val count = repository.count()
		val randomId = RandomExtension.range(1, count)
		return repository.findById(randomId).orElseThrow { NotFoundException() }
	}
	
	@Cacheable("user.followToUserPage")
	override fun getFollowToUserPage(id: Long, pageable: Pageable): Page<User> {
		return repository.findByFollowByUserId(id, pageable)
	}
	
	@Cacheable("user.followToUserCount")
	override fun getFollowToUserCount(id: Long): Long {
		return repository.countByFollowByUserId(id)
	}
	
	@Cacheable("user.followByUserPage")
	override fun getFollowByUserPage(id: Long, pageable: Pageable): Page<User> {
		return repository.findByFollowToUserId(id, pageable)
	}
	
	@Cacheable("user.followByUserCount")
	override fun getFollowByUserCount(id: Long): Long {
		return repository.countByFollowToUserId(id)
	}
	
	@Cacheable("user.collectPage")
	override fun getCollectPage(id: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findByUserIdAndDeleteStatus(id, false, pageable)
	}
	
	@Cacheable("user.collectCount")
	override fun getCollectCount(id: Long): Long {
		return collectRepository.countByUserIdAndDeleteStatus(id, false)
	}
	
	@Cacheable("user.collectCategoryPage")
	override fun getCollectCategoryPage(id: Long, pageable: Pageable): Page<CollectCategory> {
		return categoryRepository.findByUserId(id, pageable)
	}
	
	@Cacheable("user.collectCategoryCount")
	override fun getCollectCategoryCount(id: Long): Long {
		return categoryRepository.countByUserId(id)
	}
	
	@Cacheable("user.noticePage")
	override fun getNoticePage(id: Long, pageable: Pageable): Page<Notice> {
		return noticeRepository.findByUserId(id, pageable)
	}
	
	@Cacheable("collect.noticeCount")
	override fun getNoticeCount(id: Long): Long {
		return noticeRepository.countByUserId(id)
	}
	
	@Cacheable("collectPage")
	override fun findAll(pageable: Pageable): Page<User> {
		return repository.findAll(pageable)
	}
	
	@Cacheable("collectPage.byNickname")
	override fun findByNickname(nickname: String, pageable: Pageable): Page<User> {
		return repository.findByNicknameContains(nickname, pageable)
	}
	
	@Cacheable("collectPage.byRole")
	override fun findByRole(role: Role, pageable: Pageable): Page<User> {
		return repository.findByRole(role, pageable)
	}
	
	override fun exists(user: User): Boolean {
		val username = user.username
		val email = user.email
		return repository.existsByUsernameOrEmail(username, email)
	}
}
