package kr.or.houroffice.mail.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.or.houroffice.common.PageList;
import kr.or.houroffice.mail.model.dao.MailDAO;
import kr.or.houroffice.mail.model.vo.MailFile;
import kr.or.houroffice.mail.model.vo.MailGetter;
import kr.or.houroffice.mail.model.vo.MailInfo;
import kr.or.houroffice.mail.model.vo.MailList;
import kr.or.houroffice.mail.model.vo.MailListPage;
import kr.or.houroffice.mail.model.vo.MailReceive;
import kr.or.houroffice.mail.model.vo.SendingMail;
import kr.or.houroffice.member.model.vo.Member;

@Service("mailService")
public class MailServiceImpl {

	@Autowired
	@Qualifier("mailDAO")
	private MailDAO mailDAO;
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	public ArrayList<Member> selectMemberAll() {
		return mailDAO.selectMemberAll(sqlSession);
	}
	
	public ArrayList<Member> selectDeptMember(String deptCode) {
		return mailDAO.selectMemberAll(sqlSession,deptCode);
	}

	public int insertMail(SendingMail sm) throws IllegalStateException, IOException {
		return mailDAO.insertMail(sqlSession, sm);
	}

	public PageList selectMailList(MailListPage mlp) {
		ArrayList<MailGetter> list = null;
		if(mlp.getListType()=='S') list = mailDAO.selectSendMailList(sqlSession, mlp);
		else list = mailDAO.selectMailList(sqlSession,mlp);//R,F,K,D
		
		String pageNavi = mailDAO.getPageNavi(sqlSession,mlp); //페이지 네비
		PageList pl = new PageList();
		pl.setList(list);
		pl.setPageNavi(pageNavi);
		return pl;
	}

	public HashMap<String, Integer> selectMailCount(MailListPage mlp) {
		return mailDAO.selectMailCount(sqlSession, mlp);
	}

	public int updateReadYN(MailReceive mr) {
		return mailDAO.updateReadYN(sqlSession, mr);
	}

	public int updateKeepYN(MailReceive mr) {
		return mailDAO.updateKeepYN(sqlSession,mr);
	}

	public MailGetter selectOneMail(MailReceive mr) {
		return mailDAO.selectOneMail(sqlSession,mr);
	}

	public int deleteMail(MailInfo mi) {
		return mailDAO.deleteMail(sqlSession, mi);
	}

	public MailGetter selectTransferMail(int mailNo) {
		return mailDAO.selectTransferMail(sqlSession,mailNo);
	}

	public MailFile selectOneFile(int attachNo) {
		return mailDAO.selectOneFile(sqlSession, attachNo);
	}

	public int insertResendMail(MailInfo mi) {
		return mailDAO.insertResendMail(sqlSession, mi);
	}

	public int deleteMailList(MailList ml) {
		return mailDAO.deleteMailList(sqlSession, ml);
	}

	public int updateRestoreMailList(MailList ml) {
		return mailDAO.updateRestoreMailList(sqlSession, ml);
	}

	public int deletePermMailList(MailList ml) {
		return mailDAO.deletePermMailList(sqlSession, ml);
	}

	public int updateReadMailList(MailList ml) {
		return mailDAO.updateReadMailList(sqlSession, ml);
	}

}
