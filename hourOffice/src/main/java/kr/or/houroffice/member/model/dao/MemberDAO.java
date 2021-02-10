package kr.or.houroffice.member.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.houroffice.member.model.vo.Attendance;
import kr.or.houroffice.member.model.vo.Member;

@Repository("memberDAO")
public class MemberDAO {

	public Member loginMember(SqlSessionTemplate sqlSession, Member m) {
		Member member = sqlSession.selectOne("member.loginMember", m);
		return member;
	}

	public Member selectOneAsMemNo(SqlSessionTemplate sqlSession, int memNo) {
		return sqlSession.selectOne("member.selectOneAsMemNo", memNo);
	}

	public ArrayList<Member> selectProjectBoardMemberList(int proNo, SqlSessionTemplate sqlSession) {
		List boardMemberList = (List)sqlSession.selectList("member.selectProjectBoardMemberList", proNo);
		return (ArrayList<Member>)boardMemberList;
	}

	public ArrayList<Member> selectProjectMemberList(int proNo, SqlSessionTemplate sqlSession) {
		List projectMemberList = (List)sqlSession.selectList("member.selectProjectMemberList", proNo);
		return (ArrayList<Member>)projectMemberList;
	}
	
	public Attendance selectAttendanceMember(SqlSessionTemplate sqlSession, int memNo) {
		Attendance atten = sqlSession.selectOne("member.selectAttendanceMember", memNo);
		return atten;
		
	}
	
	public int insertAttendanceMember(SqlSessionTemplate sqlSession, int memNo) {
		int result =sqlSession.insert("member.insertAttendanceMember", memNo);
		return result;
	}

	public int updateAttendanceMember(SqlSessionTemplate sqlSession, Attendance atten) {
		int result =sqlSession.update("member.updateAttendanceMember", atten);
		return result;
	}



}
