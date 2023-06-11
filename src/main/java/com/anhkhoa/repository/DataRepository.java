package com.anhkhoa.repository;

import java.util.List;

public class DataRepository<E> {
	private int status;
	private List<E> data;
	
	public DataRepository() {
		
	}
	
	public DataRepository(int status, List<E> data) {
		this.status = status;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<E> getData() {
		return data;
	}

	public void setData(List<E> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "DataRepository [status=" + status + ", data=" + data + "]";
	}
}
