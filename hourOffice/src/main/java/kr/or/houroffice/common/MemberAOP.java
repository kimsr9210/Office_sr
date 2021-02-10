package kr.or.houroffice.common;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.or.houroffice.member.model.vo.Member;

@Service
@Aspect
public class MemberAOP {
	
	@Autowired
	@Qualifier(value="Sha256Util")
	private Sha256Util enc;
	
	@Pointcut("execution(* kr.or.houroffice.member.model.service.MemberService.loginMember*(..))")
	public void loginPoint() {}
	
	@Before("loginPoint()")
	public void loginPasswordEncrytion(JoinPoint jp) throws Exception { //로그인 동작 전 암호화
		this.passwordEncrytion(jp);
	}
	
	
	public void passwordEncrytion(JoinPoint jp) throws Exception { //암호화 호출
		Member m = (Member)jp.getArgs()[0];
		String memPwd = m.getMemPwd();
		String encMemPwd = enc.encryData(memPwd);
		m.setMemPwd(encMemPwd);
	}
	
}
