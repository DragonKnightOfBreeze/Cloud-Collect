package com.windea.demo.cloudcollect.core.service;

import com.windea.demo.cloudcollect.core.domain.entity.Collect;

import java.util.List;

public interface ImportExportService {
	List<Collect> fromXml(String text);

	List<Collect> fromJson(String text);

	List<Collect> fromYaml(String text);

	String toXml();

	String toJson();

	String toYaml();
}
