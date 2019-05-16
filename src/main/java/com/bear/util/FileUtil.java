package com.bear.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

	// private static String UPLOADED_FOLDER = "D:\\10.ideaWork\\Mybatis-Spring-master\\src\\main\\webapp\\static\\img";

	private static String UPLOADED_FOLDER = "D:\\mybatis-spring\\images";

	public static String fileUrl;

	public static String getFileUrl() {
		return UPLOADED_FOLDER;
	}

	public static void setFileUrl(String fileUrl) {
		FileUtil.fileUrl = fileUrl;
	}

	/**
	 * 上传文件
	 * @param file
	 * @return
	 */
	public static Map<String,Object> upload(MultipartFile file){
		// 用户上传文件的原始名称
		String fileName = null;
		// 当前时间的时间戳字符串
		String newName = null;
		// 用户上传文件的原始名称加上时间戳生成的唯一的新名称(便于在文件夹存放中不重名)
		String newFileName = null;
		// 要保存到服务器的文件夹路径
		String projectPath = FileUtil.projectPath("upload");
		// 服务器返回的对象
		Map<String,Object> map = new HashMap<String, Object>();
		// 有上传文件,执行以下操作
		if( !file.isEmpty() ){
			// 用户上传文件的原始名称
			fileName = file.getOriginalFilename();
			// 当前时间的时间戳字符串
			newName =  DateFormatUtil.getDate("yyyyMMddhhmmssSSS");
			// 用户上传文件的扩展名
			String extName = "";
			int pos = fileName.lastIndexOf(".");
			if( pos > -1){
				extName=fileName.substring(pos);
			}
			// 用户上传文件的原始名称加上时间戳生成的唯一的新名称(便于在文件夹存放中不重名)
			newFileName = newName + fileName;
			// 把上传的文件保存到服务器,创建File对象(文件夹,文件)
			File outFile = new File(projectPath,newFileName);
			try {
				// 写到磁盘(upload文件夹下)
				file.transferTo(outFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 将文件路径(upload后)保存到数据库
			String filePath = projectPath.substring(projectPath.indexOf("/upload"))+newFileName;
			// 前台展示图片格式
			// <Context docBase="D:/mybatis-spring/images" path="/openattach"/>
			// <img src="${webPath}/openattach${attach.fileResPath}" />

			map.put("success",true);
			map.put("url","/openattach"+ filePath);
		}else{
			map.put("success",false);
			map.put("url","");
		}
		return map;
	}


	/**
	 * 创建目录、返回路径
	 * catalogue 参数代表要生成的父目录名字
	 */
	public static String  projectPath(String catalogue){
		String projectPath =getFileUrl();
		String time = DateFormatUtil.getDate("yyyyMMdd");
		// 磁盘路径 / 父目录 / yyyyMMdd时间戳
		String filePath = (projectPath+"/"+catalogue+"/"+time+"/");
		// 调用创建目录的方法
		createDir(filePath);
		return filePath;
	}


	/**
	 * 创建文件夹
	 */
	public static void createDir(String path) {
		File file = new File(path);
		if (file != null && !file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 获取文件的大小
	 * @param str 文件的路径
	 * @return
	 */
	public static String fileSize(String str){
		// 获取文件
		File objFile = new File(str);
		long filesize=objFile.length();
		String strUnit="B";
		String strAfterComma="";
		int intDivisor=1;
		// 文件长度大于mb
		if(filesize>=1024*1024){
			strUnit = "MB";
			intDivisor=1024*1024;
		}else if(filesize>=1024){
			strUnit = "KB";
			intDivisor=1024;
		}
		if(intDivisor==1){
			return filesize + " " + strUnit;
		}
		strAfterComma = "" + 100 * (filesize % intDivisor) / intDivisor ;
		if(strAfterComma=="") {
			strAfterComma=".0";
		}
		return 	filesize / intDivisor + "." + strAfterComma + " " + strUnit;
	}

}