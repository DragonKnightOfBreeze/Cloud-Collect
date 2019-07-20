package com.windea.demo.cloudcollect.core.service

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.domain.view.*
import org.springframework.data.domain.*

/** 用户的服务。登录、重置密码等功能委托给`UserDetailsService`。*/
interface UserService {
	/** 通过用户名&密码登录用户。*/
	fun loginByUsernameAndPassword(view: UsernamePasswordLoginView): User
	
	/** 通过邮箱注册用户。密码需要加密。*/
	fun registerByEmail(view: EmailRegisterView): User
	
	/** 激活用户。将activated设为true。*/
	fun activate(user: User): User
	
	/** 重置用户密码。密码需要加密。*/
	fun resetPassword(user: User, newPassword: String): User
	
	/** 更新用户信息。不允许同时修改密码。*/
	fun update(id: Long, user: User): User
	
	/** 得到用户信息。*/
	fun get(id: Long): User
	
	/** 得到随机用户。*/
	fun getByRandom(): User
	
	/** 分页得到某一用户的所有关注用户。*/
	fun getFollowToUserPage(id: Long, pageable: Pageable): Page<User>
	
	/** 得到某一用户的关注用户数量。*/
	fun getFollowToUserCount(id: Long): Long
	
	/** 分页得到某一用户的所有粉丝用户。*/
	fun getFollowByUserPage(id: Long, pageable: Pageable): Page<User>
	
	/** 得到某一用户的粉丝用户数量。*/
	fun getFollowByUserCount(id: Long): Long
	
	/** 分页得到某一用户的所有收藏。*/
	fun getCollectPage(id: Long, pageable: Pageable): Page<Collect>
	
	/** 得到某一用户的收藏数量。*/
	fun getCollectCount(id: Long): Long
	
	/** 得到某一用户的所有收藏分类。*/
	fun getCollectCategoryPage(id: Long, pageable: Pageable): Page<CollectCategory>
	
	/** 得到某一用户的所有收藏分类数量。*/
	fun getCollectCategoryCount(id: Long): Long
	
	/** 分页得到某一用户的所有通知。*/
	fun getNoticePage(id: Long, pageable: Pageable): Page<Notice>
	
	/** 得到某一用户的通知数量。*/
	fun getNoticeCount(id: Long): Long
	
	/** 分页得到所有用户。*/
	fun findAll(pageable: Pageable): Page<User>
	
	/** 根据昵称分页全局模糊查询用户。*/
	fun findByNickname(nickname: String, pageable: Pageable): Page<User>
	
	/** 根据身份分页全局查询用户。*/
	fun findByRole(role: Role, pageable: Pageable): Page<User>
	
	/** 检查某一用户是否已存在。*/
	fun exists(user: User): Boolean
}
