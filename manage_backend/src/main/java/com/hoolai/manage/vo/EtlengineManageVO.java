package com.hoolai.manage.vo;


import java.util.Date;

import com.hoolai.dao.pagination.AbstractObjectVO;

import com.hoolai.manage.model.EtlengineManage;

public class EtlengineManageVO extends AbstractObjectVO<EtlengineManage>{
	
	private static final long serialVersionUID = -4862956885373318170L;
	
	  	private Integer id;

	    private String type;

	    private Integer level;

	    private String title;

	    private Date addedAt;

	    private Date modifyAt;
	    
	    public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Integer getLevel() {
			return level;
		}

		public void setLevel(Integer level) {
			this.level = level;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Date getAddedAt() {
			return addedAt;
		}

		public void setAddedAt(Date addedAt) {
			this.addedAt = addedAt;
		}

		public Date getModifyAt() {
			return modifyAt;
		}

		public void setModifyAt(Date modifyAt) {
			this.modifyAt = modifyAt;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getTemplate() {
			return template;
		}

		public void setTemplate(String template) {
			this.template = template;
		}

		private String description;

	    private String template;
	
	public  EtlengineManageVO() {
		super();
		this.entity=new EtlengineManage();
	}

	public  EtlengineManageVO(EtlengineManage etlengineManage) {
		this.entity = etlengineManage;
	}


}
