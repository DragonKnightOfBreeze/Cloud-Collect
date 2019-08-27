package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.domain.model.*
import com.windea.demo.cloudcollect.core.domain.view.*
import com.windea.demo.cloudcollect.core.exception.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.utility.common.extensions.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.data.repository.*
import org.springframework.security.authentication.*
import org.springframework.security.core.context.*
import org.springframework.security.crypto.password.*
import org.springframework.stereotype.*
import java.util.*
import javax.transaction.*

@Service
@CacheConfig(cacheNames = ["user"])
open class UserServiceImpl(
	private val userRepository: UserRepository,
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
	@CacheEvict(allEntries = true)
	override fun registerByEmail(view: EmailRegisterView): User {
		val savedUser = User(
			nickname = view.nickname,
			username = view.username,
			email = view.email,
			password = passwordEncoder.encode(view.password)
		)
		savedUser.activateCode = UUID.randomUUID().toString()
		val result = userRepository.save(savedUser)
		
		//成功注册后，发送激活邮件
		emailService.sendActivateEmail(savedUser)
		return result
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun forgotPassword(username: String): User {
		val savedUser = findByUsername(username)
		//设置随机重置密码验证码
		savedUser.resetPasswordCode = UUID.randomUUID().toString()
		val result = userRepository.save(savedUser)
		
		//发送重置密码邮件
		emailService.sendResetPasswordEmail(savedUser)
		return result
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun activate(username: String, activateCode: String): Boolean {
		val savedUser = findByUsername(username)
		//如果激活码不匹配，则直接返回
		if(savedUser.activateCode != activateCode) return false
		
		savedUser.activateCode = null
		savedUser.isActivated = true
		userRepository.save(savedUser)
		
		//发送欢迎邮件
		emailService.sendHelloEmail(savedUser)
		return true
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun resetPassword(username: String, password: String, resetPasswordCode: String): Boolean {
		val savedUser = findByUsername(username)
		//如果重置密码验证码不匹配，则直接返回
		if(savedUser.resetPasswordCode != resetPasswordCode) return false
		
		savedUser.resetPasswordCode = null
		savedUser.password = passwordEncoder.encode(password)
		userRepository.save(savedUser)
		
		//发送重置密码成功邮件
		emailService.sendResetPasswordSuccessEmail(savedUser)
		return true
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modify(id: Long, user: User): User {
		val savedUser = this.findById(id)
		savedUser.nickname = user.nickname
		savedUser.introduce = user.introduce
		savedUser.avatarUrl = user.avatarUrl
		savedUser.backgroundUrl = user.backgroundUrl
		return userRepository.save(savedUser)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findById(id: Long): User {
		return userRepository.findByIdOrNull(id) ?: throw  NotFoundException()
	}
	
	@Cacheable(key = "methodName + args")
	override fun findByUsername(username: String): User {
		return userRepository.findByUsername(username) ?: throw  NotFoundException()
	}
	
	@Cacheable(key = "methodName + args")
	override fun findByEmail(email: String): User {
		return userRepository.findByEmail(email) ?: throw  NotFoundException()
	}
	
	override fun findByRandom(): User {
		val randomId = RandomExtension.range(1, userRepository.count())
		return findById(randomId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAll(pageable: Pageable): Page<User> {
		return userRepository.findAll(pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNicknameContains(nickname: String, pageable: Pageable): Page<User> {
		return userRepository.findAllByNicknameContains(nickname, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByRole(role: Role, pageable: Pageable): Page<User> {
		return userRepository.findAllByRole(role, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByFollowToUserId(followToUserId: Long, pageable: Pageable): Page<User> {
		return userRepository.findAllByFollowToUserId(followToUserId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun countByFollowToUserId(followToUserId: Long): Long {
		return userRepository.countByFollowToUserId(followToUserId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByFollowByUserId(followByUserId: Long, pageable: Pageable): Page<User> {
		return userRepository.findAllByFollowByUserId(followByUserId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun countByFollowByUserId(followByUserId: Long): Long {
		return userRepository.countByFollowByUserId(followByUserId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByPraiseToCollectId(praiseToCollectId: Long, pageable: Pageable): Page<User> {
		return userRepository.findAllByPraiseToCollectId(praiseToCollectId, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun countByPraiseToCollectId(praiseToCollectId: Long): Long {
		return userRepository.countByPraiseToCollectId(praiseToCollectId)
	}
	
	override fun exists(user: User): Boolean {
		val username = user.username
		val email = user.email
		return userRepository.existsByUsernameOrEmail(username, email)
	}
}
