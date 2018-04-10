package com.samplepoint.controller;

import java.io.IOException;
import java.io.PrintWriter;  
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.samplepoint.beans.Emitter;
import com.samplepoint.service.EmitterService;

@Controller
public class EmitterController {
	@Autowired
	EmitterService service;

	@RequestMapping("gettranlocation")
	@ResponseBody
	public void getEmitters(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		Emitter Emitter = service.findByEmitter();
		Gson gson = new Gson();
		String json = gson.toJson(Emitter);
		System.err.println(json);
		writer.print(json);
		writer.flush();
		writer.close();
	}
}
