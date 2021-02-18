package kr.or.houroffice.mail.model.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import kr.or.houroffice.mail.model.vo.MailFile;
import kr.or.houroffice.mail.model.vo.MailGetter;
import kr.or.houroffice.mail.model.vo.MailInfo;
import kr.or.houroffice.mail.model.vo.MailList;
import kr.or.houroffice.mail.model.vo.MailListPage;
import kr.or.houroffice.mail.model.vo.MailReceive;
import kr.or.houroffice.mail.model.vo.SendingMail;
import kr.or.houroffice.member.model.vo.Member;

@Repository("mailDAO")
public class MailDAO {

	@Autowired
	private ResourceLoader resourceLoader;
	
	public ArrayList<Member> selectMemberAll(SqlSessionTemplate sqlSession) {
		List list =  sqlSession.selectList("mail.selectMemberAll");
		return (ArrayList<Member>) list;
	}
	
	public ArrayList<Member> selectMemberAll(SqlSessionTemplate sqlSession, String deptCode) {
		List list =  sqlSession.selectList("mail.selectMemberAll",deptCode);
		return (ArrayList<Member>) list;
	}

	public int insertMail(SqlSessionTemplate sqlSession, SendingMail sm) throws IllegalStateException, IOException {
		//넣어야 할 테이블 4개
		MultipartFile mpf = sm.getMailFile(); //받아온 파일 객체
		
		if(mpf.getOriginalFilename().length()>0) sm.setFileYN('Y');//파일이 있으면

		int result1 = sqlSession.insert("mail.insertMail", sm);
		int result2 = sqlSession.insert("mail.insertMailReceive", sm); //mailNo, memNo
		
		int result3 = 1;
		int result4 = 1;
		if(sm.getMemRef()!=null){//참조가 있으면
			sqlSession.insert("mail.insertMailRef",sm); //mailNo, memNo
		}
		
		if(mpf.getOriginalFilename().length()>0){//파일이 있으면
			MailFile mf = new MailFile(); //파일 정보를 담을 객체
			
			String uploadPath = "resources/file/mail"; //파일 저장 위치
			String realUploadPath = resourceLoader.getResource("/").getURI().getPath(); //webapp까지의 실제 위치
			String originalFileName = mpf.getOriginalFilename(); 
			
			//원본 파일의 이름바꾸는 작업(바꾸는 파일의 이름은 시간값_ho)
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			long currentTime = Calendar.getInstance().getTimeInMillis();
			Timestamp uploadTime = Timestamp.valueOf(formatter.format(currentTime));
			
			File file = new File(realUploadPath + uploadPath+ "\\"+originalFileName); //해당 경로에서 파일 생성
			
			file.renameTo(new File(realUploadPath+ uploadPath+"\\"+currentTime+"_ho"));//파일 이름 수정
			String changedFileName = currentTime+"_ho";
			File reNameFile = new File(realUploadPath+ uploadPath+"\\"+changedFileName);//파일이름이 변경되면 새롭게 연결하는 파일 객체가 필요.
			//String filePath = reNameFile.getPath(); //원래 절대경로
			String filePath = uploadPath+"\\"+changedFileName; //DB에 상대경로로 저장하기 위함.

			long fileSize = mpf.getSize();//업로드된 file의 사이즈
			mpf.transferTo(reNameFile); //실제 위치에 파일 저장
			
			mf.setMailNo(sm.getMailNo());
			mf.setOriginalFilename(originalFileName);
			mf.setChangedFilename(changedFileName);
			mf.setFilePath(filePath);
			mf.setFileSize(fileSize);
			mf.setUploadDate(uploadTime);
			
			sqlSession.insert("mail.insertMailFile", mf); // mailNo, file데이터
		}
		
		if(result1>0 && result2>0 && result3>0 && result4>0) return 1;
		else return 0;
	}

	public ArrayList<MailGetter> selectMailList(SqlSessionTemplate sqlSession, MailListPage mlp) {
		List list = sqlSession.selectList("mail.selectMailList", mlp);
		return (ArrayList<MailGetter>)list;
	}

	public ArrayList<MailGetter> selectSendMailList(SqlSessionTemplate sqlSession, MailListPage mlp) {
		List list = sqlSession.selectList("mail.selectSendMailList",mlp);
		return (ArrayList<MailGetter>)list;
	}

