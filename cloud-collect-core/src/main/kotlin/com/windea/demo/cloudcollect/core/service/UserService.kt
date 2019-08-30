package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.domain.request.*
import com.windea.demo.cloudcollect.core.domain.response.*
import org.springframework.data.domain.*

/**用户的服务。登录、重置密码等功能委托给`UserDetailsService`。*/
interface UserService {
	/**通过邮箱注册用户。密码需要加密。*/
	fun registerByEmail(form: EmailRegisterForm): User
	
	/**激活用户。*/
	fun activate(username: String, activateCode: String): User?
	
	/**通过用户名&密码登录用户。*/
	fun loginByUsernameAndPassword(form: UsernamePasswordLoginForm): JwtUserDetails
	
	/**忘记用户密码，发送重置密码邮件。*/
	fun forgotPassword(username: String): User
	
	/**重置用户密码。密码需要加密。*/
	fun resetPassword(form: ResetPasswordForm, resetPasswordCode: String): User?
	
	/**更新用户信息。密码需要加密。*/
	fun modify(id: Long, user: User): User
	
	/**根据id得到用户。*/
	fun findById(id: Long): User
	
	/**根据用户名得到用户。*/
	fun findByUsername(username: String): User
	
	/**根据邮箱得到用户。*/
	fun findByEmail(email: String): User
	
	/**得到所有用户。*/
	fun findAll(pageable: Pageable): Page<User>
	
	/**根据昵称全局模糊查询用户。*/
	fun findAllByNicknameContains(nickname: String, pageable: Pageable): Page<User>
	
	/**根据身份全局查询用户。*/
	fun findAllByRole(role: Role, pageable: Pageable): Page<User>
	
	/**根据关注用户id查询用户。*/
	fun findAllByFollowToUserId(followToUserId: Long, pageable: Pageable): Page<User>
	
	/**根据粉丝用户id查询用户。*/
	fun findAllByFollowByUserId(followByUserId: Long, pageable: Pageable): Page<User>
	
	/**根据点赞收藏id查询用户。*/
	fun findAllByPraiseToCollectId(praiseToCollectId: Long, pageable: Pageable): Page<User>
	
	/**检查某一用户是否已存在。*/
	fun existsByUsernameOrEmail(username: String, email: String): Boolean
	
	
	/**得到该用户的所有关注用户。*/
	fun getFollowToUserPage(id: Long, pageable: Pageable): Page<User>
	
	/**得到该用户的所有粉丝用户。*/
	fun getFollowByUserPage(id: Long, pageable: Pageable): Page<User>
	
	/**得到该用户的所有收藏。*/
	fun getCollectPage(id: Long, pageable: Pageable): Page<Collect>
	
	/**得到该用户的所有评论。*/
	fun getCommentPage(id: Long, pageable: Pageable): Page<Comment>
	
	/**得到该用户的所有通知。*/
	fun getNoticePage(id: Long, pageable: Pageable): Page<Notice>
}
