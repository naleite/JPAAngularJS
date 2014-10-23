package jpa;

import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


import shared.Evenement;
import shared.Personne;
import shared.Voiture;

public class JpaTest {

	private EntityManager manager;

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	public JpaTest(EntityManager manager) {
		this.setManager(manager);	
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("dev");
		EntityManager manager = factory.createEntityManager();
		JpaTest test = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();
		tx.begin();

		try {

			Voiture v=new Voiture();
			v.setNbPlaceTotal(3);
			v.setSeries("Benz");
			manager.persist(v);
			Personne c=new Personne();
			((Personne) c).setVoiture(v);
			c.setNom("Conducteur");
			c.setDestination("Paris");
			c.setLocalisation("Rennes");
			Date date=new Date();
			Evenement trajet=((Personne) c).proposeTrajet(c.getLocalisation(),c.getDestination(),date);
			
			Personne p1=new Personne();
			Personne p2=new Personne();
			
			p1.setNom("Paula");
			p2.setNom("Aday");
			
			p1.setDestination("Paris");
			p1.setLocalisation("Rennes");
			
			p2.setDestination("Paris");
			p2.setLocalisation("Rennes");
			
			manager.persist(c);
			manager.persist(p1);
			manager.persist(p2);
			
			trajet.addParticipant(p1);
			trajet.addParticipant(p2);
			manager.persist(p1.redigeCom(trajet, "hello world des commentaires"));
			manager.persist(trajet);
			Logger.getGlobal().info(trajet.toString());
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();
		
		/*
		Enseignant es = (Enseignant) manager.createQuery(
				"select e1 from Enseignant as e1 where e1.nom='barais'")
				.getSingleResult();*/
		
	}


}
