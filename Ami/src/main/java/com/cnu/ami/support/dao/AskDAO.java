package com.cnu.ami.support.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.support.dao.entity.AskEntity;
import com.cnu.ami.support.dao.entity.AskInterfaceVO;

@Repository
public interface AskDAO extends JpaRepository<AskEntity, Long> { // 키 값이 숫자 일경우 Long, 문자열 String

	public List<AskEntity> findById(long id); // 찾고자 할때 find By 컬럼명(첫글자 대문자) 그 다음 컬럼 존재시 _ 로 이어져서 사용 // ** 함수명 중요 !!!

	@Query(value="SELECT A.ASEQ,A.UID,B.GNAME,C.BNAME,D.RNAME,A.ASK_CODE,A.ASK_MESSAGE,A.WDATE\r\n" + 
			"FROM ASK AS A\r\n" + 
			"LEFT JOIN GROUPSET AS B\r\n" + 
			"ON A.GSEQ=B.GSEQ\r\n" + 
			"LEFT JOIN BUILDING AS C\r\n" + 
			"ON A.BSEQ=C.BSEQ\r\n" + 
			"LEFT JOIN REGION AS D\r\n" + 
			"ON B.RSEQ=D.RSEQ\r\n" + 
			"ORDER BY WDATE ASC, ASEQ DESC", nativeQuery = true)
	public List<AskInterfaceVO> getAskList(@Param("gseq") int gseq);
	
	public AskEntity findByaSeq(long aseq);
	
}
