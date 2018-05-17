package com.hoolai.manage.model;

import java.util.Date;

public class CustomReportModel {
    private Long id;

    private String name;

    private String code;

    private Integer intervalTime;

    private Integer status;
    
    private Integer type;
    
    private String description;

    private Date createTime;

    private String templateSql;
    
    private Integer isPresto;
    
    public Integer getIsPresto() {
		return isPresto;
	}

	public void setIsPresto(Integer isPresto) {
		this.isPresto = isPresto;
	}

	public CustomReportModel(){}

    public CustomReportModel(Long id,Integer type) {
		this.type = type;
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
    }


    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTemplateSql() {
        return templateSql;
    }

    public void setTemplateSql(String templateSql) {
        this.templateSql = templateSql;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}