package ztp.edziennik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ztp.edziennik.models.Member;
import ztp.edziennik.models.User;
import ztp.edziennik.repositories.MemberRepository;
import ztp.edziennik.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final MemberRepository memberRepository;

    @Autowired
    public UserService(UserRepository userRepository, MemberRepository memberRepository) {
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
    }

    public void saveUser(User model) {
        String salt = BCrypt.gensalt(4);
        model.setPassword(BCrypt.hashpw(model.getPassword(), salt));
        userRepository.save(model);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<Member> findStudents(String search){
        return memberRepository.findStudents(search);
    }

    public List<Member> findGroupMembers(Long groupId) {
        return memberRepository.findGroupMembers(groupId);
    }

}
