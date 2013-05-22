package de.ur.mi.mdc.core.com.server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.SaltedPasswordEncryptor;
import org.apache.ftpserver.usermanager.UserFactory;
import org.apache.ftpserver.usermanager.impl.WritePermission;

public class FTPServer {
	
	public static final int serverport = 2221;
	private FtpServer server;
	
	public FTPServer() {
		initServer();
	}

	private void initServer() {
		FtpServerFactory serverFactory = new FtpServerFactory();
		
		ListenerFactory factory = new ListenerFactory();
		factory.setPort(serverport);
		factory.setServerAddress(MDCCommunicationServer.getIpAdress());
		factory.setImplicitSsl(false);
		
		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setFile(new File("ftp/myusers.properties"));
        userManagerFactory.setPasswordEncryptor(new SaltedPasswordEncryptor());
        UserManager userManager = userManagerFactory.createUserManager();

        UserFactory userFact= new UserFactory();
        userFact.setName("smux");
        userFact.setPassword("smux");
        userFact.setHomeDirectory("ftp/home/smux");
        List<Authority> authorities =  new ArrayList<Authority>();
        authorities.add(new WritePermission());
        userFact.setAuthorities(authorities);
        
        User user = userFact.createUser();
        try {
			userManager.save(user);
		} catch (FtpException e) {
			e.printStackTrace();
		}

        serverFactory.setUserManager(userManager);
		serverFactory.addListener("default", factory.createListener());
		
		server = serverFactory.createServer();
		
	}
	
	public void start() {
		if(server != null) {
			try {
				server.start();
				System.out.println("ftp server running");
			} catch (FtpException e) {
				
			}
		}
	}
	
	public void stop() {
		server.stop();
	}
}
