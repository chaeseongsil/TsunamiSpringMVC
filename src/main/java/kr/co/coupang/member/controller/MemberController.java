package kr.co.coupang.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.coupang.member.domain.Member;
import kr.co.coupang.member.service.MemberService;

@Controller
@SessionAttributes({"memberId", "memberName"})
public class MemberController {
	// service를 전역변수로 두고 @Autowired를 이용해 의존성 주입해줌
	@Autowired
	private MemberService service;
	
	//doGet - 페이지 이동용
	@RequestMapping(value="/member/register.do", method=RequestMethod.GET)
	public String showRegisterForm(Model model) {
		return "member/register";
	}
	
	//doPost - 데이터 저장용
	@RequestMapping(value="/member/register.do", method=RequestMethod.POST)
	public String registerMember(
			HttpServletRequest request
			, HttpServletResponse response
			, @RequestParam("memberId") String memberId
			, @RequestParam("memberPw") String memberPw
			, @RequestParam("memberName") String memberName
			, @RequestParam("memberAge") int memberAge
			, @RequestParam("memberGender") String memberGender
			, @RequestParam("memberEmail") String memberEmail
			, @RequestParam("memberPhone") String memberPhone
			, @RequestParam("memberAddr") String memberAddr
			, @RequestParam("memberHobby") String memberHobby
			, Model model) {
		Member member = new Member(memberId, memberPw, memberName, memberAge, memberGender, memberEmail, memberPhone, memberAddr, memberHobby);
		try {
			int result = service.registerMember(member);
			if(result > 0) {
				// 성공!
				// response.sendRedirect("/index.jsp");
				return "redirect:/index.jsp";
			}else {
				// 실패!!
				model.addAttribute("msg", "가입안됨~~");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace(); // 콘솔창에 빨간색으로 뜨게 함
			model.addAttribute("msg", e.getMessage()); // 에러를 웹페이지로 띄우게 함
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/member/update.do", method=RequestMethod.GET)
	public String modifyViewMember(
			@RequestParam("memberId") String memberId
			,Model model) {
		try {
			Member member = service.showOneById(memberId);
			if(member != null) {
				model.addAttribute("member", member);
				return "member/modify";
			}else {
				model.addAttribute("msg", "데이터 조회에 실패하였습니다.");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "회원 정보를 가져오지 못했습니다.ㅠ");
			return "common/errorPage";
		}
	}

	@RequestMapping(value="/member/update.do", method=RequestMethod.POST)
	public String modifyMember(
			@RequestParam("memberId") String memberId
			, @RequestParam("memberPw") String memberPw
			, @RequestParam("memberEmail") String memberEmail
			, @RequestParam("memberPhone") String memberPhone
			, @RequestParam("memberAddr") String memberAddr
			, @RequestParam("memberHobby") String memberHobby
			, RedirectAttributes redirect
			, Model model) {
		try {
			Member member = new Member(memberId, memberPw, memberEmail, memberPhone, memberAddr, memberHobby);
			int result = service.modifyMember(member);
			if(result > 0) {
				redirect.addAttribute("memberId", memberId);
				// redirect시 쿼리스트링 붙이는법
				return "redirect:/member/mypage.do";
			}else {
				model.addAttribute("msg", "회원 정보 수정 실패~ㅠ");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common.errorPage";
		}
	}

	@RequestMapping(value="/member/delete.do", method=RequestMethod.GET)
	public String removeMember(
			@RequestParam("memberId") String memberId
			, Model model
			) {
		try {
			int result = service.removeMember(memberId);
			if(result > 0) {
				return "redirect:/member/logout.do";
			}else {
				model.addAttribute("msg", "탈퇴를 완료하지 못함~");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}

	@RequestMapping(value="/member/login.do", method=RequestMethod.POST)
	public String memberLogin(
			HttpServletRequest request
			, @RequestParam("memberId") String memberId
			, @RequestParam("memberPw") String memberPw
			, Model model) {
		try {
			Member member = new Member();
			member.setMemberId(memberId);
			member.setMemberPw(memberPw);
			Member mOne = service.memberLoginCheck(member);
			if(mOne != null) {
				// 성공하면 메인페이지로 이동
//				HttpSession session = request.getSession();
//				session.setAttribute("memberId", mOne.getMemberId());
//				session.setAttribute("memberName", mOne.getMemberName());
				model.addAttribute("memberId", mOne.getMemberId());
				model.addAttribute("memberName", mOne.getMemberName());
//				model.addAttribute("member", mOne);
				// model에 저장한 값을 세션에 저장하도록 어노테이션 사용하는 방법도 있음
				return "redirect:/index.jsp";
				// redirect는 model을 쓸 수 없음 - without data이기 때문에~
			}else {
				// 실패메시지 출력
				model.addAttribute("msg", "로그인 실패ㅠ");
				return "common/errorPage";
			}
			
		} catch (Exception e) {
			// 예외 발생시 예외 메시지 출력
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/member/logout.do", method=RequestMethod.GET)
	public String memberLogout(
			HttpSession sessionPrev
			// sessionStatus는 스프링의 어노테이션(SessionAttributes)으로 지원되는 세션을 만료시킨다.
			// 사용된 메소드는 setComplete();
			, SessionStatus session
			, Model model) {
		if(session != null) {
//			session.invalidate(); 
			session.setComplete();
			if(session.isComplete()) {
				// 세션 만료 유효성 체크
			}
			return "redirect:/index.jsp";
		}else {
			model.addAttribute("msg", "로그아웃 실패~");
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/member/mypage.do", method=RequestMethod.GET)
	public String showDetailMember(
			@RequestParam("memberId") String memberId
			, Model model) {
		try {
			Member member = service.showOneById(memberId);
			if(member != null) {
				model.addAttribute("member", member);
				return "member/mypage";
			}else {
				model.addAttribute("msg", "회원 상세정보 조회 실패");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}
}
