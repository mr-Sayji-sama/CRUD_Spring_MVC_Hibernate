package web.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.User;

import java.util.List;

@Repository
public interface UserHb extends JpaRepository<User, Long> {

}