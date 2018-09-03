package org.springnext.manager.base.security;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component("messageSource")
public class SecurityMessageSource extends ReloadableResourceBundleMessageSource {

	public SecurityMessageSource() {
		setBasename("classpath:message/messages");
	}

}
