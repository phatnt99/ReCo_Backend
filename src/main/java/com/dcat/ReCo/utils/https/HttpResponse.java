package com.dcat.ReCo.utils.https;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class HttpResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private HttpStatus status;
	private boolean success;
	private Object data;

	public HttpResponse() {
		super();
	}

	public HttpResponse(HttpStatus status, boolean success, Object data) {
		super();
		this.status = status;
		this.success = success;
		this.data = data;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ResponseEntity<Object> send() {
		return new ResponseEntity<Object>(this, this.getStatus());
	}

	public ResponseEntity<Object> sendWithPaginateHeaders() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Access-Control-Expose-Headers", "Content-Range");
		headers.add("Content-Range", String.valueOf(((Page<?>) this.data).getTotalElements()));

		return new ResponseEntity<Object>(this, headers, this.getStatus());
	}

	public static ResponseEntity<Object> sendNotFoundError() {
		return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<Object> sendNoContent() {
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	public static ResponseEntity<Object> sendOK(Object data) {
		
		return new HttpResponse(HttpStatus.OK, true, data).send();
	}
	
public static ResponseEntity<Object> sendCreated(Object data) {
		
		return new HttpResponse(HttpStatus.CREATED, true, data).send();
	}

}
