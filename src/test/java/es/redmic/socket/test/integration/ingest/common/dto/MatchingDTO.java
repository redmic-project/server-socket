package es.redmic.socket.test.integration.ingest.common.dto;

import java.util.Arrays;

public class MatchingDTO {

	/*
	 * Clase para testear el matching. Similar a la original
	 */
	public MatchingDTO() {
		ItemDTO item = new ItemDTO();
		item.setColumns(Arrays.asList("title"));
		setItemMatching(item);
	}

	public ItemDTO getItemMatching() {
		return itemMatching;
	}

	public void setItemMatching(ItemDTO itemMatching) {
		this.itemMatching = itemMatching;
	}

	private ItemDTO itemMatching;
}
