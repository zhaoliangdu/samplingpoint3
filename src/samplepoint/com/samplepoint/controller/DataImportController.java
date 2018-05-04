package com.samplepoint.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.samplepoint.beans.Emitter;
import com.samplepoint.service.AnalogDataService;
import com.samplepoint.service.CDRDataService;
import com.samplepoint.service.DataImport;
import com.samplepoint.service.DigitalDataService;
import com.samplepoint.service.EmitterService;
import com.samplepoint.service.RadioDataService;

@Controller
public class DataImportController {
	@Autowired
	DataImport service;
	@Autowired
	AnalogDataService aservice;
	@Autowired
	CDRDataService cservice;
	@Autowired
	DigitalDataService dservice;
	@Autowired
	RadioDataService rservice;
	@Autowired
	EmitterService eservice;

	/**
	 * 从文件导入数据
	 * @param mode
	 * @param emitter
	 * @param lng
	 * @param lat
	 * @param filepath
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("fileimport")
	public ModelAndView fileImport(@RequestParam("mode") String mode, @RequestParam("emitter") String emitter,
			@RequestParam("lng") float lng, @RequestParam("lat") float lat, @RequestParam("filepath") String filepath)
			throws UnsupportedEncodingException {
		String filepathStr = new String(filepath.getBytes("iso8859-1"), "utf-8").trim();
		String filePath = filepathStr.substring(10, filepathStr.length() - 1).trim().toString();
		String emitterName = emitter.substring(10, emitter.length() - 1).trim().toString();

		Emitter findByEmitterName = eservice.findByName(emitterName);
		@SuppressWarnings("rawtypes")
		Map<String, Float> map = new HashMap();
		map.put("lng", lng);
		map.put("lat", lat);
		Emitter findByLocation = eservice.findByLocation(map);
		if (findByEmitterName != null) {
			eservice.updateEmitter(new Emitter(emitterName, lng, lat));
		} else if (findByLocation != null) {
			eservice.updateEmitter(new Emitter(emitterName, lng, lat));
		}
		String realpath = filePath.replace("\\", "/");
		return new ModelAndView("index").addObject("mode", mode).addObject("emitter", emitterName).addObject("lng", lng)
				.addObject("lat", lat).addObject("filepath", realpath);
	}

	
	@RequestMapping("importdata")
	public void importData(@RequestParam("mode") String mode, @RequestParam("emitter") String emitter,
			@RequestParam("lng") float lng, @RequestParam("lat") float lat, @RequestParam("filepath") String filepath,
			HttpServletResponse response) throws IOException {

		System.err.println(
				"mode:" + mode + "-filepath:" + filepath + "emitter:" + emitter + "-lng:" + lng + "-lat:" + lat);

		System.err.println("mode:" + mode + "-filepath:" + filepath + "emitter:"
				+ emitter.substring(10, emitter.length() - 1) + "-lng:" + lng + "-lat:" + lat);

		boolean readFileToDataBase = service.readFileToDataBase(filepath);
		PrintWriter writer = response.getWriter();
		if (readFileToDataBase) { 
			writer.println("文件加载完成！"); 
		} else {
			writer.println("文件加载失败！"); 
		}
		writer.flush();
		writer.close();

	}
}
