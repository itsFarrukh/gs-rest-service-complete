package services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.firebase.dto.FirebaseUserDetail;

public class UserService {

	public FirebaseUserDetail registerUser(FirebaseUserDetail user) {

		FirebaseUserDetail fetchedUser=new FirebaseUserDetail();
		try {
			if(user!=null) {
				SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		        Session session=sessionFactory.openSession();
				session.beginTransaction();
				int id= (int) session.save(user);
				fetchedUser=session.get(FirebaseUserDetail.class, id);
				session.getTransaction().commit();
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return fetchedUser;
	}
}
