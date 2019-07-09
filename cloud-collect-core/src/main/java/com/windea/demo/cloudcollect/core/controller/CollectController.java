package com.windea.demo.cloudcollect.core.controller;

import com.windea.demo.cloudcollect.core.domain.entity.*;
import com.windea.demo.cloudcollect.core.domain.enums.CollectType;
import com.windea.demo.cloudcollect.core.domain.enums.DataType;
import com.windea.demo.cloudcollect.core.domain.model.JwtUserDetails;
import com.windea.demo.cloudcollect.core.service.CollectService;
import com.windea.demo.cloudcollect.core.service.ImportExportService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.Set;

/**
 * 收藏的控制器。
 */
@Api("收藏")
@RequestMapping("/collect")
@RestController
@CrossOrigin
public class CollectController {
	private final CollectService service;
	private final ImportExportService ieService;

	public CollectController(CollectService service, ImportExportService ieService) {
		this.service = service;
		this.ieService = ieService;
	}


	@ApiOperation("创建自己的收藏。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "collect", value = "新的收藏", required = true)
	})
	@PostMapping("/create")
	public Collect create(@RequestBody @Valid Collect collect, BindingResult bindingResult, Principal principal) {
		var user = ((JwtUserDetails) principal).getDelegateUser();
		return service.create(collect, user);
	}

	@ApiOperation("从别人的收藏创建自己的收藏。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "collect", value = "别人的收藏", required = true)
	})
	@PostMapping("/createFrom")
	public Collect createFrom(@RequestBody @Valid Collect collect, BindingResult bindingResult, Principal principal) {
		var user = ((JwtUserDetails) principal).getDelegateUser();
		return service.createFrom(collect, user);
	}

	@ApiOperation("删除自己的收藏。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@DeleteMapping("/{id}")
	@PreAuthorize("hasPermission(#id,'Collect','delete')")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

	@ApiOperation("修改自己的收藏。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		@ApiImplicitParam(name = "collect", value = "修改后的收藏", required = true)
	})
	@PutMapping("/{id}")
	@PreAuthorize("hasPermission(#id,'Collect','write')")
	public Collect modify(@PathVariable Long id, @RequestBody @Valid Collect collect, BindingResult bindingResult) {
		return service.modify(id, collect);
	}

	@ApiOperation("修改自己的收藏的分类。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		@ApiImplicitParam(name = "category", value = "修改后的收藏的分类", required = true)
	})
	@PutMapping("/{id}/category")
	@PreAuthorize("hasPermission(#id,'Collect','write')")
	public Collect modifyCategory(@PathVariable Long id, @RequestBody CollectCategory category) {
		return service.modifyCategory(id, category);
	}

	@ApiOperation("修改自己的收藏的标签。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		@ApiImplicitParam(name = "tags", value = "修改后的收藏的标签", required = true)
	})
	@PutMapping("/{id}/tags")
	@PreAuthorize("hasPermission(#id,'Collect','write')")
	public Collect modifyTags(@PathVariable Long id, @RequestBody Set<CollectTag> tags) {
		return service.modifyTags(id, tags);
	}

	@ApiOperation("修改自己的收藏的类型。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		@ApiImplicitParam(name = "type", value = "修改后的收藏的类型", required = true)
	})
	@PutMapping("/{id}/type")
	@PreAuthorize("hasPermission(#id,'CollectCategory','write')")
	public Collect modifyType(@PathVariable Long id, @RequestBody CollectType type) {
		return service.modifyType(id, type);
	}

	@ApiOperation("点赞某一收藏。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@PutMapping("/{id}/praise")
	public Collect praise(@PathVariable Long id, Principal principal) {
		var user = ((JwtUserDetails) principal).getDelegateUser();
		return service.praise(id, user);
	}

	@ApiOperation("得到某一收藏。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@GetMapping("/{id}")
	public Collect get(@PathVariable Long id) {
		return service.get(id);
	}

	@ApiOperation("分页得到某一收藏的所有点赞用户。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/{id}/praiseByUserPage")
	public Page<User> getPraiseByUserPage(@PathVariable Long id, @RequestParam Pageable pageable) {
		return service.getPraiseByUserPage(id, pageable);
	}

	@ApiOperation("得到某一收藏的点赞用户数量。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@GetMapping("/{id}/praiseByUserCount")
	public Long getPraiseByUserCount(@PathVariable Long id) {
		return service.getPraiseByUserCount(id);
	}

	@ApiOperation("分页得到某一收藏的所有评论。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path"),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/{id}/commentPage")
	public Page<Comment> getCommentPage(@PathVariable Long id, @RequestParam Pageable pageable) {
		return service.getCommentPage(id, pageable);
	}

	@ApiOperation("得到某一收藏的评论数量。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
	})
	@GetMapping("/{id}/commentCount")
	public Long getCommentCount(@PathVariable Long id) {
		return service.getCommentCount(id);
	}

	@ApiOperation("分页得到所有收藏。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findAll")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Page<Collect> findAll(@RequestParam Pageable pageable) {
		return service.findAll(pageable);
	}

	@ApiOperation("分页查询某一用户的所有已删除/未删除收藏。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		@ApiImplicitParam(name = "deleteStatus", value = "删除状态", required = true),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findByUserAndDeleteStatus")
	public Page<Collect> findByUserAndDeleteStatus(@RequestParam Long userId, @RequestParam Boolean deleteStatus,
		@RequestParam Pageable pageable) {
		return service.findByUserAndDeleteStatus(userId, deleteStatus, pageable);
	}

	@ApiOperation("根据名字分页模糊查询某一用户的所有收藏。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		@ApiImplicitParam(name = "name", value = "名字", required = true),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findByUserAndName")
	public Page<Collect> findByUserAndName(@RequestParam Long userId, @RequestParam String name,
		@RequestParam Pageable pageable) {
		return service.findByUserAndName(userId, name, pageable);
	}

	@ApiOperation("根据分类分页查询某一用户的所有收藏。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "categoryId", value = "分类的id", required = true),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findByUserAndCategory")
	public Page<Collect> findByUserAndCategory(@RequestParam Long categoryId, @RequestParam Pageable pageable) {
		return service.findByUserAndCategory(categoryId, pageable);
	}

	@ApiOperation("根据标签分页查询某一用户的所有收藏。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tagId", value = "标签的id", required = true),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findByUserAndCategory")
	public Page<Collect> findByUserAndTag(@RequestParam Long tagId, @RequestParam Pageable pageable) {
		return service.findByUserAndTag(tagId, pageable);
	}

	@ApiOperation("根据类型分页查询某一用户的所有收藏。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "用户的id", required = true),
		@ApiImplicitParam(name = "type", value = "收藏的类型", required = true),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findByUserAndType")
	public Page<Collect> findByUserAndType(@RequestParam Long userId, @RequestParam CollectType type,
		@RequestParam Pageable pageable) {
		return service.findByUserAndType(userId, type, pageable);
	}

	@ApiOperation("根据名字分页全局查询所有收藏。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name", value = "名字", required = true),
		@ApiImplicitParam(name = "pageable", value = "分页和排序", required = true)
	})
	@GetMapping("/findByName")
	public Page<Collect> findByName(@RequestParam String name, @RequestParam Pageable pageable) {
		return service.findByName(name, pageable);
	}

	@ApiOperation("从指定格式的文件导入收藏。例如：Xml、Json、Yaml。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "type", value = "数据类型", required = true),
		@ApiImplicitParam(name = "file", value = "上传的文件", required = true)
	})
	@PostMapping("/import")
	public void importData(@RequestParam(defaultValue = "YAML") DataType type, MultipartFile file, Principal principal)
	throws IOException {
		//不检查文件格式是否正确，委托给前端
		var fileName = "dataSchema." + type.getExtension();
		var filePath = Path.of("D:/CloudCollect/temp", fileName);
		file.transferTo(filePath);

		//读取写入过的文件内容，然后更新数据库
		var string = Files.readString(filePath);
		var user = ((JwtUserDetails) principal).getDelegateUser();
		ieService.importData(type, string, user);
	}

	@ApiOperation("导出收藏到指定格式的文件。例如：Xml、Json、Yaml。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "type", value = "数据类型", required = true)
	})
	@PostMapping("/export")
	public ResponseEntity<byte[]> exportData(@RequestParam(defaultValue = "YAML") DataType type) {
		//不在本地缓存文件
		var fileName = "dataSchema." + type.getExtension();
		var string = ieService.exportData(type);

		//设置响应头，并设置响应体为byte[]类型（也可以是InputStream、Resource等，间接得到byte[]）
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());
		return new ResponseEntity<>(string.getBytes(), headers, HttpStatus.OK);
	}
}
