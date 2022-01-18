/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.scenarios.authorization;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.eclipse.basyx.examples.scenarios.authorization.exception.AddClientException;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmCreationException;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmDeletionException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import javassist.NotFoundException;

/**
 * This class provides KeyCloak rest services such as creating Realm, adding Clients to realm,
 * getting client secrets.
 * 
 * @author danish
 *
 */
public class KeyCloakRestService {
	private final Logger logger = LoggerFactory.getLogger(KeyCloakRestService.class);
	
	protected static final String REALM_NAME = "basyx-demo";
	private static final String CLIENT_NAME = "basyx-demo";
	private static final String CLIENT_SECRET = "client-secret";
	private static final String CLIENT_SECRET_FIELD_NAME = "value";
	private static final String GRANT_TYPE_FIELD = "grant_type";
	private static final String GRANT_TYPE_VALUE = "password";
	private static final String CLIENT_ID_FIELD = "client_id";
	private static final String CLIENT_ID_VALUE = "admin-cli";
	private static final String USERNAME_FIELD = "username";
	private static final String USERNAME_VALUE = "admin";
	private static final String PASSWORD_FIELD = "password";
	private static final String PASSWORD_VALUE = "admin";
	private static final String CHARSET = "UTF-8";
	private static final String ACCESS_TOKEN = "access_token";
	private static final String BEARER = "bearer ";
	private static final String FILE_BASE_PATH = "src/main/resources";
	private static final String REALM_FILE_NAME = "Test_realm.json";
	private static final String CLIENT_FILE_NAME = "Test_client.json";
	private static final String CLIENT_FIELD_NAME = "name";
	private static final String ID = "id";
	private static final String SUCCESS_MESSAGE = "Success";
	protected static final String SERVER_ADDRESS = "http://127.0.0.1:9006";
	private static final String TOKEN_ENDPOINT_URL = SERVER_ADDRESS + "/auth/realms/master/protocol/openid-connect/token";
	private static final String REST_BASE_URL = SERVER_ADDRESS + "/auth/admin/realms";
	private static final String CLIENT_URL = SERVER_ADDRESS + "/auth/admin/realms/" + REALM_NAME + "/clients";
	
	public KeyCloakRestService() throws RealmCreationException, IOException, NotFoundException, AddClientException {
		createRealm();
		addClientToRealm();
	}

	@SuppressWarnings("unchecked")
	private void createRealm() throws RealmCreationException, IOException, NotFoundException {        
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.ACCEPT_CHARSET, CHARSET);
        headers.add(HttpHeaders.AUTHORIZATION, BEARER + getAccessToken());
        
        String realmJsonDataAsString = readFileAsString(FILE_BASE_PATH + "/" + REALM_FILE_NAME);
        
        ResponseEntity<Object> response = (ResponseEntity<Object>) restProvider(REST_BASE_URL , headers, realmJsonDataAsString, HttpMethod.POST);
        
