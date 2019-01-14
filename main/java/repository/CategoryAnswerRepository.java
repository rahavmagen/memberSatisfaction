package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hibernateDataFiles.CategoryAnswer;
import hibernateDataFiles.CategoryAnswerId;

public interface CategoryAnswerRepository extends JpaRepository<CategoryAnswer, CategoryAnswerId> {
	@Query("from CategoryAnswer where survey.surveyId = ?1")
	List<CategoryAnswer> findBySurveyId(Long id );
	
	
}
