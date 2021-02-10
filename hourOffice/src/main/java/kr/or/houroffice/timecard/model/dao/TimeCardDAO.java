package kr.or.houroffice.timecard.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.houroffice.timecard.model.vo.Holiday;

@Repository("TimeCardDAO")
public class TimeCardDAO {

	public ArrayList<Holiday> selectHoliday(SqlSessionTemplate sqlSession) {
		List<Holiday> list = sqlSession.selectList("timeCard.selectHoliday");
		return (ArrayList<Holiday>) list;
	}

}
