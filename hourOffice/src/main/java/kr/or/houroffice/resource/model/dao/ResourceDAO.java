package kr.or.houroffice.resource.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.houroffice.common.Page;
import kr.or.houroffice.project.model.vo.ProjectFileData;
import kr.or.houroffice.resource.model.vo.ResourceData;
import kr.or.houroffice.resource.model.vo.ResourcePage;

@Repository("resourceDAO")
public class ResourceDAO {

	public ArrayList<ResourceData> selectAllResource(SqlSessionTemplate sqlSession) {
		List fileList = (List) sqlSession.selectList("resource.selectAllResource");
		return (ArrayList<ResourceData>) fileList;
	}

	public int insertResource(ResourceData rd, SqlSessionTemplate sqlSession) {
		int result = sqlSession.insert("resource.insertResource", rd);
		return result;
	}

	public ArrayList<ResourceData> selectCategoryResource(String resourceType, SqlSessionTemplate sqlSession) {
		List fileList = (List) sqlSession.selectList("resource.selectCategoryResource", resourceType);
		return (ArrayList<ResourceData>) fileList;
	}

	public ArrayList<ResourceData> selectAllResourcePage(int recordCountPerPage, int currentPage, SqlSessionTemplate sqlSession) {
		ResourcePage rPage = new ResourcePage();
		rPage.setCurrentPage(currentPage);
		rPage.setRecordCountPerPage(recordCountPerPage);
		
		List fileList = (List) sqlSession.selectList("resource.selectAllResourcePage", rPage);
		return (ArrayList<ResourceData>) fileList;
	}
	
	
	public String getPageCateNavi(SqlSessionTemplate sqlSession, String resourceType, int currentPage, int recordCountPerPage,
			int naviCountPerPage) {

		int postTotalCount = postTotalCount(resourceType, sqlSession);

		
		int pageTotalCount;		//전체 페이지의 개수를 저장하는 변수
		
		if (postTotalCount % recordCountPerPage > 0) {
			pageTotalCount = postTotalCount / recordCountPerPage + 1;
		} else {
			pageTotalCount = postTotalCount / recordCountPerPage + 0;
		}
		int startNavi = ((currentPage - 1) / naviCountPerPage) * naviCountPerPage + 1;

		int endNavi = startNavi + naviCountPerPage - 1;

		if (endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		StringBuilder sb = new StringBuilder();

		if (startNavi != 1) {
			sb.append("<a href='/resourceCenter.ho?resourceType="+resourceType+"&currentPage=" + (startNavi - 1) + "'>< </a>");
		}

		for (int i = startNavi; i <= endNavi; i++) {
			if (i == currentPage) {
				sb.append("<a href='/resourceCenter.ho?resourceType="+resourceType+"&currentPage=" + i + "'><b>" + i + "</b> </a>");
			} else {
				sb.append("<a href='/resourceCenter.ho?resourceType="+resourceType+"&currentPage=" + i + "'>" + i + " </a>");
			}
		}
		if (endNavi != pageTotalCount) {
			sb.append("<a href='/resourceCenter.ho?resourceType="+resourceType+"&currentPage=" + (endNavi + 1) + "'>> </a>");
		}

		return sb.toString();
	}

	// 페이지의 총 갯수를 구하는 메소드
	public int postTotalCount(SqlSessionTemplate sqlSession) {
		int result = sqlSession.selectOne("resource.postTotalCount");
		return result;
	}
	
	public int postTotalCount(String resourceType, SqlSessionTemplate sqlSession) {
		int result = sqlSession.selectOne("resource.postCategoryCount", resourceType);
		return result;
	}

	public ArrayList<ResourceData> selectCategoryResourcePage(String resourceType, int recordCountPerPage,
			int currentPage, SqlSessionTemplate sqlSession) {
		ResourcePage rPage = new ResourcePage();
		rPage.setCurrentPage(currentPage);
		rPage.setRecordCountPerPage(recordCountPerPage);
		rPage.setCategory(resourceType);
		
		List fileList = (List) sqlSession.selectList("resource.selectCategoryResourcePage", rPage);
		return (ArrayList<ResourceData>) fileList;
	}

	public String getPageNavi(SqlSessionTemplate sqlSession, int currentPage, int recordCountPerPage,
			int naviCountPerPage) {
		int postTotalCount = postTotalCount(sqlSession);

		
		int pageTotalCount;		//전체 페이지의 개수를 저장하는 변수
		
		if (postTotalCount % recordCountPerPage > 0) {
			pageTotalCount = postTotalCount / recordCountPerPage + 1;
		} else {
			pageTotalCount = postTotalCount / recordCountPerPage + 0;
		}
		int startNavi = ((currentPage - 1) / naviCountPerPage) * naviCountPerPage + 1;

		int endNavi = startNavi + naviCountPerPage - 1;

		if (endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		StringBuilder sb = new StringBuilder();

		if (startNavi != 1) {
			sb.append("<a href='/resourceCenter.ho?resourceType=all&currentPage=" + (startNavi - 1) + "'>< </a>");
		}

		for (int i = startNavi; i <= endNavi; i++) {
			if (i == currentPage) {
				sb.append("<a href='/resourceCenter.ho?resourceType=all&currentPage=" + i + "'><b>" + i + "</b> </a>");
			} else {
				sb.append("<a href='/resourceCenter.ho?resourceType=all&currentPage=" + i + "'>" + i + " </a>");
			}
		}
		if (endNavi != pageTotalCount) {
			sb.append("<a href='/resourceCenter.ho?resourceType=all&currentPage=" + (endNavi + 1) + "'>> </a>");
		}

		return sb.toString();
	}

	public ResourceData selectOneResouceFile(int fileNo, SqlSessionTemplate sqlSession) {
		ResourceData rd = sqlSession.selectOne("resource.selectOneResouceFile", fileNo);
		return rd;
	}

	public int deleteResource(int fileNo, SqlSessionTemplate sqlSession) {
		int result = sqlSession.update("resource.deleteResource", fileNo);
		return result;
	}

	

}
