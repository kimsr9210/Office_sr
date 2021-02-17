package kr.or.houroffice.resource.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.or.houroffice.common.Page;
import kr.or.houroffice.common.PageList;
import kr.or.houroffice.project.model.vo.ProjectFileData;
import kr.or.houroffice.resource.model.dao.ResourceDAO;
import kr.or.houroffice.resource.model.vo.ResourceData;

@Service("resourceService")
public class ResourceService {
	
	@Autowired
	@Qualifier(value="resourceDAO")
	private ResourceDAO rDAO;
	
	@Autowired
	@Qualifier(value="sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	public ArrayList<ResourceData> selectAllResource() {
		ArrayList<ResourceData> fileList = rDAO.selectAllResource(sqlSession);
		return fileList;
	}

	public int insertProjectBoardFile(ResourceData rd) {
		int result = rDAO.insertResource(rd, sqlSession);
		return result;
	}

	public ArrayList<ResourceData> selectCategoryResource(String resourceType) {
		ArrayList<ResourceData> fileList = rDAO.selectCategoryResource(resourceType, sqlSession);
		return fileList;
	}

	public PageList selectAllResourcePage(int currentPage) {
		int recordCountPerPage = 10; // 한 페이지 당 몇개씩 보여줄 것이냐
		
		// 화면 만들기
		ArrayList<ResourceData> fileList = rDAO.selectAllResourcePage(recordCountPerPage, currentPage, sqlSession);

		// navi 값 보여주기
		int naviCountPerPage = 5; // navi 값 몇개 보여줄지
		String pageNavi = rDAO.getPageNavi(sqlSession, currentPage, recordCountPerPage, naviCountPerPage);

		// 리턴은 한번에 하나밖에 할 수 없기 때문에 2개의 데이터를 저장할 vo객체가 필요함

		PageList pl = new PageList();
		pl.setList(fileList);
		pl.setPageNavi(pageNavi);
		return pl;
	}

	public PageList selectCategoryResourcePage(int currentPage, String resourceType) {
		int recordCountPerPage = 10; // 한 페이지 당 몇개씩 보여줄 것이냐
		
		// 화면 만들기
		ArrayList<ResourceData> fileList = rDAO.selectCategoryResourcePage(resourceType, recordCountPerPage, currentPage, sqlSession);

		// navi 값 보여주기
		int naviCountPerPage = 5; // navi 값 몇개 보여줄지
		String pageNavi = rDAO.getPageCateNavi(sqlSession, resourceType, currentPage, recordCountPerPage, naviCountPerPage);

		// 리턴은 한번에 하나밖에 할 수 없기 때문에 2개의 데이터를 저장할 vo객체가 필요함

		PageList pl = new PageList();
		pl.setList(fileList);
		pl.setPageNavi(pageNavi);
		return pl;
	}

	public ResourceData selectOneResouceFile(int fileNo) {
		ResourceData rd = rDAO.selectOneResouceFile(fileNo, sqlSession);
		return rd;
	}

	public int deleteResource(int fileNo) {
		int result = rDAO.deleteResource(fileNo, sqlSession);
		return result;
	}

	
}
