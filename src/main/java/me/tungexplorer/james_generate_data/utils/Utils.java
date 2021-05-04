package me.tungexplorer.james_generate_data.utils;

import lombok.NonNull;
import me.tungexplorer.james_generate_data.dto.User;

import java.util.Base64;

public class Utils {

    public static String genAuthorization(@NonNull User user, String password) {
        byte[] decodeValue = Base64.getEncoder().encode(String.format("%s:%s", user.getUsername(), password).getBytes());
        return String.format("Basic %s", new String(decodeValue));
    }
}
