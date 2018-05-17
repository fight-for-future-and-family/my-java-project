package com.hoolai.manage.vo;

import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.model.OldReportUser;

public class OldReportUserVO extends AbstractObjectVO<OldReportUser> {

    private static final long serialVersionUID = -4862956885373318170L;
    

    public OldReportUserVO() {
        super();
        this.entity = new OldReportUser();
    }

    public OldReportUserVO(OldReportUser entity) {
        super(entity);
    }
}
