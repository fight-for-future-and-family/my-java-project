package com.hoolai.manage.model;

public class Resources {
    private Long id;

    private String resourcePatternExpression;

    private String resourceDesc;

    private Integer version;

    private Integer status;

    private Integer priority;

    private String resourceName;

    private String resourceType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourcePatternExpression() {
        return resourcePatternExpression;
    }

    public void setResourcePatternExpression(String resourcePatternExpression) {
        this.resourcePatternExpression = resourcePatternExpression;
    }

    public String getResourceDesc() {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}