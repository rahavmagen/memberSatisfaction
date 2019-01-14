package repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hibernateDataFiles.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

//	@Query("from Survey s join fetch CategoryAnswer ca where  ?1 <= s.creationDate  and  s.creationDate  <  ?2 ")
	@Query("from Survey where  ?1 <= creation_date  and  creation_date  <  ?2 order by survey_id desc " )	
	List<Survey> getSurveysByDates(Date fromDate , Date toDate );
	
}
