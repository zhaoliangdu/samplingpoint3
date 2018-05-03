package com.samplepoint.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MapController {

	@RequestMapping("gaode")
	public ModelAndView gaodeMap() {

		return new ModelAndView("/jsp/map/gaode");
	}

	@RequestMapping("bing")
	public ModelAndView bingMap() {

		return new ModelAndView("/jsp/map/bing");
	}
}
