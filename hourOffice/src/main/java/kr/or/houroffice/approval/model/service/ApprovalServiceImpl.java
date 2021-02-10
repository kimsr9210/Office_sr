package kr.or.houroffice.approval.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.or.houroffice.approval.model.dao.ApprovalDAO;
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
import kr.or.houroffice.common.PageList;
import kr.or.houroffice.member.model.vo.Member;

@Service("ApprovalService")
public class ApprovalServiceImpl implements ApprovalService {
	
	@Autowired
	@Qualifier(value="ApprovalDAO")
	private ApprovalDAO aprDAO;

	@Autowired
	@Qualifier(value="sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	public PageList selectApprovalList(AprListPage alp, char listType) {
		ArrayList<Approval> list = null;
		switch(listType){
		case 'R': list = aprDAO.selectApprovalListRequest(sqlSession,alp);
			break;
		case 'W': list = aprDAO.selectApprovalListWait(sqlSession,alp);
			break;
		case 'F': list = aprDAO.selectApprovalListRef(sqlSession,alp);
			break;
		case 'D': list = aprDAO.selectApprovalListDept(sqlSession,alp);
			break;
		}
		String pageNavi = aprDAO.getPageNavi(sqlSession,alp,listType); //페이지 네비
		PageList pl = new PageList();
		pl.setList(list);
		pl.setPageNavi(pageNavi);
		return pl;
	}
	

	public ArrayList<AprLineMember> selectMyDeptPeople(Member m) {
		ArrayList<AprLineMember> list = aprDAO.selectMyDeptPeople(sqlSession,m);
		return list;
	}

	public ArrayList<AprLineMember> selectCCCLinePeople(Member m) {
		ArrayList<AprLineMember> list = aprDAO.selectCCCLinePeople(sqlSession,m);
		return list;
	}
	
	// BY 진원  - 자신이 결재해야할 문서개수 조회
	public int selectMyAprLineCount(int memNo){
		return aprDAO.selectMyAprLineCount(sqlSession, memNo);
	}

	public int insertAprCommon(AprForm af){ //결재 입력 공통 호출
		//기본 결재 정보
		int docuNo = aprDAO.insertApproval(sqlSession, af);
		if(docuNo>0){
			ApprovalLine al = new ApprovalLine();
			ApprovalRef ar = new ApprovalRef();
			
			al.setDocuNo(docuNo);
			ar.setDocuNo(docuNo);
			
			//결재선
			int[] aprLine = af.getAprLine();
			if(aprLine != null){
				for(int i=0; i<aprLine.length;i++){
					al.setMemNo(aprLine[i]);
					int result = aprDAO.insertApprovalLine(sqlSession,al);
				}
			}
			//결재 참조
			int[] aprRef = af.getAprRef();
			if(aprRef != null){
				for(int i=0; i<aprRef.length;i++){
					ar.setMemNo(aprRef[i]);
					int result = aprDAO.insertApprovalRef(sqlSession,ar);
				}
			}
		}
		return docuNo;
	}

	public int insertAprForm(AprFormHol afh) {
		int docuNo = insertAprCommon(afh);
		afh.setDocuNo(docuNo);
		
		if(docuNo>0){
			//개별 양식 정보
			int result = aprDAO.insertHolidayForm(sqlSession, afh);
			
			if(result>0){
				return 1;
			}else {
				return 0;
			}
		}
		return 0;
	}
	
	public int insertAprForm(AprFormLazy afl) {
		int docuNo = insertAprCommon(afl);
		afl.setDocuNo(docuNo);
		
		if(docuNo>0){
			//개별 양식 정보
			int result = aprDAO.insertLazinessForm(sqlSession, afl);
			
			if(result>0){
				return 1;
			}else {
				return 0;
			}
		}
		return 0;
	}

	public int insertAprForm(AprFormOvt afo) {
		int docuNo = insertAprCommon(afo);
		afo.setDocuNo(docuNo);
		
		if(docuNo>0){
			//개별 양식 정보
			int result = aprDAO.insertOvertimeForm(sqlSession, afo);
			
			if(result>0){
				return 1;
			}else {
				return 0;
			}
		}
		return 0;
	}

