
package org.ardal.utils;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {
    public static JsonObject locationToJson(Location location){
        JsonObject locationObj = new JsonObject();
        locationObj.addProperty("world", location.getWorld().getUID().toString());
        locationObj.addProperty("x", location.getX());
        locationObj.addProperty("y", location.getY());
        locationObj.addProperty("z", location.getZ());
        locationObj.addProperty("yaw", location.getYaw());
        locationObj.addProperty("pitch", location.getPitch());

        return locationObj;
    }

    public static Location getLocationFromJson(JsonObject locationObj){
        World world = Bukkit.getWorld(locationObj.getAsJsonObject("world").getAsString());
        double x = locationObj.getAsJsonObject("x").getAsDouble();
        double y = locationObj.getAsJsonObject("y").getAsDouble();
        double z = locationObj.getAsJsonObject("z").getAsDouble();
        float yaw = locationObj.getAsJsonObject("yaw").getAsFloat();
        float pitch = locationObj.getAsJsonObject("pitch").getAsFloat();

        return new Location(world, x, y, z, yaw, pitch);
    }
}
