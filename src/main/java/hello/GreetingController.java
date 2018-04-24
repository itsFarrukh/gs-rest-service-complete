package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.firebase.dto.FirebaseDto;
import com.firebase.dto.FirebaseNotification;
import com.firebase.dto.FirebaseNotificationResponseDto;
import com.firebase.dto.FirebaseUserDetail;
import com.firebase.dto.NotificationTemplate;
import com.firebase.dto.NotificationTopicTemplate;

import services.UserService;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	HttpHeaders headers = new HttpHeaders();

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public String sendNotification(@RequestBody String message) {
		String response = "";
		try {
			String token = "f7C4AKXo078:APA91bEi9auODaX_ZMx8mvPYjsnd0nQLZk_2dn8MnF9vDPwH6N77tBZoyFBBchp9PPnpUKqcP3ZAYWOrBFCGwiSrOjsIjE0AJM7amseoL-IhW60vHd39wA-pLNcuPCIcFk63v2LHNacu";
			response = SendNotification.sendNotification(token, message);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return response;

	}

	@RequestMapping(value = "/sendNotification", method = RequestMethod.POST)
	public ResponseEntity<FirebaseNotificationResponseDto> sendMessage(@RequestBody NotificationTemplate notification) {
		FirebaseNotificationResponseDto resp = new FirebaseNotificationResponseDto();

		resp = SendNotification.sendMessage(notification);
		return new ResponseEntity<>(resp, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<FirebaseUserDetail> registerUser(@RequestBody FirebaseUserDetail user) {

		FirebaseUserDetail resp = new FirebaseUserDetail();
		resp = new UserService().registerUser(user);
		return new ResponseEntity<>(resp, headers, HttpStatus.CREATED);

	}
	@RequestMapping(value="/topic",method=RequestMethod.POST)
	public ResponseEntity<FirebaseNotificationResponseDto> sendTopicMessages(@RequestBody NotificationTopicTemplate topicNotification){
		FirebaseNotificationResponseDto resp = new FirebaseNotificationResponseDto();

		resp = SendNotification.sendMessageByTopic(topicNotification);
		return new ResponseEntity<>(resp, headers, HttpStatus.OK);
	}
	@RequestMapping(value="/topicMessage",method=RequestMethod.POST)
	public ResponseEntity<String> sendMessages(@RequestBody FirebaseDto firebaseObj){
		String resp="";
		try {
			resp=SendNotification.sendMessages(firebaseObj);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<>(resp,headers,HttpStatus.OK);
	}

}
