package monoko.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import javax.json.*;
import java.util.ArrayList;
import java.util.List;

import monoko.objects.Character;
import monoko.objects.Skill;
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
		Character c1 = new Character(0, "Escanor", new Soul("Fighter"), new Soul("Ross'Fert"));
		Character c2 = new Character(1, "Phillipe", new Soul("Hunter"), new Soul("Simmenoid"));
		Character c3 = new Character(2, "Jean", new Soul("Cleric"), null);
		l.add(c1);
		l.add(c2);
		l.add(c3);
		c1.addSkill(new Skill("Sword"));
		c3.addSkill(new Skill("Scepter"));
		http.login("Mambab", "azerty");
		/*
		http.saveCharacter(c1);
		http.saveCharacter(c2);
		http.saveCharacter(c3);
		*/
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

	public void saveCharacter(Character c) throws Exception {
		String urlParameters = "{\"username\":\"" +user.getUsername()+ "\",\"password\":\"" +user.getPassword()+ "\",\"character\":" + c.toJson()+ "}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/saveCharacter", urlParameters);
	}
	
	public void deleteCharacter(Character c) throws Exception {
		String urlParameters = "{\"username\":\"" +user.getUsername()+ "\",\"password\":\"" +user.getPassword()+ "\",\"ref\":" +c.getId()+ "}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/deleteCharacter", urlParameters);
	}

	public void saveTeam(Team t) throws Exception {
		String urlParameters = "{\"username\":\"" +user.getUsername()+ "\",\"password\":\"" +user.getPassword()+ "\",\"team\":" + t.toJson()+ "}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/saveTeam", urlParameters);
	}
	
	public void deleteTeam(Team t) throws Exception {
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
			JsonReader reader = Json.createReader(new StringReader(response.toString()));
			JsonObject userJson = reader.readObject();
			JsonArray characters = userJson.getJsonArray("characters");
			for(int i=0; i<characters.size(); i++) {
				JsonObject c = characters.getJsonObject(i);
				Character character = new Character(c.getInt("ref"), c.getString("name"), null, null);
				String job = c.getString("job");
				String god = c.getString("god");
				if(!job.equals("none"))
					character.setJob(new Soul(job));
				if(!god.equals("none"))
					character.setGod(new Soul(god));
				JsonArray skills = c.getJsonArray("skills");
				for(int j=0; j<skills.size(); j++) {
					character.addSkill(new Skill(skills.getString(j)));
				}
				user.addCharacter(character);
			}
			JsonArray teams = userJson.getJsonArray("teams");
			for(int i=0; i<teams.size(); i++) {
				JsonObject t = teams.getJsonObject(i);
				List<Character> teamCharacters = new ArrayList<Character>();
				JsonArray charactersRefs = t.getJsonArray("charactersRefs");
				for(int j=0; j<charactersRefs.size(); j++) {
					teamCharacters.add(user.findCharacter(charactersRefs.getInt(j)));
				}
				user.addTeam(new Team(t.getInt("ref"), t.getString("name"), teamCharacters));
			}
			return user;
		}
		else return null;
	}

	public void register(String _username, String _password) throws Exception {
		String urlParameters = "{\"username\":\"" +_username+ "\",\"password\":\"" +_password+ "\"}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/register", urlParameters);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}