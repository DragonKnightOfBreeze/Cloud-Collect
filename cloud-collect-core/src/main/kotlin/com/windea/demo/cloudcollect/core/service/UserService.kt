package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.domain.view.*
import org.springframework.data.domain.*

/**用户的服务。登录、重置密码等功能委托给`UserDetailsService`。*/
interface UserService {
	/**通过用户名&密码登录用户。*/
	fun loginByUsernameAndPassword(view: UsernamePasswordLoginView): User
	
	/**通过邮箱注册用户。密码需要加密。*/
	fun registerByEmail(view: EmailRegisterView): User
	
	/**激活用户。将activated设为true。*/
	fun activate(user: User): User
	
	/**重置用户密码。密码需要加密。*/
	fun resetPassword(user: User, newPassword: String): User
	
	/**更新用户信息。不允许同时修改密码。*/
	fun update(id: Long, user: User): User
	
	/**根据id得到用户。*/
	fun findById(id: Long): User
	
	/**根据用户名得到用户。*/
	fun findByUsername(username: String): User
	
	/**根据邮箱得到用户。*/
	fun findByEmail(email: String): User
	
	/**得到随机用户。*/
	fun findByRandom(): User
	
	/**分页得到所有用户。*/
	fun findAll(pageable: Pageable): Page<User>
	
	/**根据昵称分页全局模糊查询用户。*/
	fun findAllByNicknameContains(nickname: String, pageable: Pageable): Page<User>
	
	/**根据身份分页全局查询用户。*/
	fun findAllByRole(role: Role, pageable: Pageable): Page<User>
	
	/**根据关注用户id分页查询用户。*/
	fun findAllByFollowToUserId(followToUserId: Long, pageable: Pageable): Page<User>
	
	/**根据关注用户id得到用户数量。*/
	fun countByFollowToUserId(followToUserId: Long): Long
	
	/**根据粉丝用户id分页查询用户。*/
	fun findAllByFollowByUserId(followByUserId: Long, pageable: Pageable): Page<User>
	
	/**根据粉丝用户id得到用户数量。*/
	fun countByFollowByUserId(followByUserId: Long): Long
	
	/**根据点赞收藏id分页查询用户。*/
	fun findAllByPraiseToCollectId(praiseToCollectId: Long, pageable: Pageable): Page<User>
	
	/**根据点赞收藏id得到用户数量。*/
	fun countByPraiseToCollectId(praiseToCollectId: Long): Long
	
	/**检查某一用户是否已存在。*/
	fun exists(user: User): Boolean
}
