package hello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.dto.FirebaseDto;
import com.firebase.dto.FirebaseNotification;
import com.firebase.dto.FirebaseNotificationResponseDto;
import com.firebase.dto.NotificationTemplate;
import com.firebase.dto.NotificationTopicTemplate;

import services.RestHttpClient;

public class SendNotification {

	public static String sendNotification(String token, String message) throws IOException {

		String response = "";
		try {

			String AUTH_KEY_FCM = "AAAAaW0HcTM:APA91bEZicZnx5JW-7_qdQRlrUyyhjY7NITdMraAa3gCSEpn9e_L6-Z0-YwJWrOSItQbWp3TwBTohfc08WS91r71BN1kpifD_uYkLQZIPt3xUd_s8nV0QP6yX_7g1HyGYV-CEwpd_KDr";
			URL baseUrl = new URL("https://fcm.googleapis.com/fcm/send");
			System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
			HttpURLConnection conn = (HttpURLConnection) baseUrl.openConnection();

			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);

			JSONObject json = new JSONObject();
			JSONObject info = new JSONObject();

			json.put("to", token.trim());

			info.put("title", "notification title");
			info.put("body", message);
			json.put("notification", info);

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(json.toString());
			wr.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			response = "200";

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return response;

	}

	public static FirebaseNotificationResponseDto sendMessage(NotificationTemplate notification) {
		// String response = "";
		FirebaseNotificationResponseDto resp = new FirebaseNotificationResponseDto();
		try {
			RestHttpClient httpClient = RestHttpClient.getInstance();
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(notification);
			resp = httpClient.firebasePost(jsonString);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resp;
	}

	public static FirebaseNotificationResponseDto sendMessageByTopic(NotificationTopicTemplate notification) {
		FirebaseNotificationResponseDto resp = new FirebaseNotificationResponseDto();
		try {
			RestHttpClient httpClient = RestHttpClient.getInstance();
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(notification);
			resp = httpClient.firebasePost(jsonString);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resp;
	}

	public static String sendMessages(FirebaseDto message) {
		String resp = "";
		try {
			RestHttpClient httpClient = RestHttpClient.getInstance();
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(message);
			resp = httpClient.sendNotifications(jsonString);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resp;
	}

}
