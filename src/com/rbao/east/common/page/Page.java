package com.rbao.east.common.page;

import java.io.Serializable;
import java.util.Collection;
/**
 *page实体 
 * */
public class Page<T> implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	  public int pageSize;
	  public Collection<T> data;
	  public long totalPage;
	  public long currentPage;  
	  public long totalNum  ;
	  
	  public Page (){
		  
	  }

	 
	  public Page(long current, long total, Collection<T> data, int pageSize)
	  {
		  
		this.currentPage = current;
	    this.pageSize = pageSize; 
	    this.totalPage = total;
	    this.data = data;
	  }

	  public   long getStar(){
			 long begin = this.currentPage -1L ;
			 if(begin <0){
				 begin = 0; 
			 }
			 return begin;
	  }
		
		public   int getPageSize() {
			return pageSize;
		}

		public Collection<T> getData() {
			return data;
		}


		public void setData(Collection<T> data) {
			this.data = data;
		}


		public long getTotalPage() {
			return totalPage;
		}


		public void setTotalPage(long totalPage) {
			this.totalPage = totalPage;
		}


		public long getCurrentPage() {
			return currentPage;
		}


		public void setCurrentPage(long currentPage) {
			this.currentPage = currentPage;
		}


		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}


		public long getTotalNum() {
			return totalNum;
		}


		public void setTotalNum(long totalNum) {
			this.totalNum = totalNum;
		}
}
