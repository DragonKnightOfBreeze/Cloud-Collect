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
	override fun activate(username: String, activateCode: String): User? {
		var result: User? = null
		repository.findByUsername(username).ifPresent { user ->
			if(!user.isActivated && user.activateCode == activateCode) {
				user.isActivated = true
				user.activateCode = null
				result = repository.save(user)
				//成功激活后，发送欢迎邮件
				emailService.sendHelloEmail(user)
			}
		}
		return result
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun resetPassword(username: String, newPassword: String): User? {
		var result: User? = null
		repository.findByUsername(username).ifPresent { user ->
			//重置密码
			user.password = passwordEncoder.encode(newPassword)
			result = repository.save(user)
		}
		return result
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun update(id: Long, user: User): User {
		val rawUser = findById(id)
		rawUser.nickname = user.nickname
		rawUser.introduce = user.introduce
		rawUser.avatarUrl = user.avatarUrl
		rawUser.backgroundUrl = user.backgroundUrl
		return repository.save(user)
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
