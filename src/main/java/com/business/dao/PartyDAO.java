package com.business.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.business.beans.HibernateSessionFactory;
import com.business.models.Party;

@Component
public class PartyDAO {

	@Autowired
	private HibernateSessionFactory hibernateSessionFactory;

	public void saveParty(Party party) throws Exception {
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		session.save(party);
		session.getTransaction().commit();
	}

	public Party getPartyById(Integer id) throws Exception {
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		Party party = session.get(Party.class, id);
		session.getTransaction().commit();
		return party;
	}

	public List<Party> getParties() throws Exception {
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		List<Party> parties = session.createQuery("from Party").getResultList();
		session.getTransaction().commit();
		return parties;
	}

	public Boolean deletePartyById(Integer partyId) throws Exception {
		Boolean flag = false;
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		Party party = session.get(Party.class, partyId);
		if (party != null) {
			flag = true;
			session.delete(party);
		}
		session.getTransaction().commit();
		return flag;
	}

	public void updateParty(Party party) throws Exception {
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		session.update(party);
		session.getTransaction().commit();
	}

	private Session getSessionFromSessionFactory() throws Exception {
		return hibernateSessionFactory.getSessionFactory().getCurrentSession();
	}

}
