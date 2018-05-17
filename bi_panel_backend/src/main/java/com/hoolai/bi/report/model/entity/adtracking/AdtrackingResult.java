package com.hoolai.bi.report.model.entity.adtracking;

public class AdtrackingResult extends AdTracking{

	private GameInstalls gameInstalls;
	
	private AdTrackingCallback adTrackingCallback;

	public GameInstalls getGameInstalls() {
		return gameInstalls;
	}

	public void setGameInstalls(GameInstalls gameInstalls) {
		this.gameInstalls = gameInstalls;
	}

	public AdTrackingCallback getAdTrackingCallback() {
		return adTrackingCallback;
	}

	public void setAdTrackingCallback(AdTrackingCallback adTrackingCallback) {
		this.adTrackingCallback = adTrackingCallback;
	}
	
	
}
