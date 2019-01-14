package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hibernateDataFiles.EdiParameters;


public interface EdiParametersRepository extends JpaRepository<EdiParameters, Long> {

	@Query("from EdiParameters where parameterSystem = ?1 and parameterName =?2 ")
	EdiParameters getEdiParametersByName(String system , String Name);
	
}
