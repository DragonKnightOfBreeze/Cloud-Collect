package com.windea.demo.cloudcollect.core.service.impl;

import com.windea.demo.cloudcollect.core.domain.entity.Collect;
import com.windea.demo.cloudcollect.core.service.ImportExportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportExportServiceImpl implements ImportExportService {
	@Override
	public List<Collect> fromXml(String text) {
		return null;
	}

	@Override
	public List<Collect> fromJson(String text) {
		return null;
	}

	@Override
	public List<Collect> fromYaml(String text) {
		return null;
	}

	@Override
	public String toXml() {
		return null;
	}

	@Override
	public String toJson() {
		return null;
	}

	@Override
	public String toYaml() {
		return null;
	}
}
