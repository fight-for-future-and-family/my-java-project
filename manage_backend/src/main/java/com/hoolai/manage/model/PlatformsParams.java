package com.hoolai.manage.model;

import java.util.Date;

public class PlatformsParams {
    private Long id;

    private Long platformsId;

    private String platformsCode;

    private String platformsName;

    private Integer mapperType;

    private String mapperColumn;

    private String paramsCode;

    private Integer isExtraParams;

    private Integer status;

    private Date createTime;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlatformsId() {
        return platformsId;
    }

    public void setPlatformsId(Long platformsId) {
        this.platformsId = platformsId;
    }

    public String getPlatformsCode() {
        return platformsCode;
    }

    public void setPlatformsCode(String platformsCode) {
        this.platformsCode = platformsCode;
    }

    public String getPlatformsName() {
        return platformsName;
    }

    public void setPlatformsName(String platformsName) {
        this.platformsName = platformsName;
    }

    public Integer getMapperType() {
        return mapperType;
    }

    public void setMapperType(Integer mapperType) {
        this.mapperType = mapperType;
    }

    public String getMapperColumn() {
        return mapperColumn;
    }

    public void setMapperColumn(String mapperColumn) {
        this.mapperColumn = mapperColumn;
    }

    public String getParamsCode() {
        return paramsCode;
    }

    public void setParamsCode(String paramsCode) {
        this.paramsCode = paramsCode;
    }

    public Integer getIsExtraParams() {
        return isExtraParams;
    }

    public void setIsExtraParams(Integer isExtraParams) {
        this.isExtraParams = isExtraParams;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}