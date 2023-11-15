package cc.altoya.companions;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
public class App extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        initializeConfig();


        //How to register commands
        // this.getCommand("chunk").setExecutor(new Claims());
        this.getCommand("summonHorse").setExecutor(new summonHorseCommand());

        //How to register eventListeners
        getServer().getPluginManager().registerEvents((Listener) this,this);
    }



    private void initializeConfig(){
        File configFile = new File(getDataFolder(), "config.yml");

        if(configFile.exists()){
            return;
        }


        getConfig().options().copyDefaults(true);
        saveConfig();
    }



}