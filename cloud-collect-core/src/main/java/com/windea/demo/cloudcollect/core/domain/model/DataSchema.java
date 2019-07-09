package com.windea.demo.cloudcollect.core.domain.model;

import com.windea.demo.cloudcollect.core.domain.entity.Collect;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据约束。用于导入/导出数据。
 */
@Data
@NoArgsConstructor
public class DataSchema implements Serializable {
	private static final long serialVersionUID = 2929238521052792644L;

	private List<Collect> collectList = new ArrayList<>();


	public DataSchema(List<Collect> collectList) {
		this.collectList = collectList;
	}
}
