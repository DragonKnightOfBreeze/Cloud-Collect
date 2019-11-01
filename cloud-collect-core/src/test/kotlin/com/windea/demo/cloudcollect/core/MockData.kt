package com.windea.demo.cloudcollect.core

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.entity.Tag
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.service.*
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.context.*
import org.springframework.data.domain.*
import org.springframework.data.repository.*
import kotlin.random.*

@SpringBootTest
class MockData(
	@Autowired private val categoryRepository: CategoryRepository,
	@Autowired private val collectRepository: CollectRepository,
	@Autowired private val commentRepository: CommentRepository,
	@Autowired private val historyRepository: HistoryRepository,
	@Autowired private val noticeRepository: NoticeRepository,
	@Autowired private val tagRepository: TagRepository,
	@Autowired private val userRepository: UserRepository,
	@Autowired private val userService: UserService
) {
	private fun Int.randomId() = Random.nextInt(this) + 1L
	
	@Test //TESTED
	fun addMockData() {
		userRepository.save(User(
			id = 1,
			nickname = "微风的龙骑士",
			username = "Windea",
			email = "dk_breeze@qq.com",
			password = "BreezesLanding"
		))
		
		repeat(5) {
			userRepository.save(User(0, "UserUsername$it", "UserPassword$it", "UserEmail$it"))
		}
		repeat(20) {
			val user = userRepository.findByIdOrNull(5.randomId())!!
			categoryRepository.save(Category(0, "CategoryName$it", "CategorySummary$it", user = user))
		}
		repeat(20) {
			val user = userRepository.findByIdOrNull(5.randomId())!!
			tagRepository.save(Tag(0, "TagName$it", "TagSummary$it", user = user))
		}
		repeat(150) {
			val user = userRepository.findByIdOrNull(5.randomId())!!
			val category = categoryRepository.findByIdOrNull(20.randomId())!!
			val tag1 = tagRepository.findByIdOrNull(20.randomId())!!
			val tag2 = tagRepository.findByIdOrNull(20.randomId())!!
			val tag3 = tagRepository.findByIdOrNull(20.randomId())!!
			val tags = setOf(tag1, tag2, tag3).distinctBy { t -> t.name }.toSet()
			collectRepository.save(Collect(0, "CollectName$it", "CollectSummary$it", "CollectUrl$it", "CollectLogoUrl$it",
				category, tags, user = user))
		}
		repeat(50) {
			val collect = collectRepository.findByIdOrNull(5.randomId())!!
			val sponsorByUser = userRepository.findByIdOrNull(5.randomId())!!
			commentRepository.save(Comment(0, "CommentContent$it", collect, sponsorByUser))
		}
		repeat(30) {
			val collect = collectRepository.findByIdOrNull(it + 10L)!!
			val user = collect.user
			historyRepository.save(History(0, collect, user))
		}
		repeat(20) {
			val user = userRepository.findByIdOrNull(1)!!
			noticeRepository.save(Notice(0, "NoticeTitle$it", "NoticeType$it", user = user))
		}
	}
	
	@Test
	fun addCascadeMockData() {
		val user = userRepository.findByIdOrNull(1)!!
		val user1 = userRepository.findByIdOrNull(5)!!
		userService.focus(3, user)
		userService.focus(6, user)
		userService.focus(1, user1)
		
		val a = userRepository.findAllByFollowByUserListId(1, Pageable.unpaged())
		println(a.content)
		
		val b = userRepository.findAllByFollowToUserListId(1, Pageable.unpaged())
		println(b.content)
		
		//不能这样操作
		//user.praiseToCollectList += collectRepository.findAll().subList(0, 10)
		//user.followByUserList += userRepository.findAll().subList(5, 10)
		//user.followToUserList += userRepository.findAll().subList(10, 15)
		//userRepository.save(user)
	}
	
	@Test //TESTED
	fun addNewUser() {
		userRepository.save(User(0, "UserUsername???", "UserPassword???", "UserEmail???"))
	}
	
	@Test
	fun addNewTagToCollect() {
		val user = userRepository.findByIdOrNull(1)!!
		collectRepository.saveAndFlush(Collect(0, "CollectName???", "CollectSummary???", "???", "???",
			category = Category(0, "Category123132", user = user),
			tags = setOf(
				tagRepository.findByIdOrNull(2)!!
			),
			user = user
		))
	}
}
