package kr.or.houroffice.timecard.controller;


import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.houroffice.timecard.model.service.TimeCardServiceImpl;
import kr.or.houroffice.timecard.model.vo.Holiday;



@Controller
public class TimecardController {
	
	@Autowired
	@Qualifier(value="TimeCardService")
	private TimeCardServiceImpl tService;
	
	@Autowired
	@Qualifier(value="sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	@RequestMapping(value ="/holiday.ho")
	public String holiday() {
		return "timeCard/holiday";
	}
	
	@RequestMapping(value ="/searchHoliday.ho")
	@ResponseBody
	public ArrayList<Holiday> searchHoliday() {
		ArrayList<Holiday> list = tService.selectHoliday();
		return list;
	}
	
	@RequestMapping(value ="/work.ho")
	public String work() {
		return "timeCard/work";
	}
	
	@RequestMapping(value ="/test.ho")
	public String test() {
		return "personnel/test";
	}

	
	
}
