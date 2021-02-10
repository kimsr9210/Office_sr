package kr.or.houroffice.timecard.model.service;

import java.util.ArrayList;

import kr.or.houroffice.approval.model.vo.HolidayForm;
import kr.or.houroffice.member.model.vo.Member;
import kr.or.houroffice.timecard.model.vo.Holiday;

public interface TimeCardService  {
	
	public ArrayList<Holiday> selectHoliday();

}
