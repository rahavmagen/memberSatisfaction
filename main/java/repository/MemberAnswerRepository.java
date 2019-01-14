package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hibernateDataFiles.MemberAnswer;
import hibernateDataFiles.MemberAnswerId;

public interface MemberAnswerRepository extends JpaRepository<MemberAnswer, MemberAnswerId> {

}
