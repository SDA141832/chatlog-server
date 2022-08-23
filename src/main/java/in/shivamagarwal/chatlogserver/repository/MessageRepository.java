package in.shivamagarwal.chatlogserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.shivamagarwal.chatlogserver.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	
	List<Message> findByUsername(String username);
	
	List<Message> deleteByUsername(String username);
	
	Message deleteByIdAndUsername(Long id,String username);
	
	Optional<Message> findByIdAndUsername(Long id,String username);

}
