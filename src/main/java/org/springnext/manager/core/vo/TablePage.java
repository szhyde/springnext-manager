package org.springnext.manager.core.vo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springnext.manager.core.vo.transform.VOTransform;

import com.google.common.collect.Lists;

/**
 * 分页查询时返回的VO
 * 
 * @author HyDe
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class TablePage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7824565284555196174L;
	
	private TablePage(){
		
	}
	
	/**
	 * 初始化方法
	 * @return
	 */
	public static TablePage createTablePage(){
		TablePage tablePage = new TablePage();
		tablePage.setTotal(0);
		tablePage.setData(Lists.newArrayList());
		return tablePage;
	}
	
	/**
	 * 把spring的Page转换成Table
	 * @param page
	 * @param voTransform
	 * @return
	 */
	public static TablePage createTablePage(Page page,VOTransform voTransform){
		TablePage tablePage = new TablePage();
		tablePage.setTotal(page.getTotalElements());
		if(voTransform!=null){
			tablePage.setData(voTransform.EntityToVO(page.getContent()));
		}else{
			tablePage.setData(page.getContent());
		}
		return tablePage;
	}

	private List<Object> data;

	private long total;

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

}
