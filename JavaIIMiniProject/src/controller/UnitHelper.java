package controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import model.Unit;

public class UnitHelper {
	static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("RentalLeases");
	
	public void insertUnit(Unit unit) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(unit);
		em.getTransaction().commit();
		em.close();
	}
	
	public void deleteUnit(Unit toDelete) throws RollbackException{
		EntityManager em = emfactory.createEntityManager(); // unit tenant term endDate
		em.getTransaction().begin();
		TypedQuery<Unit> typedQuery = em.createQuery("select li from Unit li where "
				+ "li.id = :selectedUnitId", Unit.class);
		
		//Substitute parameter with actual data from the toDelete item
		typedQuery.setParameter("selectedUnitId", toDelete.getId());
				
		//we only want one result
		typedQuery.setMaxResults(1);
		
		//get the result and save it into a new list item
		Unit result = typedQuery.getSingleResult();
		
		//remove it
		em.remove(result);
		em.getTransaction().commit();
		em.close();
	}

	public Unit searchForUnitById(int unitId) {
		// TODO Auto-generated method stub
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		Unit found = em.find(Unit.class, unitId);
		em.close();
		return found;
	}
	
	public List<Unit> searchForUnitByAddress(String addr) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Unit> typedQuery = em.createQuery("select li from "
				+ "Unit li where li.address = ::selectedAddress", Unit.class);
		typedQuery.setParameter("selectedAddress", addr);
		List<Unit> foundItems = typedQuery.getResultList();
		em.close();
		return foundItems;
	}
	

	
	public void updateUnit(Unit toEdit) {
		// TODO Auto-generated method stub
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.merge(toEdit);
		em.getTransaction().commit();
		em.close();
		
	}
	
	public void cleanUp() {
		// TODO Auto-generated method stub
		emfactory.close();
		
	}
	
	
	public List<Unit> showAllUnits() {
		// TODO Auto-generated method stub
		EntityManager em = emfactory.createEntityManager();
		List<Unit> allUnits = em.createQuery("SELECT i FROM Unit i").getResultList();
		return allUnits;
	}
}
