package es.redmic.socket.common.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import es.redmic.exception.common.ExceptionType;
import es.redmic.exception.common.InternalException;
import es.redmic.utils.httpclient.HttpClient;

@Service
public class UserUtilsService {

	protected static Logger logger = LogManager.getLogger();

	HttpClient client = new HttpClient();

	@Value("${oauth.userid.endpoint}")
	String GET_USERID_URL;

	private OAuth2Authentication authentication;

	public String getUserId() {

		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
		String token = details.getTokenValue();

		if (token == null) {
			logger.error("Imposible recuperar el token en UserBaseService. Usuario no registrado correctamente.");
			throw new InternalException(ExceptionType.INTERNAL_EXCEPTION);
		}

		return (String) client.get(GET_USERID_URL + "?access_token=" + token, String.class);
	}

	public OAuth2Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(OAuth2Authentication authentication) {
		this.authentication = authentication;
	}
}