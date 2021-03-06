@file:Suppress("DuplicatedCode", "UNUSED_VARIABLE", "ControlFlowWithEmptyBody", "ConstantConditionIf")

package com.windea.demo.cloudcollect.core.service.impl

import com.windea.demo.cloudcollect.core.GlobalConfig.requireActivation
import com.windea.demo.cloudcollect.core.GlobalConfig.sendEmail
import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.entity.User
import com.windea.demo.cloudcollect.core.domain.request.*
import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.enums.*
import com.windea.demo.cloudcollect.core.exceptions.*
import com.windea.demo.cloudcollect.core.extensions.*
import com.windea.demo.cloudcollect.core.properties.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import kotlinx.coroutines.*
import org.springframework.cache.annotation.*
import org.springframework.data.domain.*
import org.springframework.data.repository.*
import org.springframework.security.authentication.*
import org.springframework.security.core.*
import org.springframework.security.core.context.*
import org.springframework.security.core.userdetails.*
import org.springframework.security.crypto.password.*
import org.springframework.stereotype.*
import org.springframework.web.multipart.*
import java.io.*
import java.nio.file.*
import java.util.*
import javax.transaction.*
import kotlin.random.Random

@Service
@CacheConfig(cacheNames = ["user"], keyGenerator = "methodNameArgsKeyGenerator")
class UserServiceImpl(
	private val userRepository: UserRepository,
	private val collectRepository: CollectRepository,
	private val categoryRepository: CategoryRepository,
	private val noticeService: NoticeService,
	private val cacheService: CacheService,
	private val emailService: EmailService,
	private val passwordEncoder: PasswordEncoder,
	private val authenticationManager: AuthenticationManager,
	private val serializeProperties: SerializeProperties
) : UserService {
	override fun login(form: LoginForm): UserDetailsVo {
		//验证鉴定对象，然后将其转化为UserDetailsVo后返回
		//如果用户不存在或者用户名或密码错误，则再次抛出自定义的异常
		try {
			val authentication = UsernamePasswordAuthenticationToken(form.username, form.password)
			val validAuthentication = authenticationManager.authenticate(authentication)
			SecurityContextHolder.getContext().authentication = validAuthentication
			return validAuthentication.principal as UserDetailsVo
		} catch(e: UsernameNotFoundException) {
			throw UserNotFoundException()
		} catch(e: AuthenticationException) {
			throw InvalidUserException()
		}
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun register(user: User) {
		//将激活码存储到缓存中
		val activateCode = cacheService.setActivateCode(user.username)
		
		//密码需要进行加密
		user.password = passwordEncoder.encode(user.password)
		
		userRepository.save(user)
		
		//当配置为要求发送邮件时，发送邮件
		if(sendEmail)
			if(requireActivation) emailService.sendActivateEmail(user, activateCode)
			else emailService.sendHelloEmail(user)
	}
	
	//override fun logout() {
	//	SecurityContextHolder.clearContext()
	//}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun activate(username: String, activateCode: String) {
		//冲缓存中得到激活码，如果不匹配，则抛出异常
		val code = cacheService.getActivateCode(username)
		if(activateCode != code) throw InvalidAuthCodeException()
		
		val rawUser = userRepository.findByUsername(username) ?: throw UserNotFoundException()
		rawUser.activateStatus = true
		
		//当配置为要求发送邮件时，发送邮件
		if(sendEmail) emailService.sendHelloEmail(rawUser)
	}
	
	override fun forgotPassword(email: String) {
		//首先要判断用户是否存在
		val rawUser = userRepository.findByEmail(email) ?: throw UserNotFoundException()
		val username = rawUser.username
		//将验证码存储到缓存中
		val resetPasswordCode = cacheService.setResetPasswordCode(username)
		
		//当配置为要求发送邮件时，发送邮件
		if(sendEmail) emailService.sendResetPasswordEmail(rawUser, resetPasswordCode)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun resetPassword(form: ResetPasswordForm, resetPasswordCode: String) {
		//从缓存中得到激活码，如果不匹配，则抛出异常
		val code = cacheService.getResetPasswordCode(form.username)
		if(resetPasswordCode != code) throw InvalidAuthCodeException()
		
		val rawUser = userRepository.findByUsername(form.username) ?: throw UserNotFoundException()
		rawUser.password = passwordEncoder.encode(form.password) //密码需要加密
		
		//当配置为要求发送邮件时，发送邮件
		if(sendEmail) emailService.sendResetPasswordSuccessEmail(rawUser)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun modify(id: Long, user: User) {
		val rawUser = userRepository.findByIdOrNull(id) ?: throw NotFoundException()
		rawUser.apply {
			nickname = user.nickname
			introduce = user.introduce
			avatarUrl = user.avatarUrl
		}
	}
	
	override fun uploadAvatar(id: Long, multipartFile: MultipartFile): String {
		try {
			//存储用户头像到本地服务器目录
			val fileName = "avatar$id.${multipartFile.originalFilename?.substringAfterLast(".") ?: "png"}"
			val fileDirectory = serializeProperties.uploadPath
			Files.createDirectories(Path.of(fileDirectory)) //尝试创建所有父级目录
			val file = File(fileDirectory, fileName)
			multipartFile.transferTo(file) //拷贝文件内容
			
			//返回头像地址，通过添加随机的uuid查询字符串，实现前端图片动态刷新的功能
			return "${serializeProperties.uploadUrl}/$fileName?${UUID.randomUUID()}"
		} catch(e: Exception) {
			throw UploadAvatarException()
		}
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun follow(id: Long) {
		val currentUser = currentUser!!
		//用户不能关注自身
		if(id == currentUser.id) return
		
		val rawUser = userRepository.findByIdOrNull(id) ?: throw NotFoundException()
		rawUser.followByUsers += currentUser
		rawUser.addFollowNotice(currentUser)
	}
	
	@Transactional
	@CacheEvict(allEntries = true)
	override fun unfollow(id: Long) {
		val currentUser = currentUser!!
		val rawUser = userRepository.findByIdOrNull(id) ?: throw NotFoundException()
		rawUser.followByUsers -= currentUser
	}
	
	@Cacheable
	override fun findById(id: Long): User {
		return userRepository.findByIdOrNull(id)?.lateInit() ?: throw  NotFoundException()
	}
	
	@Cacheable
	override fun findByUsername(username: String): User {
		return userRepository.findByUsername(username)?.lateInit() ?: throw  NotFoundException()
	}
	
	@Cacheable
	override fun findByEmail(email: String): User {
		return userRepository.findByEmail(email)?.lateInit() ?: throw  NotFoundException()
	}
	
	override fun findByRandom(): User {
		val randomId = Random.nextLong(userRepository.count()) + 1
		return userRepository.findByIdOrNull(randomId)?.lateInit() ?: throw NotFoundException()
	}
	
	@Cacheable
	override fun findAll(pageable: Pageable): Page<User> {
		return userRepository.findAll(pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByNicknameContains(nickname: String, pageable: Pageable): Page<User> {
		return userRepository.findAllByNicknameContains(nickname, pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByUsernameContains(username: String, pageable: Pageable): Page<User> {
		return userRepository.findAllByUsernameContains(username, pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByEmailContains(email: String, pageable: Pageable): Page<User> {
		return userRepository.findAllByEmailContains(email, pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByRole(role: Role, pageable: Pageable): Page<User> {
		return userRepository.findAllByRole(role, pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByFollowToUserId(followToUserId: Long, pageable: Pageable): Page<User> {
		return userRepository.findAllByFollowToUsersId(followToUserId, pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByNicknameContainsAndFollowToUserId(nickname: String, followToUserId: Long, pageable: Pageable): Page<User> {
		return userRepository.findAllByNicknameContainsAndFollowToUsersId(nickname, followToUserId, pageable)
	}
	
	@Cacheable
	override fun findAllByFollowByUserId(followByUserId: Long, pageable: Pageable): Page<User> {
		return userRepository.findAllByFollowByUsersId(followByUserId, pageable).map { it.lateInit() }
	}
	
	@Cacheable
	override fun findAllByNicknameContainsAndFollowByUserId(nickname: String, followByUserId: Long, pageable: Pageable): Page<User> {
		return userRepository.findAllByNicknameContainsAndFollowByUsersId(nickname, followByUserId, pageable)
	}
	
	@Cacheable
	override fun findAllByPraiseToCollectId(praiseToCollectId: Long, pageable: Pageable): Page<User> {
		return userRepository.findAllByPraiseToCollectsId(praiseToCollectId, pageable).map { it.lateInit() }
	}
	
	override fun existsByUsernameOrEmail(username: String, email: String): Boolean {
		return userRepository.existsByUsernameOrEmail(username, email)
	}
	
	private fun User.lateInit() = this.apply {
		isFollowed = currentUser?.let { currentUser -> userRepository.existsByIdAndFollowByUsers(id, currentUser) }
		collectCount = collectRepository.countByUserId(id)
		categoryCount = categoryRepository.countByUserId(id)
		praiseToCollectCount = collectRepository.countByPraiseByUsersId(id)
		followToUserCount = userRepository.countByFollowByUsersId(id)
		followByUserCount = userRepository.countByFollowToUsersId(id)
	}
	
	//DONE 使用协程实现以下代码，注意这时当前用户已被清空
	
	//尝试为当前用户（以及他的所有粉丝用户）添加一条关注用户通知
	private fun User.addFollowNotice(currentUser: User) = GlobalScope.launch {
		val user = this@addFollowNotice
		val notice = Notice(
			title = "${currentUser.nickname}刚刚关注了一名用户",
			//language=HTML
			content = """
				<div>
				  <a href="/profile/${currentUser.id}">${currentUser.nickname}</a>
				  刚刚关注了一名用户：
				  <a href="/profile/${user.id}">${user.nickname}</a>
				</div>
			""".trimIndent(),
			type = NoticeType.ACCOUNT,
			user = currentUser
		)
		noticeService.create(notice)
		for(followByUser in currentUser.followByUsers) {
			noticeService.create(notice.copy(user = followByUser))
		}
	}
}
