package ticket.booking.util;

import org.mindrot.jbcrypt.BCrypt;

public class UserServiceUtil {

 // Method to check if the plain password matches the hashed password
 public static boolean checkPassword(String plainPassword, String hashedPassword) {
  return BCrypt.checkpw(plainPassword, hashedPassword);
 }

 // Method to hash a plain password using BCrypt
 public static String hashPassword(String plainPassword) {
  // Generate a salt and hash the password
  return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
 }
}

