package com.tcc.servidor_tcc.entidades;

import com.tcc.servidor_tcc.type.RoleType;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "ReviewerRole.getAllByEmail",
                query ="SELECT RR FROM ReviewerRole RR where RR.reviewer.email = :email")
}
)
@Entity
public class ReviewerRole {
    
    @Id
    @GeneratedValue
    public long id;

    @ManyToOne
    private SystematicReview sysReview;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Reviewer reviewer;

    @Enumerated(EnumType.STRING)
    private List<RoleType> roles;

    public long getId() {
        return id;
    }

    public void setId(long Id) {
        this.id = Id;
    }

    public SystematicReview getSysReview() {
        return sysReview;
    }

    public void setSysReview(SystematicReview sysReview) {
        this.sysReview = sysReview;
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    public List<RoleType> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleType> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReviewerRole other = (ReviewerRole) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.sysReview, other.sysReview)) {
            return false;
        }
        if (!Objects.equals(this.reviewer, other.reviewer)) {
            return false;
        }
        if (!Objects.equals(this.roles, other.roles)) {
            return false;
        }
        return true;
    }
    
    
}
