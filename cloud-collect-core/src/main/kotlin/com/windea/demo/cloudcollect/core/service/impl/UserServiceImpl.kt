package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.domain.request.*
import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.exception.*
import com.windea.demo.cloudcollect.core.properties.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.utility.common.extensions.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.data.redis.core.*
import org.springframework.data.repository.*
import org.springframework.security.authentication.*
import org.springframework.security.core.context.*
import org.springframework.security.crypto.password.*
import org.springframework.stereotype.*
import java.util.*
import javax.transaction.*

@Service
@CacheConfig(cacheNames = ["user"])
class UserServiceImpl(
	private val userRepository: UserRepository,
	private val collectRepository: CollectRepository,
	private val commentRepository: CommentRepository,
	private val noticeRepository: NoticeRepository,
	private val passwordEncoder: PasswordEncoder,
	private val authenticationManager: AuthenticationManager,
	private val redisTemplate: StringRedisTemplate,
	private val redisProperties: RedisProperties,
	private val emailService: EmailService,
	private val configProperties: ConfigProperties
) : UserService {
	@Transactional
	@CacheEvict(allEntries = true)
	override fun registerByEmail(form: EmailRegisterForm): User {
		//将激活码存储到缓存中，同时也暂时存储到User对象中
		val activateCodeKey = "${redisProperties.activateCodePrefix}${form.username}"
		val activateCodeValue = UUID.randomUUID().toString()
		redisTemplate.opsForValue().set(activateCodeKey, activateCodeValue, redisProperties.expiration)
		
		val savedUser = User(
			nickname = form.nickname,
			username = form.username,
			email = form.email,
			password = passwordEncoder.encode(form.password)
		)
		return userRepository.save(savedUser).also {
			//如果需要发送邮件，则发送激活邮件
			if(configProperties.requireActivate && configProperties.sendEmail) emailService.sendActivateEmail(it, activateCodeValue)
			
			//如果不要求激活，则立即激活当前用户
			if(!configProperties.requireActivate) activate(it.username, activateCodeValue)
		}
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun activate(username: String, activateCode: String): User? {
		//冲缓存中得到激活码，如果不匹配，则直接返回null
		val activateCodeKey = "${redisProperties.activateCodePrefix}${username}"
		val activateCodeValue = redisTemplate.opsForValue()[activateCodeKey]
		if(activateCode != activateCodeValue) return null
		
		//进行数据库操作
		val savedUser = userRepository.findByUsername(username) ?: throw UserNotFoundException()
		savedUser.activateStatus = true
		return userRepository.save(savedUser).also {
			//如果需要发送邮件，则发送欢迎邮件
			if(configProperties.sendEmail) emailService.sendHelloEmail(it)
		}
	}
	
	override fun loginByUsernameAndPassword(form: UsernamePasswordLoginForm): JwtUserDetails {
		val authentication = UsernamePasswordAuthenticationToken(form.username, form.password)
		val validAuthentication = authenticationManager.authenticate(authentication)
		SecurityContextHolder.getContext().authentication = validAuthentication
		
		return (validAuthentication.principal as JwtUserDetails)
	}
	
	override fun forgotPassword(username: String): User {
		//首先要判断用户是否存在
		val savedUser = userRepository.findByUsername(username) ?: throw UserNotFoundException()
		
		//将验证码存储到缓存中，同时也暂时存储到User对象中
		val resetPasswordCodeKey = "${redisProperties.resetPasswordPrefix}${username}"
		val resetPasswordCodeValue = UUID.randomUUID().toString()
		redisTemplate.opsForValue().set(resetPasswordCodeKey, resetPasswordCodeValue, redisProperties.expiration)
		
		return userRepository.save(savedUser).also {
			//如果需要发送邮件，则发送重置密码邮件
			if(configProperties.sendEmail) emailService.sendResetPasswordEmail(it, resetPasswordCodeValue)
		}
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun resetPassword(form: ResetPasswordForm, resetPasswordCode: String): User? {
		//从缓存中得到激活码，如果不匹配，则直接返回null
		val resetPasswordCodeKey = "${redisProperties.resetPasswordPrefix}${form.username}"
		val resetPasswordCodeValue = redisTemplate.opsForValue()[resetPasswordCodeKey]
		if(resetPasswordCode != resetPasswordCodeValue) return null
		
		//进行数据库操作
		val savedUser = userRepository.findByUsername(form.username) ?: throw UserNotFoundException()
		savedUser.password = passwordEncoder.encode(form.password)
		return userRepository.save(savedUser).also {
			//如果需要发送邮件，则发送重置密码成功邮件
			if(configProperties.sendEmail) emailService.sendResetPasswordSuccessEmail(it)
		}
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modify(id: Long, user: User): User {
		val savedUser = userRepository.findByIdOrNull(id) ?: throw NotFoundException()
		savedUser.password = passwordEncoder.encode(user.password)
		savedUser.nickname = user.nickname
		savedUser.introduce = user.introduce
		savedUser.avatarUrl = user.avatarUrl
		savedUser.backgroundUrl = user.backgroundUrl
		return userRepository.save(savedUser)
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
		val randomId = RandomExtension.range(1, userRepository.count())
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
