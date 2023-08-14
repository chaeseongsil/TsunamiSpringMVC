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
	// service�� ���������� �ΰ� @Autowired�� �̿��� ������ ��������
	@Autowired
	private MemberService service;
	
	//doGet - ������ �̵���
	@RequestMapping(value="/member/register.do", method=RequestMethod.GET)
	public String showRegisterForm(Model model) {
		return "member/register";
	}
	
	//doPost - ������ �����
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
				// ����!
				// response.sendRedirect("/index.jsp");
				return "redirect:/index.jsp";
			}else {
				// ����!!
				model.addAttribute("msg", "���Ծȵ�~~");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace(); // �ܼ�â�� ���������� �߰� ��
			model.addAttribute("msg", e.getMessage()); // ������ ���������� ���� ��
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
				model.addAttribute("msg", "������ ��ȸ�� �����Ͽ����ϴ�.");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "ȸ�� ������ �������� ���߽��ϴ�.��");
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
				// redirect�� ������Ʈ�� ���̴¹�
				return "redirect:/member/mypage.do";
			}else {
				model.addAttribute("msg", "ȸ�� ���� ���� ����~��");
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
				model.addAttribute("msg", "Ż�� �Ϸ����� ����~");
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
				// �����ϸ� ������������ �̵�
//				HttpSession session = request.getSession();
//				session.setAttribute("memberId", mOne.getMemberId());
//				session.setAttribute("memberName", mOne.getMemberName());
				model.addAttribute("memberId", mOne.getMemberId());
				model.addAttribute("memberName", mOne.getMemberName());
//				model.addAttribute("member", mOne);
				// model�� ������ ���� ���ǿ� �����ϵ��� ������̼� ����ϴ� ����� ����
				return "redirect:/index.jsp";
				// redirect�� model�� �� �� ���� - without data�̱� ������~
			}else {
				// ���и޽��� ���
				model.addAttribute("msg", "�α��� ���Ф�");
				return "common/errorPage";
			}
			
		} catch (Exception e) {
			// ���� �߻��� ���� �޽��� ���
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/member/logout.do", method=RequestMethod.GET)
	public String memberLogout(
			HttpSession sessionPrev
			// sessionStatus�� �������� ������̼�(SessionAttributes)���� �����Ǵ� ������ �����Ų��.
			// ���� �޼ҵ�� setComplete();
			, SessionStatus session
			, Model model) {
		if(session != null) {
//			session.invalidate(); 
			session.setComplete();
			if(session.isComplete()) {
				// ���� ���� ��ȿ�� üũ
			}
			return "redirect:/index.jsp";
		}else {
			model.addAttribute("msg", "�α׾ƿ� ����~");
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
				model.addAttribute("msg", "ȸ�� ������ ��ȸ ����");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}
}
