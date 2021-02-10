package kr.or.houroffice.project.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.houroffice.project.model.vo.Project;
import kr.or.houroffice.project.model.vo.ProjectBoard;
import kr.or.houroffice.project.model.vo.ProjectCode;
import kr.or.houroffice.project.model.vo.ProjectComment;
import kr.or.houroffice.project.model.vo.ProjectFavorite;
import kr.or.houroffice.project.model.vo.ProjectFileData;
import kr.or.houroffice.project.model.vo.ProjectMember;
import kr.or.houroffice.project.model.vo.ProjectPlan;

@Repository("projectDAO")
public class ProjectDAO {

	// 프로젝트 생성
	public int insertProject(Project p, SqlSessionTemplate sqlSession) {
		int result = sqlSession.insert("project.insertProject",p);
		return result;
	}

	// 참여 프로젝트 목록
	public ArrayList<Project> selectAllProject(int memNo, SqlSessionTemplate sqlSession) {
		List myList = (List)sqlSession.selectList("project.selectAllProject", memNo);
		return (ArrayList<Project>)myList;
	}

	// 공개 프로젝트 목록
	public ArrayList<Project> selectPublicProject(SqlSessionTemplate sqlSession) {
		List publicList = (List)sqlSession.selectList("project.selectPublicProject");
		return (ArrayList<Project>)publicList;
	}

	public Project selectOneProject(int proNo, SqlSessionTemplate sqlSession) {
		Project p = sqlSession.selectOne("project.selectOneProject", proNo);
		return p;
	}

	public Project selectOneProjectSubject(String proSubject, SqlSessionTemplate sqlSession) {
		Project p = sqlSession.selectOne("project.selectOneProjectSubject", proSubject);
		return p;
	}

	public int insertProjectMemberAdmin(ProjectMember pm, SqlSessionTemplate sqlSession) {
		int result = sqlSession.insert("project.insertProjectMemberAdmin", pm);
		return result;
		
	}

	public ArrayList<ProjectMember> selectProjectMemberList(int proNo, SqlSessionTemplate sqlSession) {
		List projectMemberList = (List)sqlSession.selectList("project.selectProjectMemberList", proNo);
		return (ArrayList<ProjectMember>)projectMemberList;
	}

	public int insertProjectBoard(ProjectBoard pb, SqlSessionTemplate sqlSession) {
		int result = sqlSession.insert("project.insertProjectBoard", pb);
		return result;
		
	}

	public ArrayList<ProjectBoard> selectProjectBoardList(int proNo, SqlSessionTemplate sqlSession) {
		List boardList = (List)sqlSession.selectList("project.selectProjectBoardList", proNo);
		return (ArrayList<ProjectBoard>)boardList;
	}


	public ArrayList<Project> selectMyProjectList(int memNo, SqlSessionTemplate sqlSession) {
		List myProjectList = (List)sqlSession.selectList("project.selectMyProjectList", memNo);
		return (ArrayList<Project>)myProjectList;
	}

	public int insertProjectFavor(ProjectFavorite pf, SqlSessionTemplate sqlSession) {
		int result = sqlSession.insert("project.insertProjectFavor", pf);
		return result;
	}

	public ArrayList<Project> selectProjectFavoriteList(int memNo, SqlSessionTemplate sqlSession) {
		List favoriteList = (List)sqlSession.selectList("project.selectProjectFavoriteList", memNo);
		return (ArrayList<Project>)favoriteList;
	}

	public int deleteProjectFavor(ProjectFavorite pf, SqlSessionTemplate sqlSession) {
		int result = sqlSession.insert("project.deleteProjectFavor", pf);
		return result;
	}

	public int insertBoardComment(ProjectComment pc, SqlSessionTemplate sqlSession) {
		int result = sqlSession.insert("project.insertBoardComment", pc);
		return result;
	}

	public ArrayList<ProjectComment> selectBoardCommentList(int proNo, SqlSessionTemplate sqlSession) {
		List commentList = (List)sqlSession.selectList("project.selectBoardCommentList", proNo);
		return (ArrayList<ProjectComment>)commentList;
	}

	public int updateProjectComment(ProjectComment pc, SqlSessionTemplate sqlSession) {
		int result = sqlSession.update("project.updateProjectComment", pc);
		return result;
	}

	public int deleteProjectComment(int commentNo, SqlSessionTemplate sqlSession) {
		int result = sqlSession.update("project.deleteProjectComment", commentNo);
		return result;
	}

	public int updateProject(Project p, SqlSessionTemplate sqlSession) {
		int result = sqlSession.update("project.updateProject",p);
		return result;
	}

	public int updateProjectBoard(ProjectBoard pb, SqlSessionTemplate sqlSession) {
		int result = sqlSession.update("project.updateProjectBoard",pb);
		return result;
	}

	public int deleteProjectBoard(int boardNo, SqlSessionTemplate sqlSession) {
		int result = sqlSession.update("project.deleteProjectBoard",boardNo);
		return result;
	}

	public int updateProjectMemberExit(ProjectMember pm, SqlSessionTemplate sqlSession) {
		int result = sqlSession.update("project.updateProjectMemberExit",pm);
		return result;
	}

	public int deleteProject(int proNo, SqlSessionTemplate sqlSession) {
		int result = sqlSession.update("project.deleteProject",proNo);
		return result;
	}

	public int updateProjectMgrSet(ProjectMember pm, SqlSessionTemplate sqlSession) {
		int result = sqlSession.update("project.updateProjectMgrSet",pm);
		return result;
	}

	public int updateProjectMgrCancel(ProjectMember pm, SqlSessionTemplate sqlSession) {
		int result = sqlSession.update("project.updateProjectMgrCancel",pm);
		return result;
	}

	public int insertProjectPlan(ProjectPlan pp, SqlSessionTemplate sqlSession) {
		int result = sqlSession.insert("project.insertProjectPlan",pp);
		return result;
	}

	public int updateProjectComplate(Project p, SqlSessionTemplate sqlSession) {
		int result = sqlSession.insert("project.updateProjectComplate",p);
		return result;
	}

	//BY 진원 - 한 게시물에 대한 댓글 목록
	public ArrayList<ProjectComment> selectOneBoardComment(SqlSessionTemplate sqlSession, int boardNo) {
		List commentList = sqlSession.selectList("project.selectOneBoardComment", boardNo);
		return (ArrayList<ProjectComment>) commentList;
  }
  
	public int insertProjectBoardFile(ProjectFileData pfd, SqlSessionTemplate sqlSession) {
		int result = sqlSession.insert("project.insertProjectBoardFile",pfd);
		return result;
	}

	public int insertProjectCode(ProjectCode pc, SqlSessionTemplate sqlSession) {
		int result = sqlSession.insert("project.insertProjectCode",pc);
		return result;
	}

	public ArrayList<ProjectCode> selectProjectCodeList(int proNo, SqlSessionTemplate sqlSession) {
		List codeList = sqlSession.selectList("project.selectProjectCodeList", proNo);
		return (ArrayList<ProjectCode>)codeList;
	}

	

	

}
