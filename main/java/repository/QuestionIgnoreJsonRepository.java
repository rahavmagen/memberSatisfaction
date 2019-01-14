package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import hibernateDataFiles.QuestionIgnoreJson;

public interface QuestionIgnoreJsonRepository extends JpaRepository<QuestionIgnoreJson , Long>{
	@Transactional 
	@Modifying
	@Query("update Question set expiration_date = current_date() where question_id = ?1 ")
	void expireQuestion(Long id );	
	
	
	@Query("from Question where function ('coalesce' ,effectiveDate ,current_date() ) <= current_date() "
			+ "and function('coalesce' ,expirationDate , to_date('50001231','yyyymmdd')) > current_date() and category.categoryId=?1")
	List<QuestionIgnoreJson> findByCategoryId(Long id );
	

	
}



