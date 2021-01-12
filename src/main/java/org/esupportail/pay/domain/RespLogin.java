/**
 * Licensed to ESUP-Portail under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 *
 * ESUP-Portail licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.esupportail.pay.domain;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Configurable
public class RespLogin {

    @Column(unique = true)
    String login;
    
    @Transient
	String displayName;

	public static RespLogin findOrCreateRespLogin(String login2find) {
		RespLogin respLogin = null;
		List<RespLogin> respLogins = findRespLoginsByLoginEquals(login2find).getResultList();
		if(!respLogins.isEmpty()) {
			respLogin = respLogins.get(0);
		} else {
			respLogin = new RespLogin();
			respLogin.setLogin(login2find);
			respLogin.persist();
		}
		return respLogin;
	}

	@Override
	public String toString() {
		return login;
	}

	public String getLogin() {
        return this.login;
    }

	public void setLogin(String login) {
        this.login = login;
    }

	public String getDisplayName() {
        return this.displayName;
    }

	public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }

	public static Long countFindRespLoginsByLoginEquals(String login) {
        if (login == null || login.length() == 0) throw new IllegalArgumentException("The login argument is required");
        EntityManager em = RespLogin.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM RespLogin AS o WHERE o.login = :login", Long.class);
        q.setParameter("login", login);
        return ((Long) q.getSingleResult());
    }

	public static TypedQuery<RespLogin> findRespLoginsByLoginEquals(String login) {
        if (login == null || login.length() == 0) throw new IllegalArgumentException("The login argument is required");
        EntityManager em = RespLogin.entityManager();
        TypedQuery<RespLogin> q = em.createQuery("SELECT o FROM RespLogin AS o WHERE o.login = :login", RespLogin.class);
        q.setParameter("login", login);
        return q;
    }

	public static TypedQuery<RespLogin> findRespLoginsByLoginEquals(String login, String sortFieldName, String sortOrder) {
        if (login == null || login.length() == 0) throw new IllegalArgumentException("The login argument is required");
        EntityManager em = RespLogin.entityManager();
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM RespLogin AS o WHERE o.login = :login");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<RespLogin> q = em.createQuery(queryBuilder.toString(), RespLogin.class);
        q.setParameter("login", login);
        return q;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("login");

	public static final EntityManager entityManager() {
        EntityManager em = new RespLogin().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countRespLogins() {
        return entityManager().createQuery("SELECT COUNT(o) FROM RespLogin o", Long.class).getSingleResult();
    }

	public static List<RespLogin> findAllRespLogins() {
        return entityManager().createQuery("SELECT o FROM RespLogin o", RespLogin.class).getResultList();
    }

	public static List<RespLogin> findAllRespLogins(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM RespLogin o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, RespLogin.class).getResultList();
    }

	public static RespLogin findRespLogin(Long id) {
        if (id == null) return null;
        return entityManager().find(RespLogin.class, id);
    }

	public static List<RespLogin> findRespLoginEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM RespLogin o", RespLogin.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<RespLogin> findRespLoginEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM RespLogin o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, RespLogin.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            RespLogin attached = RespLogin.findRespLogin(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public RespLogin merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        RespLogin merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
