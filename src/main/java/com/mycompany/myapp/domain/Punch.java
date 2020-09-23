package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Punch.
 */
@Entity
@Table(name = "punch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Punch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user")
    private String user;

    @Column(name = "mylatitude", precision = 21, scale = 2)
    private BigDecimal mylatitude;

    @Column(name = "my_longitude", precision = 21, scale = 2)
    private BigDecimal myLongitude;

    @Column(name = "punchtime")
    private LocalDate punchtime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public Punch user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public BigDecimal getMylatitude() {
        return mylatitude;
    }

    public Punch mylatitude(BigDecimal mylatitude) {
        this.mylatitude = mylatitude;
        return this;
    }

    public void setMylatitude(BigDecimal mylatitude) {
        this.mylatitude = mylatitude;
    }

    public BigDecimal getMyLongitude() {
        return myLongitude;
    }

    public Punch myLongitude(BigDecimal myLongitude) {
        this.myLongitude = myLongitude;
        return this;
    }

    public void setMyLongitude(BigDecimal myLongitude) {
        this.myLongitude = myLongitude;
    }

    public LocalDate getPunchtime() {
        return punchtime;
    }

    public Punch punchtime(LocalDate punchtime) {
        this.punchtime = punchtime;
        return this;
    }

    public void setPunchtime(LocalDate punchtime) {
        this.punchtime = punchtime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Punch)) {
            return false;
        }
        return id != null && id.equals(((Punch) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Punch{" +
            "id=" + getId() +
            ", user='" + getUser() + "'" +
            ", mylatitude=" + getMylatitude() +
            ", myLongitude=" + getMyLongitude() +
            ", punchtime='" + getPunchtime() + "'" +
            "}";
    }
}
