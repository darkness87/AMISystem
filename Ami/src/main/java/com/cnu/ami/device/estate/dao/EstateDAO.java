package com.cnu.ami.device.estate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.device.estate.dao.entity.EstateReadingFirstInterfaceVO;

@Repository
public interface EstateDAO extends JpaRepository<EstateEntity, String> { // 키 값이 숫자 일경우 Long, 문자열 String

	public EstateEntity findBygId(String gId); // 찾고자 할때 find By 컬럼명(첫글자 대문자) 그 다음 컬럼 존재시 _ 로 이어져서 사용 // ** 함수명 중요 !!!

	public EstateEntity findBygSeq(int gSeq);
	
	public void deleteBygSeq(int gSeq);
	
	@Query(value = "SELECT DAY_POWER AS READINGDAY, HOUSECOUNT\r\n" + 
			"FROM (SELECT DAY_POWER, SUM(CNT_HOUSE) AS HOUSECOUNT FROM GROUPSET WHERE CHK_POWER = 'Y' GROUP BY DAY_POWER) AS A\r\n" + 
			"WHERE A.DAY_POWER <= :day ORDER BY DAY_POWER DESC LIMIT 1", nativeQuery = true)
	public EstateReadingFirstInterfaceVO getEstateReadingFirst(@Param("day") int day);
	
	@Query(value = "SELECT COUNT(*) FROM (SELECT GSEQ FROM GROUPSET WHERE DAY_POWER = :day) A\r\n" + 
			"JOIN BUILDING B\r\n" + 
			"ON A.GSEQ=B.GSEQ\r\n" + 
			"JOIN BUILDING_DCU_MAP C\r\n" + 
			"ON B.BSEQ=C.BSEQ\r\n" + 
			"JOIN (SELECT DID FROM DCU_INFO WHERE IS_DELETE = 'N') D\r\n" + 
			"ON C.DID=D.DID\r\n" + 
			"JOIN METER_INFO E\r\n" + 
			"ON D.DID=E.DID\r\n" + 
			"JOIN (SELECT METER_ID FROM GAUGE_PERIOD WHERE FROM_UNIXTIME(MTIME) = :toDate) F\r\n" + 
			"ON E.METER_ID=F.METER_ID", nativeQuery = true)
	public int getEstateReadingSucess(@Param("day") int day, @Param("toDate") String toDate);
	
}
