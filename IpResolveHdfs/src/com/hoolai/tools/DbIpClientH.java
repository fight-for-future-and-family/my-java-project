package com.hoolai.tools;

import in.ankushs.dbip.api.GeoEntity;
import in.ankushs.dbip.exceptions.InvalidIPException;
import in.ankushs.dbip.importer.ResourceImporter;
import in.ankushs.dbip.lookup.GeoEntityLookupService;
import in.ankushs.dbip.lookup.GeoEntityLookupServiceImpl;
import in.ankushs.dbip.utils.PreConditions;

import java.io.File;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.net.InetAddresses;
public final class DbIpClientH {
	
	private static final Logger logger = LoggerFactory.getLogger(DbIpClientH.class);

	private final File file ;
	private final GeoEntityLookupService lookupService = GeoEntityLookupServiceImpl.getInstance();
	
	private static boolean flag = false;
	
	
	public DbIpClientH(final File gzip){
		PreConditions.checkExpression(!gzip.exists(), "file " + gzip.getName() + " does not exist");
		this.file = gzip;
		if(!flag){
			flag = true;
			logger.info("Loading db ip into repository ");
			ResourceImporter.getInstance().load(gzip);
			logger.info("Loading finished");
		}
		else{
			logger.info(" DbIp csv file has already been loaded ");
		}
	}
	
	
	public GeoEntity lookup(final String ip){
		InetAddress inetAddress = null;
		try{
			inetAddress = InetAddresses.forString(ip);
		}
		catch(final IllegalArgumentException ex){
			logger.error("Invalid IP given",ex);
			throw new InvalidIPException("Invalid IP passed");
		}
		return lookup(inetAddress);
	}
	
	public GeoEntity lookup(final InetAddress inetAddress){
		PreConditions.checkNull(inetAddress, "inetAddress cannot be null");
		return lookupService.lookup(inetAddress);
	}
}
