package com.javastudy.utils;

import java.io.Serializable;
import java.util.List;

public class DataPage<T> implements Serializable {

	private static final long serialVersionUID = 7386201149818309936L;
	private int pageSize;
	private int currentPage;
	private long totalRecords;
	private List<T> datas;

	public DataPage() {
	}

	public DataPage(int pageSize, int current) {
		this.pageSize = pageSize;
		this.currentPage = current;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalRecords() {
		return this.totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public long getPageTotal() {
		if (this.pageSize > 0) {
			return ((this.totalRecords + this.pageSize - 1L) / this.pageSize);
		}
		return -1L;
	}

	public List<T> getDatas() {
		return this.datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof DataPage)) {
			return false;
		}

		DataPage that = (DataPage) o;

		if (this.currentPage != that.currentPage) {
			return false;
		}
		if (this.pageSize != that.pageSize) {
			return false;
		}
		if (getPageTotal() != that.getPageTotal()) {
			return false;
		}
		if (this.totalRecords != that.totalRecords) {
			return false;
		}
		if (this.datas != null)
			if (this.datas.equals(that.datas))
				return true;
			else if (that.datas == null)
				return false;

		return true;
	}

	public int hashCode() {
		int result = this.pageSize;
		result = 31 * result + this.currentPage;
		result = 31 * result + new Long(this.totalRecords).intValue();
		result = 31 * result + new Long(getPageTotal()).intValue();
		result = 31 * result
				+ ((this.datas != null) ? this.datas.hashCode() : 0);
		return result;
	}

}
