package com.jt.controller;

import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.EasyUIFile;


@Controller
public class FileController {
	@Autowired
	private FileService fileService;
     @RequestMapping("/file")
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException {
    	 /**
    	  *  上传文件的步骤   
    	  * 1.获取文件的名字
    	  * 2.创建文件存放的路径
    	  * 3.判断文件是否存在
    	  * 3。实现文件的上传
    	  * 
    	  */
    	//1.获取文件的名字 
    	 String Name = fileImage.getName();
    	 String filename = fileImage.getOriginalFilename();
		 
    	 //创建文件目录
    	 File dirFile=new File("F:/java/vm/01-jt-upload");
    	 //判断文件夹是否存在
    	 if(!dirFile.exists())
    		 dirFile.mkdirs();
    	 //实现文件的上传
    	 File file=new File("F:/java/vm/01-jt-upload/"+filename);
    	 fileImage.transferTo(file);
    	 return "redirect:file.jsp";
      }
     @RequestMapping("pic/upload")
     @ResponseBody
     public EasyUIFile fileUpload(MultipartFile uploadFile) {
		return	fileService.fileUpload(uploadFile);
     }
}
