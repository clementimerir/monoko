package monoko.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.util.ArrayList;
import java.util.List;

import com.sun.media.jfxmedia.events.NewFrameEvent;

import monoko.objects.Character;
import monoko.objects.Player;
import monoko.objects.Soul;
import monoko.objects.Team;

public class Network {
	//random commentary
	private String username;
	private String password;
	StringBuffer response;

	public static void main(String[] args) throws Exception {
		Network http = new Network();
		//System.out.println("\nTesting 2 - Send Http POST request");
		//http.sendPost("{\\\"username\\\":\\\"Mambab\\\",\\\"password\\\":\\\"azerty\\\"}");
		List<Character> l = new ArrayList<Character>();
		Character c1 = new Character(0, "Escanor", null, null, "unSprite", "unAutreSprite");
		Character c2 = new Character(1, "Phillipe", null, null, "unSprite", "unAutreSprite");
		Character c3 = new Character(2, "Jean", null, null, "unSprite", "unAutreSprite");
		l.add(c1);
		l.add(c2);
		l.add(c3);
		Team t = new Team(0, "Team O", l);
		http.login("Mambab", "azerty");
		http.saveTeam(t);
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
		String urlParameters = "{\"username\":\"" +username+ "\",\"password\":\"" +password+ "\",\"character\":" + c.toJson()+ "}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/saveCharacter", urlParameters);
	}
	
	private void deleteCharacter(Character c) throws Exception {
		String urlParameters = "{\"username\":\"" +username+ "\",\"password\":\"" +password+ "\",\"ref\":" +c.getId()+ "}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/deleteCharacter", urlParameters);
	}

	private void saveTeam(Team t) throws Exception {
		String urlParameters = "{\"username\":\"" +username+ "\",\"password\":\"" +password+ "\",\"team\":" + t.toJson()+ "}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/saveTeam", urlParameters);
	}
	
	private void deleteTeam(Team t) throws Exception {
		String urlParameters = "{\"username\":\"" +username+ "\",\"password\":\"" +password+ "\",\"ref\":" +t.getId()+ "}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/deleteTeam", urlParameters);
	}

	public Player login(String _username, String _password) throws Exception {
		String urlParameters = "{\"username\":\"" +_username+ "\",\"password\":\"" +_password+ "\"}";
		System.out.println(urlParameters);
		if(sendPost("https://multiplayer-mambab.c9users.io/login", urlParameters) == 200) {
			username = _username;
			password = _password;
			Player p = new Player(0, "", null);
			JSONParser parser = new JSONParser();
			JSONObject playerJSON = (JSONObject) parser.parse(response.toString());
			System.out.println(playerJSON.get("characters").toString());
			return p;
		}
		else return null;
	}

	public void register(String _username, String _password) throws Exception {
		String urlParameters = "{\"username\":\"" +_username+ "\",\"password\":\"" +_password+ "\"}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/register", urlParameters);
	}

}