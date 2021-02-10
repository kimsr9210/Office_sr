package kr.or.houroffice.approval.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.houroffice.approval.model.vo.Approval;
import kr.or.houroffice.approval.model.vo.ApprovalLine;
import kr.or.houroffice.approval.model.vo.ApprovalRef;
import kr.or.houroffice.approval.model.vo.AprForm;
import kr.or.houroffice.approval.model.vo.AprFormCCC;
import kr.or.houroffice.approval.model.vo.AprFormHol;
import kr.or.houroffice.approval.model.vo.AprFormLazy;
import kr.or.houroffice.approval.model.vo.AprFormOvt;
import kr.or.houroffice.approval.model.vo.AprLineMember;
import kr.or.houroffice.approval.model.vo.AprListPage;
import kr.or.houroffice.approval.model.vo.CCCForm;
import kr.or.houroffice.member.model.vo.Member;

@Repository("ApprovalDAO")
public class ApprovalDAO {

	public ArrayList<Approval> selectApprovalListRequest(SqlSessionTemplate sqlSession, AprListPage alp) {
		List list = sqlSession.selectList("approval.selectApprovalListRequest",alp);
		return (ArrayList<Approval>)list;
	}
	
	public ArrayList<Approval> selectApprovalListWait(SqlSessionTemplate sqlSession, AprListPage alp) {
		List list = sqlSession.selectList("approval.selectApprovalListWait",alp);
		return (ArrayList<Approval>)list;
	}

	public ArrayList<Approval> selectApprovalListRef(SqlSessionTemplate sqlSession, AprListPage alp) {
		List list = sqlSession.selectList("approval.selectApprovalListRef",alp);
		return (ArrayList<Approval>)list;
	}
	
	public ArrayList<Approval> selectApprovalListDept(SqlSessionTemplate sqlSession, AprListPage alp) {
		List list = sqlSession.selectList("approval.selectApprovalListDept",alp);
		return (ArrayList<Approval>)list;
	}
	
	public ArrayList<AprLineMember> selectMyDeptPeople(SqlSessionTemplate sqlSession, Member m) {
		List list = sqlSession.selectList("approval.selectMyDeptPeople",m);
		return (ArrayList<AprLineMember>)list;
	}

	public ArrayList<AprLineMember> selectCCCLinePeople(SqlSessionTemplate sqlSession, Member m) {
		List list = sqlSession.selectList("approval.selectCCCLinePeople",m);
		return (ArrayList<AprLineMember>)list;
	}

	public int selectMyAprLineCount(SqlSessionTemplate sqlSession, int memNo) {
		int count = sqlSession.selectOne("approval.selectMyAprLineCount",memNo);
		return count;
  }
	public int insertApproval(SqlSessionTemplate sqlSession, AprForm af) {//결재문서 입력
		int result = sqlSession.insert("approval.insertApproval",af);
		if(result>0){
			return af.getDocuNo();
		}
		return result;
	}

	public int insertApprovalLine(SqlSessionTemplate sqlSession, ApprovalLine al) {
		int result = sqlSession.insert("approval.insertApprovalLine",al);
		return result;
	}

	public int insertApprovalRef(SqlSessionTemplate sqlSession, ApprovalRef ar) {
		int result =  sqlSession.insert("approval.insertApprovalRef", ar);
		return result;
	}

	public int insertHolidayForm(SqlSessionTemplate sqlSession, AprFormHol afh) {
		int result = sqlSession.insert("approval.insertHolidayForm",afh);
		return result;
	}

	public int insertLazinessForm(SqlSessionTemplate sqlSession, AprFormLazy afl) {
		int result = sqlSession.insert("approval.insertLazinessForm",afl);
		return result;
	}

	public int insertOvertimeForm(SqlSessionTemplate sqlSession, AprFormOvt afo) {
		int result = sqlSession.insert("approval.insertOvertimeForm",afo);
		return result;
	}

	public int insertCCCForm(SqlSessionTemplate sqlSession, AprFormCCC afc) {
		int result = sqlSession.insert("approval.insertCCCForm",afc);
		return result;
	}

	public String getPageNavi(SqlSessionTemplate sqlSession, AprListPage alp, char listType) {
		String url = alp.getUrl();
		int count = 0 ;
		switch(listType){
		case 'R': count = sqlSession.selectOne("approval.postTotalCount", alp);
			break;
		case 'W': count = sqlSession.selectOne("approval.postTotalCountWait", alp);
			break;
		case 'F': count = sqlSession.selectOne("approval.postTotalCountRef", alp);
			break;
		case 'D': count = sqlSession.selectOne("approval.postTotalCount", alp);
			break;
		}
		alp.setPostTotalCount(count);
		int startNavi = alp.getStartNavi();
		int endNavi = alp.getEndNavi();
		
		String filter = "";
		if(alp.getAprStatus()!=null){
			filter = "&aprStatus="+alp.getAprStatus();
		}
		
		String search ="";
		if(alp.getKeyword()!=null){
			search = "&searchType="+alp.getSearchType()+"&keyword="+alp.getKeyword(); 
		}
		
		StringBuilder sb = new StringBuilder();
		// < 버튼 출력
		if(startNavi !=1) {
			sb.append("<li class='page-list'><a href='"+url+"&page="+(startNavi-1)+filter+search+"' class='page-link'>◀</a></li>");
		}
		
		for(int i=startNavi; i<=endNavi; i++) {
			if(i==alp.getCurrentPage()) {
				sb.append("<li class='page-list'><a href='"+url+"&page="+i+filter+search+"' class='page-link'><b>"+i+"</b></a></li>");
			}else {
				sb.append("<li class='page-list'><a href='"+url+"&page="+i+filter+search+"' class='page-link'>"+i+"</a></li>");
			}
		}
		// > 버튼 출력
		if(endNavi != alp.getPageTotalCount()) {
			sb.append("<li class='page-list'><a href='"+url+"&page="+(endNavi-1)+filter+search+"' class='page-link'>◀</a></li>");
		}
		return sb.toString();
	}

