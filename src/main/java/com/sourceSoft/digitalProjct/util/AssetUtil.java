package com.sourceSoft.digitalProjct.util;

import com.sourceSoft.digitalProjct.constant.SysConstant;
import com.sourceSoft.digitalProjct.model.system.SysUser;

public class AssetUtil {
	public static String getAssetId(String assetClass) {
		String assetId = "";
		assetId = (assetClass + Identities.uuid2()).subSequence(0,32).toString();
		return assetId;
	}
	/**
	 * 是否是管理员角色或者是主任角色
	 * @param loginUser
	 * @return
	 */
	public static boolean isAllLevel(SysUser loginUser){
		if(SysConstant.ADMINROLE.equals(loginUser.getPowerLevel())||
		SysConstant.ZRROLE.equals(loginUser.getPowerLevel())){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取附件存放路径
	 * @return
	 */
	public static String getAffixPath(){
		StringBuffer strBuffer = DateUtil.getDateSequence2();
		strBuffer.insert(0, SysConstant.AFFIX);
		strBuffer.insert(0, SysConstant.NAVIGATION_SPLIT_CHAR);
		strBuffer.insert(0, SysConstant.RESOURCE_PATH);
		return strBuffer.toString();
	}
	
	/**
	 * 获取头像存放路径
	 * @return
	 */
	public static String getHeadImgPath(){
		StringBuffer strBuffer = DateUtil.getDateSequence2();
		strBuffer.insert(0, SysConstant.HEADIMG);
		strBuffer.insert(0, SysConstant.NAVIGATION_SPLIT_CHAR);
		strBuffer.insert(0, SysConstant.RESOURCE_PATH);
		return strBuffer.toString();
	}
	
	/**
	 * 获取文件存放路径
	 * @return
	 */
	public static String getFileInfoPath(){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.insert(0, SysConstant.NAVIGATION_SPLIT_CHAR);
		strBuffer.insert(0, SysConstant.FILEINFO);
		strBuffer.insert(0, SysConstant.NAVIGATION_SPLIT_CHAR);
		strBuffer.insert(0, SysConstant.RESOURCE_PATH);
		return strBuffer.toString();
	}
	
	/**
	 * 获取pdf存放路径
	 * @return
	 */
	public static String getPDFInfoPath(){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.insert(0, SysConstant.NAVIGATION_SPLIT_CHAR);
		strBuffer.insert(0, SysConstant.PDF);
		strBuffer.insert(0, SysConstant.NAVIGATION_SPLIT_CHAR);
		strBuffer.insert(0, SysConstant.RESOURCE_PATH);
		return strBuffer.toString();
	}
}
