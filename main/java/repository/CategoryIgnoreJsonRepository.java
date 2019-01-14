package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import hibernateDataFiles.CategoryIgnoreJson;

public interface CategoryIgnoreJsonRepository extends JpaRepository<CategoryIgnoreJson, Long>{
	@Transactional 
	@Modifying
	@Query("update  Category set expiration_date = current_date() where category_id = ?1 ")
	void expireCategory(Long id );	
	

	@Query("from CategoryIgnoreJson where function ('coalesce' ,effectiveDate ,current_date() ) <= current_date() "
			+ "and function('coalesce' ,expirationDate , to_date('50001231','yyyymmdd')) > current_date() ")
	List<CategoryIgnoreJson> getEffective( );
	
	

}
