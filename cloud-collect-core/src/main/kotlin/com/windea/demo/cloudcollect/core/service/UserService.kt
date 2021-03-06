package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.request.*
import com.windea.demo.cloudcollect.core.domain.response.*
import com.windea.demo.cloudcollect.core.enums.*
import org.springframework.data.domain.*
import org.springframework.web.multipart.*

interface UserService {
	/**登录用户。*/
	fun login(form: LoginForm): UserDetailsVo
	
	/**注册用户。*/
	fun register(user: User)
	
	///**注销用户。*/
	//fun logout()
	
	/**激活用户。*/
	fun activate(username: String, activateCode: String)
	
	/**忘记用户密码。*/
	fun forgotPassword(email: String)
	
	/**重置用户密码。*/
	fun resetPassword(form: ResetPasswordForm, resetPasswordCode: String)
	
	/**更新用户信息。*/
	fun modify(id: Long, user: User)
	
	/**上传用户头像。*/
	fun uploadAvatar(id: Long, multipartFile: MultipartFile): String
	
	/**关注某一用户。*/
	fun follow(id: Long)
	
	/**取消关注某一用户。*/
	fun unfollow(id: Long)
	
	/**根据id得到用户。*/
	fun findById(id: Long): User
	
	/**根据用户名得到用户。*/
	fun findByUsername(username: String): User
	
	/**根据邮箱得到用户。*/
	fun findByEmail(email: String): User
	
	/**得到随机用户。*/
	fun findByRandom(): User
	
	/**得到所有用户。*/
	fun findAll(pageable: Pageable): Page<User>
	
	/**根据昵称全局模糊查询用户。*/
	fun findAllByNicknameContains(nickname: String, pageable: Pageable): Page<User>
	
	/**根据用户名全局模糊查询用户。*/
	fun findAllByUsernameContains(username: String, pageable: Pageable): Page<User>
	
	/**根据邮箱全局模糊查询用户。*/
	fun findAllByEmailContains(email: String, pageable: Pageable): Page<User>
	
	/**根据身份全局查询用户。*/
	fun findAllByRole(role: Role, pageable: Pageable): Page<User>
	
	/**根据关注用户id查询用户。*/
	fun findAllByFollowToUserId(followToUserId: Long, pageable: Pageable): Page<User>
	
	/**根据昵称和关注用户id模糊查询用户。*/
	fun findAllByNicknameContainsAndFollowToUserId(nickname: String, followToUserId: Long, pageable: Pageable): Page<User>
	
	/**根据粉丝用户id查询用户。*/
	fun findAllByFollowByUserId(followByUserId: Long, pageable: Pageable): Page<User>
	
	/**根据昵称和粉丝用户id模糊查询用户。*/
	fun findAllByNicknameContainsAndFollowByUserId(nickname: String, followByUserId: Long, pageable: Pageable): Page<User>
	
	/**根据点赞收藏id查询用户。*/
	fun findAllByPraiseToCollectId(praiseToCollectId: Long, pageable: Pageable): Page<User>
	
	/**检查某一用户是否已存在。*/
	fun existsByUsernameOrEmail(username: String, email: String): Boolean
}
