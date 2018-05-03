package com.samplepoint.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List; 
import javax.servlet.http.HttpServletResponse; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView; 
import com.google.gson.Gson;
import com.samplepoint.service.AnalogDataService;
import com.samplepoint.service.CDRDataService;
import com.samplepoint.service.DigitalDataService;
import com.samplepoint.service.RadioDataService; 

@Controller

public class ShowDataController {
	@Autowired
	DigitalDataService dservice;
	@Autowired
	AnalogDataService aservice;
	@Autowired
	CDRDataService cservice;
	@Autowired
	RadioDataService rservice;

	/**
	 * 显示数据页面
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("datalist")
	public ModelAndView dataList(@RequestParam("type") String type) {
		List list = null;
		if ("1".equals(type)) {
			list = dservice.listDigitalDatas();
			return new ModelAndView("jsp/DTVlist").addObject("list", list).addObject("type", "数字电视");
		} else if ("2".equals(type)) {
			list = aservice.listAnalogDatas();
			return new ModelAndView("jsp/ATVlist").addObject("list", list).addObject("type", "模拟电视");
		} else if ("3".equals(type)) {
			list = cservice.listCDRDatas();
			return new ModelAndView("jsp/CDRlist").addObject("list", list).addObject("type", "CDR");
		} else if ("4".equals(type)) {
			list = rservice.listRadioDatas();
			return new ModelAndView("jsp/AFMlist").addObject("list", list).addObject("type", "调幅调频");
		}
		return new ModelAndView("jsp/DTVlist").addObject("list", dservice.listDigitalDatas()).addObject("type", "数字电视");
	}

	/**
	 * 获取采样点
	 *
	 * @param uid
	 * @param testModeId
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("getpoints")
	@ResponseBody
	public void getPoints(@RequestParam("testModeId") int testModeId, @RequestParam("typeId") int typeId,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter writer = response.getWriter();
		System.err.println(testModeId + "-" + typeId);
		List points = new ArrayList<>();
		if (testModeId == 1) {
			points = dservice.listPoints(typeId);
		} else if (testModeId == 2) {
			points = rservice.listPoints(typeId);
		} else if (testModeId == 3) {
			points = cservice.listPoints(typeId);
		} else {
			points = aservice.listPoints(typeId);
		}
		String json = new Gson().toJson(points).trim();
		System.err.println("json:" + json);
		writer.println(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping("delcdrdata")
	@ResponseBody
	public void delcdrdata(@RequestParam("id") int id, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		int deleteCDRData = cservice.deleteCDRData(id);
		if (deleteCDRData == 1) {
			writer.println("删除成功");
		} else {
			writer.print("删除失败");
		}
		writer.flush();
		writer.close();
	}

	@RequestMapping("deldigitaldata")
	@ResponseBody
	public void deldigitaldata(@RequestParam("id") int id, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		int deleteCDRData = dservice.deleteDigitalData(id);
		if (deleteCDRData == 1) {
			writer.println("删除成功");
		} else {
			writer.print("删除失败");
		}
		writer.flush();
		writer.close();
	}

	@RequestMapping("delanalogdata")
	@ResponseBody
	public void delanalogdata(@RequestParam("id") int id, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		int deleteCDRData = aservice.deleteAnalogData(id);
		if (deleteCDRData == 1) {
			writer.println("删除成功");
		} else {
			writer.print("删除失败");
		}
		writer.flush();
		writer.close();
	}

	@RequestMapping("delradiodata")
	@ResponseBody
	public void delradiodata(@RequestParam("id") int id, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		int deleteCDRData = rservice.deleteRadioData(id);
		if (deleteCDRData == 1) {
			writer.println("删除成功");
		} else {
			writer.print("删除失败");
		}
		writer.flush();
		writer.close();
	}
}
