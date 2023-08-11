package kr.co.coupang.member.service;

import kr.co.coupang.member.domain.Member;

public interface MemberService {
	/**
	 * ¸â¹ö µî·Ï Service
	 * @param member
	 * @return int
	 */
	public int registerMember(Member member);
	/**
	 * È¸¿ø Á¤º¸ ¼öÁ¤ Service
	 * @param member
	 * @return int
	 */
	public int modifyMember(Member member);
	/**
	 * È¸¿ø Å»Åð Service
	 * @param memberId
	 * @return int
	 */
	public int removeMember(String memberId);
	/**
	 * ¸â¹ö ·Î±×ÀÎ Service
	 * @param ID, PW
	 * @return member °´Ã¼
	 */
	public Member memberLoginCheck(Member member);
	/**
	 * È¸¿ø »ó¼¼ Á¶È¸ Service
	 * @param memberId
	 * @return member °´Ã¼
	 */
	public Member showOneById(String memberId);
	
}
