package com.windea.demo.cloudcollect.core.service;

import com.windea.commons.kotlin.enums.DataType;
import com.windea.demo.cloudcollect.core.domain.entity.User;

/**
 * 导入/导出数据的服务。
 * @see com.windea.demo.cloudcollect.core.domain.model.DataSchema
 */
public interface ImportExportService {
	/**
	 * 从指定格式的文件导入收藏。例如：Xml、Json、Yaml。
	 */
	void importData(DataType type, String string, User user);

	/**
	 * 导出收藏到指定格式的文件。例如：Xml、Json、Yaml。
	 */
	String exportData(DataType type);
}
