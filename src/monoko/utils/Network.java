package monoko.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.json.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import monoko.objects.Character;
import monoko.objects.Game;
import monoko.objects.Player;
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
		http.register("p", "p");
		http.register("patol", "patol");
		http.register("Mambab", "azerty");
		/*
		List<Character> l = new ArrayList<Character>();
		Character c1 = new Character(0, "Escanor", new Soul("Fighter"), new Soul("Ross'Fert"));
		Character c2 = new Character(1, "Phillipe", new Soul("Hunter"), new Soul("Simmenoid"));
		Character c3 = new Character(2, "Jean", new Soul("Cleric"), null);
		l.add(c1);
		l.add(c2);
		l.add(c3);
		c1.addSkill(new Skill("Sword"));
		c3.addSkill(new Skill("Scepter"));
		Team t = new Team(0, "lolilol", l);
		http.login("p", "p");
		http.joinGame(t);
		http.login("Mambab", "azerty");
		http.joinGame(t);*/
		//http.invitePlayer("p");
		/*http.login("p", "p");
		http.joinGame(t);
		http.login("Mambab", "azerty");
		QueueInfo queue = http.updateQueue();
		for(String s : queue.getWaitingPlayers()) {
			System.out.println(s);
			http.invitePlayer(s);
		}
		http.login("p", "p");
		queue = http.updateQueue();
		Game game = new Game(null, null);
		for(Game g : queue.getWaitingGames()) {
			if(!g.getStatus().equals("ended")) {
				game = g;
			}
		}
		http.acceptGame(game);
		Action action = new Action(0,1,1,"Sword",10,5);
		game.addAction(action);
		http.updateGame(game, action);
		action = new Action(1,2,2,"Sword",10,5);
		http.updateGame(game, action);
		http.getGameUpdates(game);
		http.endGame(game);*//*
		http.register("p", "p");
		http.register("Mambab", "azerty");*/
	}
	
	public Network() {
		setUser(new User());
	}

	public Network(User user) {
		setUser(user);
	}
	
	private int sendPost(String url, String urlParameters) throws Exception {
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
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
				Character character = new Character(c);
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

	@SuppressWarnings("unchecked")
	public void joinGame(Team t) throws Exception {
		Random random = new Random();
		JSONObject urlParametersJSON = new JSONObject();
		JSONObject teamJSON = new JSONObject();
		JSONArray teamCharactersJSON = new JSONArray();
		urlParametersJSON.put("username", user.getUsername());
		urlParametersJSON.put("password", user.getPassword());
		if (t!=null) {
			for(int i=0; i<t.getCharacters().size(); i++) {
				teamCharactersJSON.add(t.getCharacters().get(i).toJson());
			}
		}
		teamJSON.put("ref", random.nextInt(1000000000));
		teamJSON.put("name", t.getName());
		teamJSON.put("characters", teamCharactersJSON);
		urlParametersJSON.put("team", teamJSON);
		System.out.println(urlParametersJSON.toString());
		sendPost("https://multiplayer-mambab.c9users.io/joinGame", urlParametersJSON.toString());
		System.out.println(response);
	}

	public QueueInfo updateQueue() throws Exception {
		String urlParameters = "{\"username\":\"" +user.getUsername()+ "\",\"password\":\"" +user.getPassword()+ "\"}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/updateQueue", urlParameters);
		System.out.println(response);
		List<String> waitingPlayers = new ArrayList<String>();
		List<Game> waitingGames = new ArrayList<Game>();
		JsonReader reader = Json.createReader(new StringReader(response.toString()));
		JsonObject status = reader.readObject();
		JsonArray waitingPlayersJson = status.getJsonArray("playersList");
		for(int i=0; i<waitingPlayersJson.size(); i++) {
			JsonObject p = waitingPlayersJson.getJsonObject(i);
			waitingPlayers.add(p.getString("username"));
		}
		
		JsonArray waitingGamesJson = status.getJsonArray("gamesList");
		for(int i=0; i<waitingGamesJson.size(); i++) {
			JsonObject g = waitingGamesJson.getJsonObject(i);
			if(!(g.getString("status").equals("ended"))) {
				List<Character> cl1 = new ArrayList<Character>();
				JsonObject teamJson = g.getJsonObject("team1");
				JsonArray charactersJson = teamJson.getJsonArray("characters");
				for(int j=0; j<charactersJson.size(); j++) {
					JsonObject c = charactersJson.getJsonObject(j);
					Character character = new Character(c);
					cl1.add(character);
				}
				Team t1 = new Team(teamJson.getInt("ref"), teamJson.getString("name"), cl1);
				Player p1 = new Player(1, g.getString("player1"), t1);
				
				List<Character> cl2 = new ArrayList<Character>();
				teamJson = g.getJsonObject("team2");
				charactersJson = teamJson.getJsonArray("characters");
				for(int j=0; j<charactersJson.size(); j++) {
					JsonObject c = charactersJson.getJsonObject(j);
					Character character = new Character(c);
					cl2.add(character);
				}
				Team t2 = new Team(teamJson.getInt("ref"), teamJson.getString("name"), cl2);
				Player p2 = new Player(1, g.getString("player2"), t2);
				
				Game game = new Game(p1, p2);
				game.setStatus(g.getString("status"));
				game.setGameID(g.getString("gameID"));
				waitingGames.add(game);
			}
		}
		
		QueueInfo queue = new QueueInfo(waitingPlayers, waitingGames);
		return queue;
	}

	public void invitePlayer(String opponentUsername) throws Exception {
		String urlParameters = "{\"username\": \"" +user.getUsername()+ "\", \"password\" : \"" +user.getPassword()+ "\", \"opponentUsername\" : \"" +opponentUsername+ "\"}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/invitePlayer", urlParameters);
	}

	public void acceptGame(Game game) throws Exception {
		String urlParameters = "{\"username\": \"" +user.getUsername()+ "\", \"password\" : \"" +user.getPassword()+ "\", \"gameID\" : \"" +game.getGameID()+ "\"}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/acceptGame", urlParameters);
	}

	public void endGame(Game game) throws Exception {
		String urlParameters = "{\"username\": \"" +user.getUsername()+ "\", \"password\" : \"" +user.getPassword()+ "\", \"gameID\" : \"" +game.getGameID()+ "\"}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/endGame", urlParameters);
	}

	public void updateGame(Game game, Action action) throws Exception {
		String urlParameters = "{\"username\": \"" +user.getUsername()+ "\", \"password\" : \"" +user.getPassword()+ "\", \"gameID\" : \"" +game.getGameID()+ "\", \"action\" : " +action.toJson()+ "}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/updateGame", urlParameters);
	}

	public void getGameUpdates(Game game) throws Exception {
		String urlParameters = "{\"username\": \"" +user.getUsername()+ "\", \"password\" : \"" +user.getPassword()+ "\", \"gameID\" : \"" +game.getGameID()+ "\"}";
		System.out.println(urlParameters);
		sendPost("https://multiplayer-mambab.c9users.io/getGameUpdates", urlParameters);
		JsonReader reader = Json.createReader(new StringReader(response.toString()));
		JsonObject gameJson = reader.readObject();
		JsonArray actions = gameJson.getJsonArray("actions");
		for(int i=0; i<actions.size(); i++) {
			JsonObject actionJson = actions.getJsonObject(i);
			if(actionJson.getInt("id")>game.getLastActionID())
				game.addAction(new Action(actionJson.getInt("id"), actionJson.getInt("characterID"), actionJson.getInt("teamID"), actionJson.getString("skillName"), actionJson.getInt("posX"), actionJson.getInt("posY")));
		}
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}