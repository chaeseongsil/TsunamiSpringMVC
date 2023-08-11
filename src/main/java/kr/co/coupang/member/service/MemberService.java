package kr.co.coupang.member.service;

import kr.co.coupang.member.domain.Member;

public interface MemberService {
	/**
	 * ��� ��� Service
	 * @param member
	 * @return int
	 */
	public int registerMember(Member member);
	/**
	 * ȸ�� ���� ���� Service
	 * @param member
	 * @return int
	 */
	public int modifyMember(Member member);
	/**
	 * ȸ�� Ż�� Service
	 * @param memberId
	 * @return int
	 */
	public int removeMember(String memberId);
	/**
	 * ��� �α��� Service
	 * @param ID, PW
	 * @return member ��ü
	 */
	public Member memberLoginCheck(Member member);
	/**
	 * ȸ�� �� ��ȸ Service
	 * @param memberId
	 * @return member ��ü
	 */
	public Member showOneById(String memberId);
	
}