	public int insertAprForm(AprFormCCC afc) {
		int docuNo = insertAprCommon(afc);
		afc.setDocuNo(docuNo);
		
		if(docuNo>0){
			//개별 양식 정보
			int result = aprDAO.insertCCCForm(sqlSession, afc);
			
			if(result>0){
				return 1;
			}else {
				return 0;
			}
		}
		return 0;
	}

	public AprFormHol selectOneAprHol(int docuNo) {
		AprFormHol afh = aprDAO.selectOneAprHol(sqlSession, docuNo);
		return afh;
	}

	public AprFormOvt selectOneAprOvt(int docuNo) {
		AprFormOvt afo = aprDAO.selectOneAprOvt(sqlSession, docuNo);
		return afo;
	}

	public AprFormLazy selectOneAprLazy(int docuNo) {
		AprFormLazy afl = aprDAO.selectOneAprLazy(sqlSession, docuNo);
		return afl;
	}

	public AprFormCCC selectOneAprCCC(int docuNo) {
		AprFormCCC afc = aprDAO.selectOneAprCCC(sqlSession, docuNo);
		return afc;
	}

	public ArrayList<ApprovalLine> selectOneAprLine(int docuNo) { //결재문서에 해당하는 결재선 정보
		ArrayList<ApprovalLine> list = aprDAO.selectOneAprLine(sqlSession, docuNo);
		return list;
	}

	public ArrayList<ApprovalRef> selectOneAprRef(int docuNo) {
		ArrayList<ApprovalRef> list = aprDAO.selectOneAprRef(sqlSession, docuNo);
		return list;
	}
	
	public int insertAprMark(ApprovalLine al, char docuType) { //결재 승인 or 반려 처리
		int result = aprDAO.insertAprMark(sqlSession, al, docuType);
		return result;
	}

	public int deleteApproval(Approval apr) {
		int result = aprDAO.deleteApproval(sqlSession, apr);
		return result;
	}

	public int updateAprCommon(AprForm af){
		ApprovalLine al = new ApprovalLine();
		ApprovalRef ar = new ApprovalRef();
		
		al.setDocuNo(af.getDocuNo());
		ar.setDocuNo(af.getDocuNo());
		
		//기존 데이터 지우기
		int alResult = aprDAO.deleteApprovalLine(sqlSession, af.getDocuNo());
		int arResult = aprDAO.deleteApprovalRef(sqlSession, af.getDocuNo());
		
		//개별 데이터 추가
		//결재선
		int[] aprLine = af.getAprLine();
		if(aprLine != null){
			for(int i=0; i<aprLine.length;i++){
				al.setMemNo(aprLine[i]);
				aprDAO.insertApprovalLine(sqlSession,al);
			}
		}
		//결재 참조
		int[] aprRef = af.getAprRef();
		if(aprRef != null){
			for(int i=0; i<aprRef.length;i++){
				ar.setMemNo(aprRef[i]);
				aprDAO.insertApprovalRef(sqlSession,ar);
			}
		}
		if(alResult+arResult>1){
			return 1;
		}
		return 0;
	}
	
	public int updateAprForm(AprFormHol afh) {
		//결재선, 결재참조 수정, form 업데이트
		int result = aprDAO.updateAprForm(sqlSession, afh);
		int result2 = updateAprCommon(afh);
		
		if(result+result2>1){
			return 1;
		}
		return 0;
	}

	public int updateAprForm(AprFormCCC afc) {
		int result = aprDAO.updateAprForm(sqlSession, afc);
		int result2 = updateAprCommon(afc);
		if(result+result2>1){
			return 1;
		}
		return 0;
	}

	public int updateAprForm(AprFormLazy afl) {
		int result = aprDAO.updateAprForm(sqlSession, afl);
		int result2 = updateAprCommon(afl);
		if(result+result2>1){
			return 1;
		}
		return 0;
	}

	public int updateAprForm(AprFormOvt afo) {
		int result = aprDAO.updateAprForm(sqlSession, afo);
		int result2 = updateAprCommon(afo);
		if(result+result2>1){
			return 1;
		}
		return 0;
	}


	public int updateCardType(CCCForm cf) {
		int result = aprDAO.updateCardType(sqlSession, cf);
		return result;
	}

}
