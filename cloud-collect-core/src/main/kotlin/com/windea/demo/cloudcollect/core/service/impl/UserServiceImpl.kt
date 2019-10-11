@file:Suppress("DuplicatedCode", "UNUSED_VARIABLE")

package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.request.*
import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.enums.*
import com.windea.demo.cloudcollect.core.exceptions.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.data.repository.*
import org.springframework.security.authentication.*
import org.springframework.security.core.context.*
import org.springframework.security.crypto.password.*
import org.springframework.stereotype.*
import javax.transaction.*
import kotlin.random.*

@Service
@CacheConfig(cacheNames = ["user"])
class UserServiceImpl(
	private val userRepository: UserRepository,
	private val collectRepository: CollectRepository,
	private val commentRepository: CommentRepository,
	private val noticeRepository: NoticeRepository,
	private val cacheService: CacheService,
	private val passwordEncoder: PasswordEncoder,
	private val authenticationManager: AuthenticationManager
) : UserService {
	override fun login(form: LoginForm): UserDetailsVo {
		//验证鉴定对象，然后将其转化为UserDetailsVo后返回
		val authentication = UsernamePasswordAuthenticationToken(form.username, form.password)
		val validAuthentication = authenticationManager.authenticate(authentication)
		SecurityContextHolder.getContext().authentication = validAuthentication
		return (validAuthentication.principal as UserDetailsVo)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun register(user: User): User {
		//将激活码存储到缓存中
		cacheService.setActivateCode(user.username)
		
		val newUser = user.copy(
			password = passwordEncoder.encode(user.password) //NOTE 密码需要加密
		)
		return userRepository.save(newUser)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun activate(username: String, activateCode: String) {
		//冲缓存中得到激活码，如果不匹配，则抛出异常
		val code = cacheService.getActivateCode(username)
		if(activateCode != code) throw IncorrectAuthCodeException()
		
		val user = userRepository.findByUsername(username) ?: throw UserNotFoundException()
		user.activateStatus = true
		userRepository.save(user)
	}
	
	override fun forgotPassword(username: String) {
		//首先要判断用户是否存在
		if(!userRepository.existsByUsername(username)) throw UserNotFoundException()
		
		//将验证码存储到缓存中
		cacheService.setResetPasswordCode(username)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun resetPassword(form: ResetPasswordForm, resetPasswordCode: String) {
		//从缓存中得到激活码，如果不匹配，则抛出异常
		val code = cacheService.getResetPasswordCode(form.username)
		if(resetPasswordCode != code) throw IncorrectAuthCodeException()
		
		val user = userRepository.findByUsername(form.username) ?: throw UserNotFoundException()
		user.password = passwordEncoder.encode(form.password) //NOTE 密码需要加密
		userRepository.save(user)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modify(id: Long, user: User) {
		user.password = passwordEncoder.encode(user.password) //NOTE 密码需要加密
		userRepository.save(user)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findById(id: Long): User {
		return userRepository.findByIdOrNull(id)?.lateInit() ?: throw  NotFoundException()
	}
	
	@Cacheable(key = "methodName + args")
	override fun findByUsername(username: String): User {
		return userRepository.findByUsername(username)?.lateInit() ?: throw  NotFoundException()
	}
	
	@Cacheable(key = "methodName + args")
	override fun findByEmail(email: String): User {
		return userRepository.findByEmail(email)?.lateInit() ?: throw  NotFoundException()
	}
	
	override fun findByRandom(): User {
		val randomId = Random.nextLong(userRepository.count()) + 1
		return userRepository.findByIdOrNull(randomId)?.lateInit() ?: throw NotFoundException()
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAll(pageable: Pageable): Page<User> {
		return userRepository.findAll(pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNicknameContains(nickname: String, pageable: Pageable): Page<User> {
		return userRepository.findAllByNicknameContains(nickname, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByRole(role: Role, pageable: Pageable): Page<User> {
		return userRepository.findAllByRole(role, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByFollowToUserId(followToUserId: Long, pageable: Pageable): Page<User> {
		return userRepository.findAllByFollowToUserId(followToUserId, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByFollowByUserId(followByUserId: Long, pageable: Pageable): Page<User> {
		return userRepository.findAllByFollowByUserId(followByUserId, pageable).map { it.lateInit() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByPraiseToCollectId(praiseToCollectId: Long, pageable: Pageable): Page<User> {
		return userRepository.findAllByPraiseToCollectId(praiseToCollectId, pageable).map { it.lateInit() }
	}
	
	override fun existsByUsernameOrEmail(username: String, email: String): Boolean {
		return userRepository.existsByUsernameOrEmail(username, email)
	}
	
	private fun User.lateInit() = this.apply {
		followToUserCount = userRepository.countByFollowByUserId(id!!)
		followByUserCount = userRepository.countByFollowToUserId(id!!)
		collectCount = collectRepository.countByUserId(id!!)
		commentCount = commentRepository.countBySponsorByUserId(id!!)
		noticeCount = noticeRepository.countByUserId(id!!)
	}
	
	
	@Cacheable(key = "methodName + args")
	override fun getFollowToUserPage(id: Long, pageable: Pageable): Page<User> {
		return userRepository.findAllByFollowByUserId(id, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun getFollowByUserPage(id: Long, pageable: Pageable): Page<User> {
		return userRepository.findAllByFollowToUserId(id, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun getCollectPage(id: Long, pageable: Pageable): Page<Collect> {
		return collectRepository.findAllByUserId(id, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun getCommentPage(id: Long, pageable: Pageable): Page<Comment> {
		return commentRepository.findAllBySponsorByUserId(id, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun getNoticePage(id: Long, pageable: Pageable): Page<Notice> {
		return noticeRepository.findAllByUserId(id, pageable)
	}
}
