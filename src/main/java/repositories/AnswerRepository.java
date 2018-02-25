package repositories;

import domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    @Query("select avg(q.answers.size), stddev(q.answers.size) from Rendezvous r join r.questions q")
    Object[] avgDevAnswersQuestionsPerRendezvous();
}
