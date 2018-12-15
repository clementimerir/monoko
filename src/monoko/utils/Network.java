package monoko.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.ArrayList;
import java.util.List;

import monoko.objects.Character;
import monoko.objects.Soul;
import monoko.objects.Team;
import monoko.objects.User;

public class Network {
	//random commentary
	User user;
	StringBuffer response;

	public static void main(String[] args) throws Exception {
		Network http = new Network();
		List<Character> l = new ArrayList<Character>();
		Character c1 = new Character(0, "Escanor", null, null, "unSprite", "unAutreSprite");
		Character c2 = new Character(1, "Phillipe", null, null, "unSprite", "unAutreSprite");
		Character c3 = new Character(2, "Jean", null, null, "unSprite", "unAutreSprite");
		l.add(c1);
		l.add(c2);
		l.add(c3);
		Team t = new Team(0, "Team O", l);
		http.login("Mambab", "azerty");
	}
	
	private int sendPost(String url, String urlParameters) throws Exception {
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		/*
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
		*/
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return responseCode;
	}

	private void saveCharacter(Character c) throws Exception {
		String urlParameters = "{\"username\":\"" +user.getUsername()+ "\",\"password\":\"" +user.getPassword()+ "\",\"character\":" + c.toJson()+ "}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/saveCharacter", urlParameters);
	}
	
	private void deleteCharacter(Character c) throws Exception {
		String urlParameters = "{\"username\":\"" +user.getUsername()+ "\",\"password\":\"" +user.getPassword()+ "\",\"ref\":" +c.getId()+ "}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/deleteCharacter", urlParameters);
	}

	private void saveTeam(Team t) throws Exception {
		String urlParameters = "{\"username\":\"" +user.getUsername()+ "\",\"password\":\"" +user.getPassword()+ "\",\"team\":" + t.toJson()+ "}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/saveTeam", urlParameters);
	}
	
	private void deleteTeam(Team t) throws Exception {
		String urlParameters = "{\"username\":\"" +user.getUsername()+ "\",\"password\":\"" +user.getPassword()+ "\",\"ref\":" +t.getId()+ "}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/deleteTeam", urlParameters);
	}

	public User login(String username, String password) throws Exception {
		String urlParameters = "{\"username\":\"" +username+ "\",\"password\":\"" +password+ "\"}";
		if(sendPost("https://multiplayer-mambab.c9users.io/login", urlParameters) == 200) {
			user = new User();
			user.setUsername(username);
			user.setPassword(password);
			JSONParser parser = new JSONParser();
			JSONObject userJSON = (JSONObject) parser.parse(response.toString());.
			JSONArray characters = (JSONArray)userJSON.get("characters");
			for(int i=0; i<characters.size(); i++) {
				JSONObject c = (JSONObject)characters.get(i);
				System.out.println(c);
				Character character = new Character(c.get("ref"),(String)c.get("name"),null,null,"","");
				if(c.get("job")!= "none")
					character.setJob(new Soul((String)c.get("job")));
				if(c.get("god")!= "none")
					character.setJob(new Soul((String)c.get("god")));
				System.out.println(character.toJson());
			}
			return user;
		}
		else return user;
	}

	public void register(String _username, String _password) throws Exception {
		String urlParameters = "{\"username\":\"" +_username+ "\",\"password\":\"" +_password+ "\"}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/register", urlParameters);
	}

}