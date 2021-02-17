package kr.or.houroffice.main.model.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import kr.or.houroffice.common.Page;
import kr.or.houroffice.main.model.dao.MainDAO;
import kr.or.houroffice.main.model.vo.AllNotice;
import kr.or.houroffice.main.model.vo.CompanyRule;
import kr.or.houroffice.main.model.vo.MainMailGetter;
import kr.or.houroffice.main.model.vo.MainMailPage;

@Service("mainService")
public class MainServiceImpl implements MainService{

	@Resource(name="mainDAO")
	private MainDAO mainDAO;
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	@Override
	public ArrayList<AllNotice> selectAllNotice() {
		return mainDAO.selectAllNotice(sqlSession);
	}

	@Override
	public ArrayList<CompanyRule> selectAllRule(Page page) {
		return mainDAO.selectAllRule(sqlSession,page);
		
	}

	@Override
	public ArrayList<MainMailGetter> selectAllMail(MainMailPage mmp) {
		return mainDAO.selectAllMail(sqlSession,mmp);
		
	}

}
