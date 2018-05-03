package com.samplepoint.controller;
 
import java.util.List;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.servlet.ModelAndView;  
import com.samplepoint.service.AnalogDataService;
import com.samplepoint.service.CDRDataService;
import com.samplepoint.service.DigitalDataService;
import com.samplepoint.service.RadioDataService; 

@Controller
@RequestMapping("dataview")
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

	
}
