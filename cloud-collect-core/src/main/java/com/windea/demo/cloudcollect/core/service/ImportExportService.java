package com.windea.demo.cloudcollect.core.service;

import com.windea.demo.cloudcollect.core.domain.entity.Collect;

import java.util.List;

/**
 * 导入导出的服务。
 *
 * <p>文件结构：
 * <pre>
 * #/Collect/[]/name,summary,url,logoUrl,category,tags,type
 * #/CollectCategory/[]/name,summary
 * #/CollectTag/[]/name,summary
 * </pre>
 */
public interface ImportExportService {
	/**
	 * TODO 从xml文件导入收藏。
	 */
	List<Collect> fromXml(String text);

	/**
	 * TODO 从json文件导入收藏。
	 */
	List<Collect> fromJson(String text);

	/**
	 * TODO 从yaml文件导入收藏。
	 */
	List<Collect> fromYaml(String text);

	/**
	 * TODO 导出收藏到xml文件。
	 */
	void toXml();

	/**
	 * TODO 导出收藏到json文件。
	 */
	void toJson();

	/**
	 * TODO 导出收藏到yaml文件。
	 */
	void toYaml();
}
