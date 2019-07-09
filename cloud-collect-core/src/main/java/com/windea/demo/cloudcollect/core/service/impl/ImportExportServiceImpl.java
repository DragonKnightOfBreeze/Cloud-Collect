package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.commons.kotlin.loader.JsonLoader;
import com.windea.demo.cloudcollect.core.domain.entity.Collect;
import com.windea.demo.cloudcollect.core.exception.NotImplementedException;
import com.windea.demo.cloudcollect.core.repository.*;
import com.windea.demo.cloudcollect.core.service.ImportExportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportExportServiceImpl implements ImportExportService {
	private final CollectRepository collectRepository;
	private final CollectCategoryRepository categoryRepository;
	private final CollectTagRepository tagRepository;

	public ImportExportServiceImpl(CollectRepository collectRepository, CollectCategoryRepository categoryRepository,
		CollectTagRepository tagRepository) {
		this.collectRepository = collectRepository;
		this.categoryRepository = categoryRepository;
		this.tagRepository = tagRepository;
	}


	@Override
	public List<Collect> fromXml(String string) {
		throw new NotImplementedException();
	}

	@Override
	public List<Collect> fromJson(String string) {
		var dataMap = JsonLoader.instance().fromString(string);
		return null;
	}

	@Override
	public List<Collect> fromYaml(String string) {
		//TODO
		return null;
	}

	@Override
	public void toXml() {
		throw new NotImplementedException();
	}

	@Override
	public void toJson() {
		//TODO
	}

	@Override
	public void toYaml() {
		//TODO
	}

}
