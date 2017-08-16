package com.thinkgem.jeesite.modules.org.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.org.entity.OrgAttachment;
import com.thinkgem.jeesite.modules.org.service.OrgAttachmentService;

@Controller
@RequestMapping("${adminPath}")
public class DownloadController extends BaseController {
	
	@Autowired
	private OrgAttachmentService orgAttachmentService;

	@RequestMapping(value = "download/downloadNative")
	@ResponseBody
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
	
	@RequestMapping(value = "download/downloadRemote")
	@ResponseBody
	public void downloadRemote(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String attchId = request.getParameter("attchId");
		OrgAttachment attachment = orgAttachmentService.get(attchId);
		URL url = new URL(Global.getConfig("web.fileServer")+ attachment.getFilePath() + attachment.getSaveFileName());
		URLConnection conn = url.openConnection();
        InputStream inStream = conn.getInputStream();
		BufferedInputStream bins = new BufferedInputStream(inStream);// 放到缓冲流里面
		BufferedOutputStream bouts = new BufferedOutputStream(response.getOutputStream());
		response.reset(); // 必要地清除response中的缓存信息
//		response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));// 设置头部信息
		response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(attachment.getSaveFileName(), "UTF-8"));// 设置头部信息
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
