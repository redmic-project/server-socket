package es.redmic.socket.test.integration.ingest.common.dto;

import java.util.List;

public class ItemDTO {

	public ItemDTO() {
	}

	protected List<String> columns;

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
}
