
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
        World world = Bukkit.getWorld(locationObj.get("world").getAsString());
        double x = locationObj.get("x").getAsDouble();
        double y = locationObj.get("y").getAsDouble();
        double z = locationObj.get("z").getAsDouble();
        float yaw = locationObj.get("yaw").getAsFloat();
        float pitch = locationObj.get("pitch").getAsFloat();

        return new Location(world, x, y, z, yaw, pitch);
    }
}
