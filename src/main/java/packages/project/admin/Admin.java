package packages.project.admin;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
public class Admin {

    @Id
    private int adminId;

    private int adminName;

}
