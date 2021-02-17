package kr.or.houroffice.main.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.houroffice.common.Page;
import kr.or.houroffice.main.model.vo.AllNotice;
import kr.or.houroffice.main.model.vo.CompanyRule;
import kr.or.houroffice.main.model.vo.MainMailGetter;
import kr.or.houroffice.main.model.vo.MainMailPage;

@Repository("mainDAO")
public class MainDAO {

	public ArrayList<AllNotice> selectAllNotice(SqlSessionTemplate sqlSession) {
		List list = sqlSession.selectList("main.selectAllNotice");
		return (ArrayList<AllNotice>) list;
	}

	public ArrayList<CompanyRule> selectAllRule(SqlSessionTemplate sqlSession, Page page) {
		List list = sqlSession.selectList("main.selectAllRule",page);
		return (ArrayList<CompanyRule>)list;
	}

	public ArrayList<MainMailGetter> selectAllMail(SqlSessionTemplate sqlSession, MainMailPage mmp) {
		List list = sqlSession.selectList("main.selectAllMail",mmp);
		return (ArrayList<MainMailGetter>)list;
	}

}
