package qq.data;

public class WpkDbContext extends DbContext {


    public WpkDbContext() {
        super(WpkHibernateSessionFactory.getInstance());
    }

    public WpkDbContext(boolean transactional) {
        super(WpkHibernateSessionFactory.getInstance(), transactional);
    }


}