	public AprFormHol selectOneAprHol(SqlSessionTemplate sqlSession, int docuNo) {
		AprFormHol afh = sqlSession.selectOne("approval.selectOneAprHol",docuNo);
		return afh;
	}

	public AprFormOvt selectOneAprOvt(SqlSessionTemplate sqlSession, int docuNo) {
		AprFormOvt afo = sqlSession.selectOne("approval.selectOneAprOvt",docuNo);
		return afo;
	}

	public AprFormLazy selectOneAprLazy(SqlSessionTemplate sqlSession, int docuNo) {
		AprFormLazy afl = sqlSession.selectOne("approval.selectOneAprLazy",docuNo);
		return afl;
	}

	public AprFormCCC selectOneAprCCC(SqlSessionTemplate sqlSession, int docuNo) {
		AprFormCCC afc = sqlSession.selectOne("approval.selectOneAprCCC",docuNo);
		return afc;
	}

	public ArrayList<ApprovalLine> selectOneAprLine(SqlSessionTemplate sqlSession, int docuNo) {
		List list = sqlSession.selectList("approval.selectOneAprLine",docuNo);
		return (ArrayList<ApprovalLine>)list;
	}

	public int insertAprMark(SqlSessionTemplate sqlSession, ApprovalLine al, char docuType) {
		int result1 = sqlSession.update("approval.insertAprMark",al); //결재선 처리
		int lineCount = sqlSession.selectOne("approval.getLineCount", al.getDocuNo()); //결재선 갯수 파악
		al.setLineCount(lineCount);
		int refusalCount = sqlSession.selectOne("approval.getRefusalCount", al.getDocuNo()); //결재선 갯수 파악
		al.setRefusalCount(refusalCount);
		int result2 = sqlSession.update("approval.updateFinalDate", al); //최종 결재일 변경
		
		int result3 =1;
		int result4 =1;
		if(lineCount==0 && docuType=='H'){ //결재처리가 완료되었으면 & 연차 신청서인 경우에만
			//문서번호를 조회해서 연차 정보를 가져다가 연차내역에 추가하고 멤버 연차 갯수에 변경
			AprFormHol afh = selectOneAprHol(sqlSession, al.getDocuNo()); //문서정보 받아오기
			result3 = sqlSession.insert("approval.insertHolidayList", afh);//연차 내역 추가
			result4 = sqlSession.update("approval.updateHolidayCount",afh); //연차 갯수 변경
		}
		
		if(result1+result2+result3+result4>3){//모든 결과가 1이상이 나와야만 문제가 없음.
			return 1;
		}return 0;
	}

	public int deleteApproval(SqlSessionTemplate sqlSession, Approval apr) {
		int result = sqlSession.update("approval.deleteApproval",apr);
		return result;
	}

	public ArrayList<ApprovalRef> selectOneAprRef(SqlSessionTemplate sqlSession, int docuNo) {
		List list = sqlSession.selectList("approval.selectOneAprRef",docuNo);
		return (ArrayList<ApprovalRef>)list;
	}

	public int updateAprForm(SqlSessionTemplate sqlSession, AprFormHol afh) {
		int result = sqlSession.update("approval.updateAprFormHol",afh);
		return result;
	}

	public int updateAprForm(SqlSessionTemplate sqlSession, AprFormCCC afc) {
		int result = sqlSession.update("approval.updateAprFormCCC",afc);
		return result;
	}

	public int updateAprForm(SqlSessionTemplate sqlSession, AprFormLazy afl) {
		int result = sqlSession.update("approval.updateAprFormLazy",afl);
		return result;
	}

	public int updateAprForm(SqlSessionTemplate sqlSession, AprFormOvt afo) {
		int result = sqlSession.update("approval.updateAprFormOvt",afo);
		return result;
	}

	public int deleteApprovalLine(SqlSessionTemplate sqlSession, int docuNo) {
		int result = sqlSession.delete("approval.deleteAprLine", docuNo);
		return result;
	}

	public int deleteApprovalRef(SqlSessionTemplate sqlSession, int docuNo) {
		int result = sqlSession.delete("approval.deleteAprRef", docuNo);
		return result;
	}

	public int updateCardType(SqlSessionTemplate sqlSession, CCCForm cf) {
		int result = sqlSession.update("approval.updateCardType",cf);
		return result;
	}

}
