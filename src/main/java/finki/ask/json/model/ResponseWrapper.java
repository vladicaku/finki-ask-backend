package finki.ask.json.model;

import com.fasterxml.jackson.annotation.JsonView;
import finki.ask.json.view.*;

public class ResponseWrapper {

	@JsonView(View.Public.class)
	private ResponseStatus responseStatus;

	@JsonView(View.Public.class)
	private String description;

	@JsonView(View.Public.class)
	private Object data;

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
