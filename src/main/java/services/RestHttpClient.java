package services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.dto.FirebaseDto;
import com.firebase.dto.FirebaseNotificationResponseDto;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

public class RestHttpClient {
	private static final String SCOPES = "https://www.googleapis.com/auth/firebase.messaging";
	private String baseUrl = "https://fcm.googleapis.com/fcm/send";
	private RestTemplate restTemplate;
	private HttpHeaders headers;
	private HttpStatus status;
	private static RestHttpClient httpClientInstance = null;

	String AUTH_KEY_FCM = "key="
			+ "AAAAaW0HcTM:APA91bEZicZnx5JW-7_qdQRlrUyyhjY7NITdMraAa3gCSEpn9e_L6-Z0-YwJWrOSItQbWp3TwBTohfc08WS91r71BN1kpifD_uYkLQZIPt3xUd_s8nV0QP6yX_7g1HyGYV-CEwpd_KDr";

	private static final String requestUrl = "https://fcm.googleapis.com/v1/projects/fir-pushnotification-fc0d7/messages:send";

	private RestHttpClient() {

		this.restTemplate = new RestTemplate();
		this.headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		//headers.add("Accept", "*/*");

	}

	public static RestHttpClient getInstance() {
		if (httpClientInstance == null) {
			httpClientInstance = new RestHttpClient();
		}
		return httpClientInstance;
	}

	public String get(String uri) {
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + uri, HttpMethod.GET, requestEntity,
				String.class);
		this.setStatus(responseEntity.getStatusCode());
		return responseEntity.getBody();
	}

	public String post(String uri, String json) {
		String resp = "";
		try {
			HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
			ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity,
					String.class);
			this.setStatus(responseEntity.getStatusCode());
			resp = responseEntity.getBody();
                                                                                                            
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return resp;
	}

	public FirebaseNotificationResponseDto firebasePost(String json)
			throws JsonParseException, JsonMappingException, IOException {
		// String response = "";
		FirebaseNotificationResponseDto resp = new FirebaseNotificationResponseDto();
		try {
			ObjectMapper mapper = new ObjectMapper();

			System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
			headers.add("Authorization", AUTH_KEY_FCM);
			HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
			ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity,
					String.class);
			this.setStatus(responseEntity.getStatusCode());
			resp = mapper.readValue(responseEntity.getBody(), FirebaseNotificationResponseDto.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resp;
	}

	public void put(String uri, String json) {
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + uri, HttpMethod.PUT, requestEntity,
				String.class);
		this.setStatus(responseEntity.getStatusCode());
	}

	private void setStatus(HttpStatus statusCode) {
		// TODO Auto-generated method stub
		this.status = statusCode;

	}

	public String sendNotifications(String json) {
		String resp = "";
		try {
			String token = "Bearer " + getAccessToken();
			headers.add("Authorization", token);
			resp = post(requestUrl, json);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resp;

	}

	private String getAccessToken() throws IOException {

		InputStream isyyy = getClass().getResourceAsStream("/service-account.json");

		GoogleCredential googleCredential = GoogleCredential.fromStream(isyyy).createScoped(Arrays.asList(SCOPES));
		googleCredential.refreshToken();
		return googleCredential.getAccessToken();

	}

}
