package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import hibernateDataFiles.PossibleAnswerIgnoreJson;

public interface PossibleAnswerIgnoreJsonRepository extends JpaRepository<PossibleAnswerIgnoreJson , Long>{
	
	@Transactional 
	@Modifying
	@Query("update PossibleAnswer set expiration_date = current_date() where possible_answer_id  = ?1 ")
	void expireAnswer(Long id );
	
	@Query("from PossibleAnswer where function ('coalesce' ,effectiveDate ,current_date() ) <= current_date() "
			+ "and function('coalesce' ,expirationDate , to_date('50001231','yyyymmdd')) > current_date() and question.questionId = ?1")

	List<PossibleAnswerIgnoreJson> getAllEffective(Long id);
}