        if(response.getStatusCodeValue() == HttpURLConnection.HTTP_CREATED) {
        	logger.info(SUCCESS_MESSAGE + " : " + response.getStatusCodeValue() + " : Realm created successfully");
        }
        else {
        	throw new RealmCreationException("Exception in creating realm : " + response.getStatusCode().toString());
        }
	}

	@SuppressWarnings("unchecked")
	private void addClientToRealm() throws AddClientException, NotFoundException, IOException {        
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.ACCEPT_CHARSET, CHARSET);
        headers.add(HttpHeaders.AUTHORIZATION, BEARER + getAccessToken());
        
        String clientJsonDataAsString = readFileAsString(FILE_BASE_PATH + "/" + CLIENT_FILE_NAME);
        
        ResponseEntity<Object> response = (ResponseEntity<Object>) restProvider(CLIENT_URL, headers, clientJsonDataAsString, HttpMethod.POST);
        
        if(response.getStatusCodeValue() == HttpURLConnection.HTTP_CREATED) {
        	logger.info(SUCCESS_MESSAGE + " : " + response.getStatusCodeValue() + " : Client added successfully");
        }
        else {
        	throw new AddClientException("Exception in adding client to the realm : " + response.getStatusCode().toString());
        }
	}
	
	private static String readFileAsString(String file)throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
	
	@SuppressWarnings("unchecked")
	public String getClientSecret() throws NotFoundException, ParseException {
        String url = CLIENT_URL + "/" + retrieveIdOfTheClient() + "/" + CLIENT_SECRET;
        
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.add(HttpHeaders.ACCEPT_CHARSET, CHARSET);
        headers.add(HttpHeaders.AUTHORIZATION, BEARER + getAccessToken());
        
        String body = null;
        
        ResponseEntity<JSONObject> response = (ResponseEntity<JSONObject>) restProvider(url, headers, body, HttpMethod.GET);;
        
        if(response.getStatusCodeValue() == HttpURLConnection.HTTP_OK) {
        	JSONObject responseBody = new JSONObject(response.getBody());
        	
        	return (String) responseBody.get(CLIENT_SECRET_FIELD_NAME);
        }
        
        throw new NotFoundException("Exception message : "  + response.getStatusCode().toString()); 
	}
	
	private String retrieveIdOfTheClient() throws NotFoundException, ParseException {
		ResponseEntity<Object> entity = getAllClients();
		
		return parseIdFromResponse(entity);
	}
	
	@SuppressWarnings("unchecked")
	private ResponseEntity<Object> getAllClients() throws NotFoundException {        
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.add(HttpHeaders.ACCEPT_CHARSET, CHARSET);
        headers.add(HttpHeaders.AUTHORIZATION, BEARER + getAccessToken());
        
        String body = null;
        
        ResponseEntity<Object> response = (ResponseEntity<Object>) restProvider(CLIENT_URL, headers, body, HttpMethod.GET);
        
        if(response.getStatusCodeValue() == HttpURLConnection.HTTP_OK) {
        	return response;
        }
        
        throw new NotFoundException("Exception in retrieving clients : "  + response.getStatusCode().toString()); 
	}
	
	@SuppressWarnings("unchecked")
	private String parseIdFromResponse(ResponseEntity<Object> response) throws NotFoundException {
		ArrayList<LinkedHashMap<String, String>> responseBody = (ArrayList<LinkedHashMap<String, String>>) response.getBody();

		Optional<String> idOfTheClient = responseBody.stream()
				.filter(map -> map.get(CLIENT_FIELD_NAME).equals(CLIENT_NAME)).map(id -> id.get(ID)).findFirst();
		
		if(idOfTheClient != null) {
			return idOfTheClient.get();
		}
		else {
			throw new NotFoundException("Exception in retrieving Client Id from the response : "  + response.getStatusCode().toString()); 
		}
	}
	
	@SuppressWarnings("unchecked")
	private String getAccessToken() throws NotFoundException {        
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.add(HttpHeaders.ACCEPT_CHARSET, CHARSET);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add(GRANT_TYPE_FIELD, GRANT_TYPE_VALUE);
        body.add(CLIENT_ID_FIELD, CLIENT_ID_VALUE);
        body.add(USERNAME_FIELD, USERNAME_VALUE);
        body.add(PASSWORD_FIELD, PASSWORD_VALUE);
        
        ResponseEntity<Object> response = (ResponseEntity<Object>) restProvider(TOKEN_ENDPOINT_URL, headers, body, HttpMethod.POST);
        
        if(response.getStatusCodeValue() == HttpURLConnection.HTTP_OK) {
        	JSONObject responseBody = new JSONObject((Map<String, String>) response.getBody());
        	
        	return (String) responseBody.get(ACCESS_TOKEN);
        }

        throw new NotFoundException("Exception in getting access token : "  + response.getStatusCode().toString()); 
	}
	
	private ResponseEntity<?> restProvider(String url, MultiValueMap<String, String> headers, Object body, HttpMethod httpMethod) {
		RestTemplate restTemplate = new RestTemplate();
		
		HttpEntity<?> entity= new HttpEntity<>(body, headers);
		
		return restTemplate.exchange(url, httpMethod, entity, Object.class);
	}

	@SuppressWarnings("unchecked")
	public void deleteRealm() throws RealmCreationException, IOException, NotFoundException, RealmDeletionException {
		String url = REST_BASE_URL + "/" + REALM_NAME;
		
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.ACCEPT_CHARSET, CHARSET);
        headers.add(HttpHeaders.AUTHORIZATION, BEARER + getAccessToken());
        
        String body = null;
        
        ResponseEntity<Object> response = (ResponseEntity<Object>) restProvider(url , headers, body, HttpMethod.DELETE);
        
        if(response.getStatusCodeValue() == HttpURLConnection.HTTP_NO_CONTENT) {
        	logger.info(SUCCESS_MESSAGE + " : " + response.getStatusCodeValue() + " : Realm deleted successfully");
        }
        else {
        	throw new RealmDeletionException("Exception in deleting the realm : " + response.getStatusCode().toString());
        }
	}
}
