package net.xornick.syncaccount.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.xornick.syncaccount.SyncAccount;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RegisterCommand implements CommandExecutor {

    private static SyncAccount instance;
    private JsonParser parser;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        if (args.length != 3) {
            player.sendMessage(ChatColor.WHITE + "/register [username] [password] [email]");
            return true;
        }
        player.sendMessage(ChatColor.AQUA + "We're attempting to create you a forum account");
        instance.getServer().getScheduler().runTaskAsynchronously(instance, new Runnable() {
            @Override
            public void run() {
                try {
                    String link = "api.php?action=register&hash=" + instance.getAPI_KEY() + "&username=" + args[0] + "&password=" + args[1] + "&email=" + args[2] + "&custom_fields=minecraft=" + instance.getName() + "&user_state=email_confirm";
                    URL url = new URL(String.valueOf(instance.getAPI_URL()) + link);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String input;
                    if ((input = reader.readLine()) != null) {
                        JsonObject object = (JsonObject) parser.parse(input);
                        if (object.get("error") != null && object.get("message").toString().contains("hash")) {
                            instance.getServer().getLogger().severe("Incorrect hash!");
                            player.sendMessage(ChatColor.RED + "Something is wrong with our internal forum hook. Please try again later.");
                            return;
                        }
                        if (object.get("error") != null && object.get("message").toString().contains("Name length is too short")) {
                            player.sendMessage(ChatColor.RED + "Name length is too short, please try again!");
                            return;
                        }
                        if (object.get("error") != null && object.get("message").toString().contains("password")) {
                            player.sendMessage(ChatColor.RED + "That password is not valid, please try a different one!");
                            return;
                        }
                        if (object.get("error") != null && object.get("message").toString().contains("User already exists")) {
                            player.sendMessage(ChatColor.RED + "That forum username already exists, please try a different one!");
                            return;
                        }
                        if (object.get("error") != null && object.get("message").toString().contains("Please enter a valid email")) {
                            player.sendMessage(ChatColor.RED + "That email address isn't valid, please try a different one!");
                            return;
                        }
                        if (object.get("error") != null && object.get("message").toString().contains("Email address must be unique")) {
                            player.sendMessage(ChatColor.RED + "There's currently an existing forum account on that email, please try a different one!");
                            return;
                        }
                        player.sendMessage(ChatColor.GREEN + "Success! Please check your email inbox for your confirmation email");
                        player.sendMessage(ChatColor.GREEN + instance.getAPI_URL());
                    }
                }
                catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        return true ;
    }

    public RegisterCommand() {
        this.parser = new JsonParser();
    }
}
