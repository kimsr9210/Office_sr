package kr.or.houroffice.resource.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.or.houroffice.common.PageList;
import kr.or.houroffice.member.model.service.MemberService;
import kr.or.houroffice.member.model.vo.Member;
import kr.or.houroffice.project.model.vo.ProjectFileData;
import kr.or.houroffice.resource.model.service.ResourceService;
import kr.or.houroffice.resource.model.vo.ResourceData;

@Controller
public class ResourceController {
	
	@Autowired
	@Qualifier(value="resourceService")
	private ResourceService rService;
	
	@Autowired
	@Qualifier(value="memberService")
	private MemberService mService;
	
	@RequestMapping("/resourceCenter.ho")
	public ModelAndView resourceCenter(HttpServletRequest request ,@RequestParam String resourceType){
		
		int currentPage;
		
		if (request.getParameter("currentPage") == null) {
			currentPage = 1;
		} else {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		
		PageList pl = null;
		
		if (resourceType.equals("all")) {
			pl = rService.selectAllResourcePage(currentPage);
		}else{
			pl = rService.selectCategoryResourcePage(currentPage, resourceType);
		}
		
		ArrayList<Member> allMember = mService.selectAllMemberList();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("pl", pl);
		mav.addObject("resourceType", resourceType);
		mav.addObject("allMember", allMember);
		mav.setViewName("resourceCenter/resourceCenter");
		return mav;
	}
	
	// 일반 게시글 올리기
	
	@RequestMapping(value = "/insertResource.ho")
	public ModelAndView insertResource(HttpServletRequest request, HttpSession session) throws Exception {

		// 업로드 유저 ID 값 가져오기(session에서 가져오기)
		Member m = (Member) session.getAttribute("member");

		String uploadPath = "/resources/file/resourceCenter/";
		ServletContext context = request.getServletContext();
		String realUploadPath = context.getRealPath(uploadPath);
		int uploadFileSizeLimit = 1000 * 1024 * 1024; // 1000MB

		String encType = "UTF-8";
		MultipartRequest multi = new MultipartRequest(request, realUploadPath, uploadFileSizeLimit, encType,
				new DefaultFileRenamePolicy());
		
		// 파일 이름 가져오기
		request.setCharacterEncoding("UTF-8");

		String originalFileName = multi.getFilesystemName("file");
		int fileUser = m.getMemNo();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); // 포멧만들기
		long currentTime = Calendar.getInstance().getTimeInMillis(); // 시간값 가져오기
		Timestamp uploadTime = Timestamp.valueOf(formatter.format(currentTime));

		File file = new File(realUploadPath + "\\" + originalFileName);

		file.renameTo(new File(realUploadPath + "\\" + currentTime + "_ho"));
		String changedFileName = currentTime + "_ho";

		File reNameFile = new File(realUploadPath + "\\" + changedFileName);
		String filePath = reNameFile.getPath();
		
		String category = multi.getParameter("category");
		int result = 0;
		long fileSize = reNameFile.length();
		
		if (originalFileName != null) {
			ResourceData rd = new ResourceData();
			rd.setMemNo(fileUser);
			rd.setCategory(category);
			rd.setOriginalFileName(originalFileName);
			rd.setChangedFileName(changedFileName);
			rd.setFilePath(filePath);
			rd.setFileSize(fileSize);
			rd.setUploadTime(uploadTime);
			System.out.println(rd);
			result = rService.insertProjectBoardFile(rd);
		} else {
			changedFileName = "";
		}
		ModelAndView mav = new ModelAndView();

		if (result > 0) {
			mav.addObject("msg", "자료실 업로드 완료");
		} else {
			mav.addObject("msg", "파일을 선택하지 않았습니다");
		}
		mav.addObject("location","/resourceCenter.ho?resourceType=all");
		mav.setViewName("result");
		return mav;

	}
	
	// 프로젝트 파일 다운로드
	@RequestMapping(value = "/resourceFileDownload.ho")
	public void resourceFileDownload(@RequestParam int fileNo, HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		ResourceData rd = rService.selectOneResouceFile(fileNo);
		if (rd != null) {

			File file = new File(rd.getFilePath());

			// 웹 브라우저를 통해 문자열(String)이 아닌 데이터가 전송되려면 Binary 타입으로 처리해야 함
			response.setContentType("application/octet-stream");

			// 파일의 사이즈를 전달해주어야 함
			response.setContentLength((int) rd.getFileSize());

			// 사용자에게 전달할 파일의 이름을 인코딩 해주어야함
			// 이때,. 파일은 해당 컴퓨터의 OS 포맷에 맞게 인코딩해주어야함
			// windows는 기본적으로 ISO-8859-1
			String fileName = new String(rd.getOriginalFileName().getBytes(), "ISO-8859-1");

			// 파일 이름을 http header를 통해서 전달
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

			// 위 코드까지는 파일이름 + 파일을 전송할 수 있는 웹 환경을 셋팅 했다고 보면 됨
			// 아래 코드부터는 실제 파일이 가지고 있는 데이터들을 보내는 작업

			// 해당되는 파일의 데이터를 가져올 수 있는 통로(InputStream 생성)
			FileInputStream fileIn = new FileInputStream(file);

			// 클라이언트에게 데이터를 전달할 통로 생성 (outputStream 생성)
			ServletOutputStream out = response.getOutputStream();

			// 4KByte 씩 처리
			byte[] outputByte = new byte[4096];

			// InputStream으로 데이터를 읽어다가 output 스트림으로 전송하기
			while (fileIn.read(outputByte, 0, 4096) != -1) {
				out.write(outputByte, 0, 4096);
			}
			fileIn.close();
			out.close();
		} else {

		}
	}
	
	//파일 삭제
	@RequestMapping("/deleteResourceFile.ho")
	public ModelAndView deleteResourceFile(@RequestParam int fileNo){
		
		int result = rService.deleteResource(fileNo);
		ModelAndView mav = new ModelAndView();
		if (result > 0) {
			mav.addObject("msg", "삭제가 완료되었습니다.");
		} else {
			mav.addObject("msg", "삭제에 실패하였습니다\n지속적인 오류시 관리자에게 문의하세요");
		}
		mav.addObject("location","/resourceCenter.ho?resourceType=all");
		mav.setViewName("result");
		return mav;
	}
	

}
