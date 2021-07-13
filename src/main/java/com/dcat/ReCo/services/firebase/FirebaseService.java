package com.dcat.ReCo.services.firebase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.Arrays;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

public class FirebaseService {
	
	private FirebaseMessaging fcm;
	
	private static FirebaseService instance;
	
	private FirebaseService() {
		try {
			setup();
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	
	private void setup() throws IOException {
		InputStream credentialStream = this.getClass().getClassLoader().getResourceAsStream("reco-thesis-firebase-key.json");
//		File file = ResourceUtils.getFile("classpath:reco-thesis-firebase-key.json");
//		FileInputStream serviceAccount = new FileInputStream(file);
		GoogleCredentials credential = GoogleCredentials.fromStream(credentialStream);
		
		FirebaseOptions firebaseOptions = FirebaseOptions
	            .builder()
	            .setCredentials(credential)
	            .build();
	    FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions);
	    
	    this.fcm = FirebaseMessaging.getInstance(app);
	}
	
	public static FirebaseService getInstance() {
		if(instance == null)
			instance = new FirebaseService();
		
		return instance;
	}

	String FIREBASE_URL = "https://firebasestorage.googleapis.com/v0/b/reco-thesis.appspot.com/o/%s?alt=media";
	
	private Storage getProvider() throws IOException {
		File file = ResourceUtils.getFile("classpath:reco-thesis-firebase-key.json");
		FileInputStream serviceAccount = new FileInputStream(file);

		return StorageOptions.newBuilder().setProjectId("reco-thesis")
				.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build().getService();
	}

	public String uploadImage(MultipartFile uploadFile) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String fileName = timestamp.getTime() + uploadFile.getOriginalFilename();
		try {
			File convertedFile = convertToFile(uploadFile, fileName);

			// TODO call multipart.getBytes
			BlobId blobId = BlobId.of("reco-thesis.appspot.com", fileName);
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

			getProvider().create(blobInfo, Files.readAllBytes(convertedFile.toPath()));
			
			return String.format(FIREBASE_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private File convertToFile(MultipartFile uploadFile, String fileName) throws FileNotFoundException, IOException {
		File temp = new File(fileName);

		try (FileOutputStream fos = new FileOutputStream(temp)) {
			fos.write(uploadFile.getBytes());
			fos.close();
		}

		return temp;
	}
	
	public void subcribe(String token) throws FirebaseMessagingException {
		String[] tokens = new String[] {token};
		
		fcm.subscribeToTopic(Arrays.asList(tokens), "reco");
	}

	public void pushNotiToAll(com.dcat.ReCo.models.Notification payload) throws FirebaseMessagingException {
		Notification notification = Notification
                .builder()
                .setTitle(payload.getTitle())
                .setBody(payload.getContent())
                .setImage(payload.getImage())
                .build();

        Message message = Message
                .builder()
                .setTopic("reco")
                .setNotification(notification)
                .build();
        
        fcm.send(message);
	}
	
	public void pushNotiToOne(com.dcat.ReCo.models.Notification payload, String token) throws FirebaseMessagingException {
		Notification notification = Notification
                .builder()
                .setTitle(payload.getTitle())
                .setBody(payload.getContent())
                .setImage(payload.getImage())
                .build();

        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .build();
        
        fcm.send(message);
	}
}
