package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.commons.kotlin.annotation.NotTested;
import com.windea.commons.kotlin.enums.DataType;
import com.windea.demo.cloudcollect.core.domain.entity.User;
import com.windea.demo.cloudcollect.core.domain.model.DataSchema;
import com.windea.demo.cloudcollect.core.repository.CollectRepository;
import com.windea.demo.cloudcollect.core.service.ImportExportService;
import org.springframework.stereotype.Service;

@Service
@NotTested("未进行实际测试……")
public class ImportExportServiceImpl implements ImportExportService {
	private final CollectRepository collectRepository;

	public ImportExportServiceImpl(CollectRepository collectRepository) {
		this.collectRepository = collectRepository;
	}


	@Override
	public void importData(DataType type, String string, User user) {
		//根据指定数据类型读取数据
		var dataSchema = type.getLoader().fromString(string, DataSchema.class);
		//导入数据时，需要重置相关id，并设置用户为当前用户
		dataSchema.getCollectList().forEach(collect -> {
			collect.setId(null);
			collect.getTags().forEach(tag -> {
				tag.setId(null);
				tag.setUser(user);
			});
			if(collect.getCategory() != null) {
				collect.getCategory().setId(null);
				collect.getCategory().setUser(user);
			}
			collect.setUser(user);
		});
		//存储到数据库中
		collectRepository.saveAll(dataSchema.getCollectList());
	}

	@Override
	public String exportData(DataType type) {
		//从数据库中查找相关数据，然后创建数据约束对象
		var collectList = collectRepository.findAll();
		var dataSchema = new DataSchema(collectList);
		//根据指定数据类型转化数据
		return type.getLoader().toString(dataSchema);
	}
}
