package ztp.edziennik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ztp.edziennik.models.Member;
import ztp.edziennik.models.User;
import ztp.edziennik.models.UserGroup;
import ztp.edziennik.models.UserGroupId;
import ztp.edziennik.repositories.MemberRepository;
import ztp.edziennik.repositories.UserGroupRepository;
import ztp.edziennik.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final UserGroupRepository userGroupRepository;

    @Autowired
    public UserService(UserRepository userRepository, MemberRepository memberRepository, UserGroupRepository userGroupRepository) {
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.userGroupRepository = userGroupRepository;
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

    public Optional<UserGroup> findUserGroupById(UserGroupId userGroupId){
        return userGroupRepository.findById(userGroupId);
    }

}
