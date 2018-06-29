package es.redmic.socket.test.integration.ingest.common.dto;

public class RunTaskIngestDataDocumentDTO {

	/*
	 * Clase para testear arrancar una tarea. Similar a la original
	 */
	public RunTaskIngestDataDocumentDTO() {
	}

	private String taskName = "ingest-document";

	private Long userId = 13l;

	private String fileName = "file.csv";

	private String delimiter = "|";

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
}
