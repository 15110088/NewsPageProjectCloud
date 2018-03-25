package service;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;

public class upload_service {
	Path location = Paths.get("upload");
	/** Application name. */
    private static final String APPLICATION_NAME =
        "Drive API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/project1");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(DriveScopes.DRIVE);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }
    public static Credential authorize() throws Exception {
        // Load client secrets.
        InputStream in =
            upload_service.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws Exception 
     */
    public static Drive getDriveService() throws Exception {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
	    public HashMap handleUpload(MultipartFile file) throws Exception {

	    	copyToUpload(file);

			Drive service = getDriveService();
			
			File fileMetadata = new File(); 
			fileMetadata.setTitle(file.getOriginalFilename());
			java.io.File convFile = new java.io.File("upload/"+file.getOriginalFilename());
//			java.io.File convFile = new java.io.File( file.getOriginalFilename());
//			file.transferTo(convFile);
			FileContent mediaContent = new FileContent(file.getContentType(),convFile);
			
			Permission newPermission = new Permission();//permission 
		    newPermission.setType("anyone");
		    newPermission.setRole("reader");
		    
			File f = service.files().insert(fileMetadata, mediaContent)
			.setFields("id")
			.execute();
			
			service.permissions().insert(f.getId(), newPermission).execute();
			
			HashMap<String, String> myMap = new HashMap<String, String>();
			myMap.put("name",file.getOriginalFilename() );
			myMap.put("link", "https://drive.google.com/open?id="+f.getId());
			System.out.println("https://drive.google.com/open?id="+f.getId());
			return myMap;
		}
	    
	    public void copyToUpload(MultipartFile file) {
	    	try {
	            if (file.isEmpty()) {
	                System.out.println("File is empty");
	            }
	            Files.copy(file.getInputStream(), this.location.resolve(file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
	        } catch (IOException e) {
	            System.out.println(this.location.resolve(file.getOriginalFilename()).toAbsolutePath().toString());
	            System.out.println(this.location.resolve(file.getOriginalFilename()).toString());
	        }
	    }
	    
}
