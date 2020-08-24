package com.pdproject.iolibrary.config;

import com.pdproject.iolibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import javax.sql.DataSource;

@Configuration
@EnableSocial
// Load to Enviroment
@PropertySource("classpath:social-cfg.properties")
public class SocialConfig implements SocialConfigurer {

    private boolean autoSignUp = false;

    @Autowired
    DataSource dataSource;

    @Autowired
    private UserRepository userRepository;

    // @env: read from social-cfg.properties
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        try {
            this.autoSignUp = Boolean.parseBoolean(environment.getProperty("social.auto-signup"));
        }catch (Exception e){
            this.autoSignUp = false;
        }

        // Facebook
        FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory(environment.getProperty("facebook.app.id"),environment.getProperty("facebook.app.secret"));
        facebookConnectionFactory.setScope(environment.getProperty("facebook.scope"));
        connectionFactoryConfigurer.addConnectionFactory(facebookConnectionFactory);

        // Google
        GoogleConnectionFactory googleConnectionFactory = new GoogleConnectionFactory(environment.getProperty("google.client.id"), environment.getProperty("google.client.secret"));
        googleConnectionFactory.setScope(environment.getProperty("google.scope"));
        connectionFactoryConfigurer.addConnectionFactory(googleConnectionFactory);

    }

    // xác định id của người dùng
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    // userconnection
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {

        JdbcUsersConnectionRepository usersConnectionRepository = new JdbcUsersConnectionRepository(dataSource,connectionFactoryLocator, Encryptors.noOpText());

        if (autoSignUp){
            // sau khi đăng nhập vào mạng xã hội
            // tự động tạo USER_ENTITY nếu nó không tồn tại
            ConnectionSignUp connectionSignUp;
        }

        return null;
    }
}
