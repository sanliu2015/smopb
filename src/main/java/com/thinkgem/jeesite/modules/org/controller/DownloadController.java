package com.thinkgem.jeesite.modules.org.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.web.BaseController;

@Controller
@RequestMapping("${adminPath}")
public class DownloadController extends BaseController {

	@RequestMapping(value = "download/downloadNative")
	public void downloadNative(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String filePath = request.getParameter("filePath");
		String fileName = request.getParameter("fileName");
		
		File file = new File(request.getSession().getServletContext().getRealPath("/") + filePath);
		BufferedInputStream bins = new BufferedInputStream(new FileInputStream(file));// 放到缓冲流里面
		BufferedOutputStream bouts = new BufferedOutputStream(response.getOutputStream());
		response.reset(); // 必要地清除response中的缓存信息
//		response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));// 设置头部信息
		response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));// 设置头部信息
		response.addHeader("Content-Length", "" + file.length());
		response.setContentType("application/x-msdownload");// 设置response内容的类型
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		// 开始向网络传输文件流
		while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
			bouts.write(buffer, 0, bytesRead);
		}
		bouts.flush();// 这里一定要调用flush()方法
		bins.close();
		bouts.close();
	}

}
