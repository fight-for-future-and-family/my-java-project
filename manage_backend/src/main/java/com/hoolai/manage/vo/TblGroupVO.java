package com.hoolai.manage.vo;

import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.model.TblGroup;

public class TblGroupVO extends AbstractObjectVO<TblGroup> {

    private static final long serialVersionUID = -4862956885373318170L;
    
    private Integer snId;
    private Integer gameId;

    public TblGroupVO() {
        super();
        this.entity = new TblGroup();
    }

    public TblGroupVO(TblGroup entity) {
        super(entity);
    }

	public Integer getSnId() {
		return snId;
	}

	public void setSnId(Integer snId) {
		this.snId = snId;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

   

}
