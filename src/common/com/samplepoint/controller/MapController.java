package com.samplepoint.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("map")
public class MapController {

	@RequestMapping("gaode")
	public ModelAndView gaodeView() {
		return new ModelAndView("jsp/map/gaode");
	}

	@RequestMapping("bing")
	public ModelAndView bingView() {
		return new ModelAndView("jsp/map/bing");
	}
}
