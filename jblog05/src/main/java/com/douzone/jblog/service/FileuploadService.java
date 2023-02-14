package com.douzone.jblog.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
@PropertySource("classpath:com/douzone/jblog/web/fileupload.properties")
public class FileuploadService {
	
	@Autowired
	private Environment env;
//	private static String SAVE_PATH = "/Users/yoon/jblog-uploads";	// 저장 경로(Windows는 "/mysite-uploads" 이렇게만)
//	private static String URL_PATH = "/assets/upload-images";	// 코드상으로 Mapping 되는 곳
	
	public String restore(MultipartFile file) {
		String url = null;
		
		try {
			File uploadDirectory = new File(env.getProperty("fileupload.uploadLocations"));
			if(!uploadDirectory.exists()) {
				uploadDirectory.mkdirs();
			}
			
			if(file.isEmpty()) {	// 파일이 업로드 되지 않았을 때
				url = "/assets/upload-images/20231752530972.jpeg";
				return url;			// null을 리턴
			}
			
			String originFilename = file.getOriginalFilename();	// 사용자가 업로드한 파일 이름 ex/ profile.jpeg
			String extName = originFilename.substring(originFilename.lastIndexOf(".")+1);	// 뒤에서부터 .을 찾아보고 / +1: . 다음부터의 문자를 뽑아낸다 ex/ jpeg
			String saveFilename = generateSaveFilename(extName);
			Long fileSize = file.getSize();
			
			System.out.println("##########" + originFilename);
			System.out.println("##########" + saveFilename);
			System.out.println("##########" + fileSize);	
			
			byte[] data = file.getBytes();
			OutputStream os = new FileOutputStream(env.getProperty("fileupload.uploadLocations") + "/" + saveFilename);
			os.write(data);
			os.close();
			
			url = env.getProperty("fileupload.resourceUrl") + "/" + saveFilename; 
		} catch(IOException ex) {
			throw new FileuploadServiceException(ex.toString());
		}
		
		return url;
	}

	private String generateSaveFilename(String extName) {
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		
		return filename;
	}

}