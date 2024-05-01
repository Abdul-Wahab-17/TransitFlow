package packages.project.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository < Admin , Integer > {

    @Query("select a.adminId from Admin a where a.login.loginId = :loginId")
    Integer getAdminIdByLoginId(Integer loginId);

    Admin getAdminByAdminId(Integer adminId);
}
