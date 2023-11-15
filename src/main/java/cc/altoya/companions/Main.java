package cc.altoya.companions;

import java.io.File;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        initializeConfig();


        //How to register commands
        // this.getCommand("chunk").setExecutor(new Claims());
        this.getCommand("summonHorse").setExecutor(new SummonHorseCommand());

        //How to register eventListeners
        getServer().getPluginManager().registerEvents(new NoHorseDropsListener(), this);
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