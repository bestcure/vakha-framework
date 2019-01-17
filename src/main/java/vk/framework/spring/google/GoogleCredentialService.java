package vk.framework.spring.google;

import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleCredentialService {
	@Autowired
	ServletContext servletContext;
}