	public String getPageNavi(SqlSessionTemplate sqlSession, MailListPage mlp) {
		String url = mlp.getUrl();
		int count = 0 ;
		if(mlp.getListType()=='S'){
			count = sqlSession.selectOne("mail.sendPostTotalCount",mlp);
		}else{ //R,F,K,D
			count = sqlSession.selectOne("mail.postTotalCount", mlp); 
		}

		mlp.setPostTotalCount(count);
		int startNavi = mlp.getStartNavi();
		int endNavi = mlp.getEndNavi();
		
		String search ="";
		if(mlp.getKeyword()!=null){
			search = "&searchType="+mlp.getSearchType()+"&keyword="+mlp.getKeyword(); 
		}
		
		StringBuilder sb = new StringBuilder();
		// < 버튼 출력
		if(startNavi !=1) {
			sb.append("<li class='page-list'><a href='"+url+"&page="+(startNavi-1)+search+"' class='page-link'>◀</a></li>");
		}
		
		for(int i=startNavi; i<=endNavi; i++) {
			if(i==mlp.getCurrentPage()) {
				sb.append("<li class='page-list'><a href='"+url+"&page="+i+search+"' class='page-link'><b>"+i+"</b></a></li>");
			}else {
				sb.append("<li class='page-list'><a href='"+url+"&page="+i+search+"' class='page-link'>"+i+"</a></li>");
			}
		}
		// > 버튼 출력
		if(endNavi != mlp.getPageTotalCount()) {
			sb.append("<li class='page-list'><a href='"+url+"&page="+(endNavi-1)+search+"' class='page-link'>◀</a></li>");
		}
		return sb.toString();
	}

	public HashMap<String, Integer> selectMailCount(SqlSessionTemplate sqlSession, MailListPage mlp) {
		HashMap<String, Integer> mailCount = new HashMap<String, Integer>(); //listType에 따라 sql이 바뀜.
		int allCount = 0;
		int nonReadCount = 0;
		
		if(mlp.getListType()=='S'){
			allCount = sqlSession.selectOne("mail.sendPostTotalCount",mlp);
		}else{// R,F,K,D
			allCount = sqlSession.selectOne("mail.postTotalCount", mlp);
			nonReadCount = sqlSession.selectOne("mail.nonReadCount",mlp);
		}
		mailCount.put("allCount", allCount);
		mailCount.put("nonReadCount", nonReadCount);
		
		return mailCount;
	}

	public int updateReadYN(SqlSessionTemplate sqlSession, MailReceive mr) {
		return sqlSession.update("mail.updateReadYN", mr);
	}

	public int updateKeepYN(SqlSessionTemplate sqlSession, MailReceive mr) {
		return sqlSession.update("mail.updateKeepYN", mr);
	}

	public MailGetter selectOneMail(SqlSessionTemplate sqlSession, MailReceive mr) {
		MailGetter mg = sqlSession.selectOne("mail.selectOneMail",mr);
		return mg;
	}

	public int deleteMail(SqlSessionTemplate sqlSession, MailInfo mi) {
		return sqlSession.update("mail.deleteMail", mi);
	}

	public MailGetter selectTransferMail(SqlSessionTemplate sqlSession, int mailNo) {
		MailGetter mg = sqlSession.selectOne("mail.selectTransferMail", mailNo);
		return mg;
	}

	public MailFile selectOneFile(SqlSessionTemplate sqlSession, int attachNo) {
		MailFile mf = sqlSession.selectOne("mail.selectOneFile",attachNo);
		return mf;
	}

	public int insertResendMail(SqlSessionTemplate sqlSession, MailInfo mi) {
		//메일을 복사해서 보내고, 다시 보내는 로직으로 바뀌어야 함. 필요한거 메일번호 보내는 사람(로그인한사람).
		int[] mailNoList = mi.getMailNoList();
		
		int resultM = 0;
		int resultC = 0; 
		int resultF = 0;
		int resultMF = 0;
		
		for(int mailNo : mailNoList){
			mi.setSdMailNo(mailNo); //정보는 같고 메일 번호만 바뀜.
			resultM = sqlSession.insert("mail.insertResendMail", mi);
			resultC += sqlSession.insert("mail.insertResendMailRec",mi);
			resultF += sqlSession.insert("mail.insertResendMailRef",mi);
			resultMF +=sqlSession.insert("mail.insertResendMailFile",mi);
		}
		
		if(resultM + resultC+resultF+resultMF>1) return 1;//메일과 수신인 1명해서 2가 최소
		return 0;
	}

	public int deleteMailList(SqlSessionTemplate sqlSession, MailList ml) {
		return sqlSession.update("mail.deleteMailList",ml);
	}

	public int updateRestoreMailList(SqlSessionTemplate sqlSession, MailList ml) {
		return sqlSession.update("mail.restoreMailList",ml);
	}

	public int deletePermMailList(SqlSessionTemplate sqlSession, MailList ml) {
		return sqlSession.delete("mail.deletePermMailList",ml);
	}

	public int updateReadMailList(SqlSessionTemplate sqlSession, MailList ml) {
		return sqlSession.update("mail.updateReadMailList",ml);
	}
}
