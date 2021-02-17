package kr.or.houroffice.main.model.service;

import java.util.ArrayList;

import kr.or.houroffice.common.Page;
import kr.or.houroffice.main.model.vo.AllNotice;
import kr.or.houroffice.main.model.vo.CompanyRule;
import kr.or.houroffice.main.model.vo.MainMailGetter;
import kr.or.houroffice.main.model.vo.MainMailPage;

public interface MainService {
	public ArrayList<AllNotice> selectAllNotice();
	public ArrayList<CompanyRule> selectAllRule(Page page);
	public ArrayList<MainMailGetter> selectAllMail(MainMailPage mmp);
}
