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
import org.springframework.security.crypto.bcrypt.*
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
	@Autowired private val userService: UserService,
	@Autowired private val collectService: CollectService,
	@Autowired private val passwordEncoder: BCryptPasswordEncoder
) {
	private fun Int.randomId() = Random.nextInt(this) + 1L
	private fun String.encode() = passwordEncoder.encode(this)
	
	@Test
	fun encodePassword() {
		val users = userRepository.findAll()
		users.forEach {
			it.password = it.password.encode()
		}
		userRepository.saveAll(users)
	}
	
	@Test //TESTED
	fun addMockData() {
		userRepository.save(User(
			id = 1,
			nickname = "微风的龙骑士",
			username = "Windea",
			email = "dk_breeze@qq.com",
			password = "BreezesLanding".encode()
		))
		
		repeat(5) {
			userRepository.save(User(0, "UserUsername$it", "UserPassword$it".encode(), "UserEmail$it"))
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
	
	@Test //TESTED
	fun addCascadeMockData() {
		val user = userRepository.findByIdOrNull(1)!!
		val user1 = userRepository.findByIdOrNull(5)!!
		userService.follow(3)
		userService.follow(5)
		userService.follow(6)
		userService.follow(1)
		userService.follow(3)
		val a = userRepository.findAllByFollowByUsersId(1, Pageable.unpaged())
		println(a.content)
		val b = userRepository.findAllByFollowToUsersId(1, Pageable.unpaged())
		println(b.content)
	}
	
	@Test //TESTED
	fun addCascadeMockData2() {
		collectService.praise(1)
		collectService.praise(2)
		collectService.praise(3)
		val result = collectService.findAllByPraiseByUserId(1, Pageable.unpaged()).content
		println(result)
	}
	
	@Test
	fun findAllCascadeMockData() {
		userService.findAllByFollowByUserId(1, Pageable.unpaged()).also { println(it.content) }
		userService.findAllByFollowToUserId(1, Pageable.unpaged()).also { println(it.content) }
	}
	
	@Test //TESTED
	fun addNewUser() {
		userRepository.save(User(0, "UserUsername???", "UserPassword???", "UserEmail???"))
	}
	
	@Test
	fun addSampleCollects() {
		val user = userService.findById(1)
		collectService.create(Collect(
			name = "百度一下",
			summary = "百度一下，你就知道",
			url = "https://www.baidu.com",
			user = user
		))
		collectService.create(Collect(
			name = "哔哩哔哩",
			summary = "哔哩哔哩 (゜-゜)つロ 干杯~-bilibili",
			url = "https://www.bilibili.com",
			user = user
		))
	}
	
	@Test
	fun addNewTagToCollect() {
		val user = userRepository.findByIdOrNull(1)!!
		collectRepository.saveAndFlush(Collect(0, "CollectName???", "CollectSummary???", "???", "???",
			category = categoryRepository.findByIdOrNull(3)!!,
			tags = setOf(
				tagRepository.findByIdOrNull(2)!!
			),
			user = user
		))
	}
}
