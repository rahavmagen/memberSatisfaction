package ms;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * @author app_sarahy Jוune 2018
 *
 */
public class SessionUtil {

	private static SessionUtil instance = new SessionUtil();
	private SessionFactory sessionFactory;

	public static SessionUtil getInstance() 
	{
		return instance;
	}

	private SessionUtil() 
	{
		super();
		System.out.println("SessionUtil");
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build(); // configures settings from  hibernate.cfg.xml
				
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) 
		{
			// The registry would be destroyed by the SessionFactory, if we had trouble building the SessionFactory so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
		}

	}

	public static Session getSession() 
	{
		Session session = getInstance().sessionFactory.openSession();
		return session;
	}

	public static SessionFactory getSessionFactory() 
	{
		SessionFactory sessionFactory = getInstance().sessionFactory;
		return sessionFactory;
	}

}
