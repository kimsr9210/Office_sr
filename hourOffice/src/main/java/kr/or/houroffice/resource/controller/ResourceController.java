package kr.or.houroffice.resource.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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
	
	@Autowired
	ServletContext context;
	
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
	
	//게시물 파일 올리기
	@RequestMapping(value = "/insertResource.ho")
	public ModelAndView insertResource(HttpServletRequest request, @SessionAttribute("member") Member m) throws Exception {

		int memNo = m.getMemNo();
		int result = 0;
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;
		
		
		String category = multi.getParameter("category");
		MultipartFile file = multi.getFile("file");
		String changedFileName = "";
		String path = "";
		UUID randomeUUID = UUID.randomUUID();
		String organizedfilePath = "";
		if (file != null) {

			System.out.println("파라미터명" + file.getName());
			System.out.println("파일크기" + file.getSize());
			System.out.println("파일 존재" + file.isEmpty());
			System.out.println("오리지날 파일 이름" + file.getOriginalFilename());

			// 파일이 업로드 되는 경로
			path = context.getRealPath("/");
			path = path.replace("\\target\\m2e-wtp\\web-resources", "");
			String uploadPath = "src\\main\\webapp\\resources\\file\\resourceCenter\\";
			path = path + uploadPath;

			System.out.println(path);

			InputStream inputStream = null;
			OutputStream outputStream = null;

			if (file.getSize() > 0) {
				inputStream = file.getInputStream();
				File realUploadDir = new File(path);

				if (!realUploadDir.exists()) {
					realUploadDir.mkdirs();// 폴더생성.
				}

				organizedfilePath = path + randomeUUID + "_" + file.getOriginalFilename();
				System.out.println(organizedfilePath);// 파일이 저장된경로 + 파일 명

				outputStream = new FileOutputStream(organizedfilePath);

				int readByte = 0;
				byte[] buffer = new byte[8192];

				while ((readByte = inputStream.read(buffer, 0, 8120)) != -1) {
					outputStream.write(buffer, 0, readByte); // 파일 생성 !
				}
			}

		}
		// 위의 코드까지 하면 파일 업로드는 완료
		if(file.getSize() > 0){
			
			// 서버에 실제로 업로드 된 파일이름 가져오기
			String originalFileName = file.getOriginalFilename();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); // 포멧만들기
			long currentTime = Calendar.getInstance().getTimeInMillis(); // 현재 시간값 가져오기
			Timestamp uploadTime = Timestamp.valueOf(formatter.format(currentTime));
			
			// 파일 리네임
			File fileSave = new File(organizedfilePath); // 파일 연결
			File copyFile = new File(path+"\\"+currentTime+"_ho");
			changedFileName = currentTime+"_ho"; // DB에 저장할 파일 이름
	
			//아래 로직은 파일을 새로만들어서 데이터를 이동하는 작업
			FileInputStream fis = new FileInputStream(fileSave); //읽을파일
            FileOutputStream fos = new FileOutputStream(copyFile); //복사할파일
            
            int fileByte = 0; 
            // fis.read()가 -1 이면 파일을 다 읽은것
            while((fileByte = fis.read()) != -1) {
                fos.write(fileByte);
            }
            //자원사용종료
            fis.close();
            fos.close();
			
            fileSave.delete(); //기존 원본 파일 삭제
			
			// File 객체를 통해 파일이름이 변경되면 새롭게 연결하는 파일 객체가 필요함
			File reNameFile = new File(path+"\\"+changedFileName); // 이름이 바뀌여 다시 연결해줌
			String filePath = reNameFile.getPath(); // 경로
			// 해당 업로드된 file의 사이즈
			long fileSize = reNameFile.length();
			
			ResourceData rd = new ResourceData();
			rd.setMemNo(memNo);
			rd.setCategory(category);
			rd.setOriginalFileName(originalFileName);
			rd.setChangedFileName(changedFileName);
			rd.setFilePath(filePath);
			rd.setFileSize(fileSize);
			rd.setUploadTime(uploadTime);
			System.out.println(rd);
			result = rService.insertProjectBoardFile(rd);
			
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
