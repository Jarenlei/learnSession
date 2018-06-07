package com.iflytek.vie.tmc.ruleanalyse.db;


import com.iflytek.vie.common.security.Crypto;
import com.iflytek.vie.common.utils.JarTool;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.File;

/**
 * Created by xiwangma on 15/8/12.
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Crypto crypto = new Crypto();
            String configPath = new File(JarTool.getJarDir()).getParent() + File.separator + "config" + File.separator;
//            Configuration cfg = new Configuration().configure(new File(configPath + "hibernate.cfg.xml"));
             Configuration cfg = new Configuration().configure();
//             cfg.getProperties().put("hibernate.connection.username",
//                     crypto.decrypt(cfg.getProperty("hibernate.connection.username")));
            cfg.getProperties().put("hibernate.connection.password",
                    crypto.decrypt(cfg.getProperty("hibernate.connection.password")));
//            cfg.getProperties().put("connection.username",
//                    crypto.decrypt(cfg.getProperty("connection.username")));
            cfg.getProperties().put("connection.password",
                    crypto.decrypt(cfg.getProperty("connection.password")));
            return cfg.buildSessionFactory(
                    new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build());
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
