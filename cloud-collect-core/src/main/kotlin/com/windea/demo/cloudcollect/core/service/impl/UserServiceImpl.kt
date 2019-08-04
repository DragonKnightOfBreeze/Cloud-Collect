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
import org.springframework.security.authentication.*
import org.springframework.security.core.context.*
import org.springframework.security.crypto.password.*
import org.springframework.stereotype.*
import java.util.*
import javax.transaction.*

@Service
@CacheConfig(cacheNames = ["user"])
open class UserServiceImpl(
	private val repository: UserRepository,
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
		val user = User()
		user.nickname = view.nickname
		user.username = view.username
		user.email = view.email
		user.password = passwordEncoder.encode(view.password)
		//得到随机验证码
		user.activateCode = UUID.randomUUID().toString()
		val result = repository.save(user)
		//成功注册后，发送激活邮件
		emailService.sendActivateEmail(user)
		return result
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun forgotPassword(username: String): User {
		val user = findByUsername(username)
		user.resetPasswordCode = UUID.randomUUID().toString()
		val result = repository.save(user)
		//发送重置密码邮件
		emailService.sendResetPasswordEmail(user)
		return result
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun activate(username: String, activateCode: String): Boolean {
		val rawUser = findByUsername(username)
		//如果激活码不匹配，则直接返回false，否则清空激活码，然后激活用户，并发送欢迎邮件
		if(rawUser.activateCode != activateCode) return false
		
		rawUser.activateCode = null
		rawUser.isActivated = true
		repository.save(rawUser)
		emailService.sendHelloEmail(rawUser)
		return true
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun resetPassword(username: String, password: String, resetPasswordCode: String): Boolean {
		val rawUser = findByUsername(username)
		//如果识别码不匹配，则直接返回false，否则清空识别码，接着更改为新的加密后的密码
		if(rawUser.resetPasswordCode != resetPasswordCode) return false
		
		rawUser.resetPasswordCode = null
		rawUser.password = passwordEncoder.encode(password)
		repository.save(rawUser)
		emailService.sendResetPasswordSuccessEmail(rawUser)
		return true
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modify(id: Long, user: User): User {
		val rawUser = findById(id)
		rawUser.nickname = user.nickname
		rawUser.introduce = user.introduce
		rawUser.avatarUrl = user.avatarUrl
		rawUser.backgroundUrl = user.backgroundUrl
		return repository.save(rawUser)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findById(id: Long): User {
		return repository.findById(id).orElseThrow { NotFoundException() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findByUsername(username: String): User {
		return repository.findByUsername(username).orElseThrow { NotFoundException() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findByEmail(email: String): User {
		return repository.findByEmail(email).orElseThrow { NotFoundException() }
	}
	
	override fun findByRandom(): User {
		val count = repository.count()
		val randomId = RandomExtension.range(1, count)
		return repository.findById(randomId).orElseThrow { NotFoundException() }
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAll(pageable: Pageable): Page<User> {
		return repository.findAll(pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByNicknameContains(nickname: String, pageable: Pageable): Page<User> {
		return repository.findAllByNicknameContains(nickname, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByRole(role: Role, pageable: Pageable): Page<User> {
		return repository.findAllByRole(role, pageable)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByFollowToUserId(followToUserId: Long, pageable: Pageable): Page<User> {
		return repository.findAllByFollowToUserId(followToUserId, pageable)
	}
	
	override fun countByFollowToUserId(followToUserId: Long): Long {
		return repository.countByFollowToUserId(followToUserId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByFollowByUserId(followByUserId: Long, pageable: Pageable): Page<User> {
		return repository.findAllByFollowByUserId(followByUserId, pageable)
	}
	
	override fun countByFollowByUserId(followByUserId: Long): Long {
		return repository.countByFollowByUserId(followByUserId)
	}
	
	@Cacheable(key = "methodName + args")
	override fun findAllByPraiseToCollectId(praiseToCollectId: Long, pageable: Pageable): Page<User> {
		return repository.findAllByPraiseToCollectId(praiseToCollectId, pageable)
	}
	
	override fun countByPraiseToCollectId(praiseToCollectId: Long): Long {
		return repository.countByPraiseToCollectId(praiseToCollectId)
	}
	
	override fun exists(user: User): Boolean {
		val username = user.username
		val email = user.email
		return repository.existsByUsernameOrEmail(username, email)
	}
}
