package com.samplepoint.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import com.samplepoint.beans.SystemSet;
import com.samplepoint.service.SystemSetService;
import com.samplepoint.utils.RandomColor;

@Controller
public class SystemSetController {
	@Autowired
	SystemSetService service;
	private int type1 = 1, type2 = 2, type3 = 3;

	/**
	 * 系统设置页面 加载系统设置数值
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping("systemset")
	public ModelAndView setSystem(@RequestParam("typeId") int typeId, HttpServletRequest request,
			HttpServletResponse response) {

		SystemSet systemSet = service.getSystemSet();
		System.err.println("system:" + systemSet);
		int score = 0, min = 0, max = 0;
		String[] colorarr = null, rangearr = null;
		List<String> rangelist = new ArrayList<>();
		List<String> colorlist = new ArrayList<>();
		if (typeId <= type3) {
			if (typeId == type1) {
				score = systemSet.getFieldScore();
				colorarr = systemSet.getFieldColor().split(",");
				rangearr = systemSet.getFieldRange().split(",");
				min = systemSet.getMinField();
				max = systemSet.getMaxField();
			} else if (typeId == type2) {
				score = systemSet.getSnrScore();
				colorarr = systemSet.getSnrColor().split(",");
				rangearr = systemSet.getSnrRange().split(",");
				min = systemSet.getMinSnr();
				max = systemSet.getMaxSnr();
			} else if (typeId == type3) {
				score = systemSet.getLdpcScore();
				colorarr = systemSet.getLdpcColor().split(",");
				rangearr = systemSet.getLdpcRange().split(",");
				min = systemSet.getMinLdpc();
				max = systemSet.getMaxLdpc();
			} else {
				score = systemSet.getFieldScore();
				colorarr = systemSet.getFieldColor().split(",");
				rangearr = systemSet.getFieldRange().split(",");
				min = systemSet.getMinField();
				max = systemSet.getMaxField();
			}
			for (String color : colorarr) {
				colorlist.add(color);
			}
			for (String range : rangearr) {
				rangelist.add(range.trim());
			}
			int clist = colorlist.size();
			System.err.println("colorlist:" + colorlist.size() + "-score:" + score);
			if (clist < score) {
				for (int i = 0; i < score - clist; i++) {
					colorlist.add(RandomColor.randomColor());
				}
			} else if (clist > score) {
				for (int i = clist - 1; i >= score; i--) {
					colorlist.remove(i);
				}
			}
			return new ModelAndView("jsp/systemset").addObject("min", min).addObject("max", max)
					.addObject("score", score).addObject("rangelist", rangelist).addObject("colorlist", colorlist)
					.addObject("typeId", typeId);
		} else {
			return new ModelAndView("jsp/systemset").addObject("typeId", 0);
		}
	}

	/**
	 * udateSystemSet 修改系统设置
	 * 
	 * @param min
	 * @param max
	 * @param score
	 * @param uid
	 * @param colors
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("updatesysset")
	public ModelAndView updateSystemSet(@RequestParam("typeId") int typeid, @RequestParam("min") int min,
			@RequestParam("max") int max, @RequestParam("score") int score, @RequestParam("colors") String colors,
			HttpServletRequest request) throws IOException {

		// 计算场强分段 返回string
		String rangelist = service.getRange(min, max, score).trim();
		String[] colorarr = colors.split(",");
		List<String> colorlist = new ArrayList<>();
		for (String color : colorarr) {
			colorlist.add("#" + color);
		}
		int clist = colorlist.size();
		if (clist < score) {
			for (int i = 0; i < score - clist; i++) {
				colorlist.add(RandomColor.randomColor());
			}
		} else if (clist > score) {
			for (int i = clist - 1; i >= score; i--) {
				colorlist.remove(i);
			}
		}
		StringBuffer sBuffer = new StringBuffer();
		for (String color : colorlist) {
			sBuffer.append(color.trim());
			sBuffer.append(",");
		}
		sBuffer.delete(sBuffer.length() - 1, sBuffer.length());
		String colorstr = sBuffer.toString().trim();
		SystemSet systemSet = new SystemSet();
		int upde = 0;

		if (typeid == type1) {
			systemSet.setMinField(min);
			systemSet.setMaxField(max);
			systemSet.setFieldColor(colorstr);
			systemSet.setFieldRange(rangelist);
			systemSet.setFieldScore(score);
			upde = service.updateFieldSet(systemSet);
		} else if (typeid == type2) {
			systemSet.setMinSnr(min);
			systemSet.setMaxSnr(max);
			systemSet.setSnrColor(colorstr);
			systemSet.setSnrRange(rangelist);
			systemSet.setSnrScore(score);
			upde = service.updateSnrSet(systemSet);
		} else if (typeid == type3) {
			systemSet.setMinLdpc(min);
			systemSet.setMaxLdpc(max);
			systemSet.setLdpcColor(colorstr);
			systemSet.setLdpcRange(rangelist);
			systemSet.setLdpcScore(score);
			upde = service.updateLdpcSet(systemSet);
		} else {
			systemSet.setMinField(min);
			systemSet.setMaxField(max);
			systemSet.setFieldColor(colorstr);
			systemSet.setFieldRange(rangelist);
			systemSet.setFieldScore(score);
			upde = service.updateFieldSet(systemSet);
		}
		if (upde == 1) {
			return new ModelAndView("jsp/systemset").addObject("min", min).addObject("max", max)
					.addObject("score", score).addObject("rangelist", rangelist).addObject("colorlist", colorlist)
					.addObject("sysmsg", "修改成功！").addObject("typeId", typeid);
		} else {
			return new ModelAndView("jsp/systemset").addObject("min", min).addObject("max", max)
					.addObject("score", score).addObject("rangelist", rangelist).addObject("colorlist", colorlist)
					.addObject("sysmsg", "修改失败！").addObject("typeId", typeid);
		}
	}

	/**
	 * 获取场强/信噪比/误包率 分段颜色
	 * 
	 * @param uid
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getsamplestyle")
	@ResponseBody
	public void getSamplestyle(@RequestParam("typeId") Integer typeid, HttpServletResponse response)
			throws IOException {
		PrintWriter writer = response.getWriter();
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		String colors = "";
		if (typeid == type1) {
			colors = service.getFieldColors();
		} else if (typeid == type2) {
			colors = service.getSnrColors();
		} else if (typeid == type3) {
			colors = service.getLdpcColors();
		} else {
			colors = service.getFieldColors();
		}
		String json = new Gson().toJson(colors.split(","));

		writer.println(json);
		writer.flush();
		writer.close();
	}

	/**
	 * 恢复默认设置
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("resetsystemset")
	public ModelAndView resetSysSet(HttpServletRequest request, HttpServletResponse response) {
		if (service.resetSysSet() == 1) {
			return setSystem(0, request, response).addObject("sysmsg", "恢复成功");
		} else {
			return setSystem(0, request, response).addObject("sysmsg", "恢复失败");
		}
	}

}
