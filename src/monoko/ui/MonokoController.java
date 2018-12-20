package monoko.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.layout.AnchorPane;
import monoko.objects.User;
import monoko.utils.FxmlManager;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class MonokoController extends MonokoBase{

	User _user;
	String _backgroundMusic;
	AudioStream BGM;
	InputStream test;
	
	public MonokoController() {
		_backgroundMusic = "./res/sound/menu-theme-dofus.wav";
		music();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		_rootAnchorPane.getChildren().add(new FxmlManager("./ui/login.fxml", new LoginController(this)).load());
	}

	@Override
	public AnchorPane getRootAnchorPane() {
		return _rootAnchorPane;
	}

	public void setRootAnchorPane(AnchorPane _rootAnchorPane) {
		this._rootAnchorPane = _rootAnchorPane;
	}
	
	public void music(){
		AudioPlayer MGP = AudioPlayer.player;
        ContinuousAudioDataStream loop = null;
        MGP.stop(BGM);
        try
        {
            test = new FileInputStream(_backgroundMusic);
            BGM = new AudioStream(test);
            MGP.start(BGM);
            //MD = BGM.getData();
            //loop = new ContinuousAudioDataStream(MD);

        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
        MGP.start(loop);
    }
	
	
	//GETTERS SETTERS
	public User getUser() {
		return _user;
	}

	public void setUser(User player) {
		this._user = player;
	}
	public String getBackgroundMusic() {
		return _backgroundMusic;
	}

	public void setBackgroundMusic(String backgroundMusic) {
		this._backgroundMusic = backgroundMusic;
		this.music();
	}

	public void stopMusic() {
		AudioPlayer.player.stop(BGM);
	}
	
}
