package repositories;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import model.Row;
import repositories.db.repositories.DBGameRepository;
import repositories.db.repositories.DBGame_UserRepository;
import repositories.db.repositories.DBUserRepository;
import repositories.db.structure.Game;
import repositories.db.structure.Game_User;
import repositories.db.structure.Game_User_ID;
import repositories.db.structure.User;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 1/16/2015.
 */
@Singleton
public class GameRepository {
    //@Inject private ArrayList<String> users;
    //@Inject private ArrayList<String> passwords;
    @Inject private DBGameRepository dbGameRepository;
    @Inject private DBGame_UserRepository dbGame_userRepository;
    @Inject private DBUserRepository dbUserRepository;

    /*dbUserRepository.persist(makeUser("Chris"));
    dbUserRepository.persist(makeUser("Arno"));
    dbUserRepository.persist(makeUser("Andre"));
    dbUserRepository.persist(makeUser("Dihan"));
    dbUserRepository.persist(makeUser("Eduan"));
    dbUserRepository.persist(makeUser("Albert"));

    private User makeUser(String username){
        User user = new User();
        user.setUser_name(username);
        user.setPassword("1000:8efcc67e8c7863b679da5d609a533ba0dd2b5a1a31ffc5fc:9820dd47727ca04fd447051804f9fde38c7f7de594290c16"); //A hash for a
        return user;
    }*/

    public void commitGame(String gamename, List<String> users, List<String> hands){
        Game newGame = new Game();
        newGame.setGame_name(gamename);
        newGame.setGame_date(new Date());
        dbGameRepository.persist(newGame);

        for(int i = 0; i < users.size(); i++) {
            Game_User_ID game_user_id = new Game_User_ID();
            game_user_id.setGame_id(newGame.getGame_id());
            game_user_id.setUser_name(users.get(i));

            Game_User game_user = new Game_User();
            game_user.setId(game_user_id);
            game_user.setHand(hands.get(i));
            dbGame_userRepository.persist(game_user);
        }
    }

    public boolean gameExists(String gamename){
        Game game = dbGameRepository.getGameByName(gamename);
        if (game != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<Row> getAllGamesInRows(){
        return dbGame_userRepository.getAllGamesInRows();
    }











    public void insertUser(String username, String password){
        try {
            String pass = createHash(password);
            User user = new User();
            user.setUser_name(username);
            user.setPassword(pass);
            dbUserRepository.persist(user);
        }
        catch(NoSuchAlgorithmException e){
            System.out.println(e.getMessage());
        }
        catch(InvalidKeySpecException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean comparePass(String username, String password){
        try {
            if(getPass(username) != null)
                return validatePassword(password, getPass(username));
        }
        catch(NoSuchAlgorithmException e){
            System.out.println(e.getMessage());
        }
        catch(InvalidKeySpecException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String getPass(String username){
            User user = dbUserRepository.getUserByName(username);
            if (user != null) {
                return user.getPassword();
            } else {
                return null;
            }
    }

    public boolean userExists(String username){
            User user = dbUserRepository.getUserByName(username);
            if (user != null) {
                return true;
            } else {
                return false;
            }
    }

    public List<String> getAllUserNames(){
        List<User> users = dbUserRepository.getAllUsers();
        List<String> names = new ArrayList<>();
        for(User user : users){
            names.add(user.getUser_name());
        }
        return names;
    }





    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    public static final int SALT_BYTE_SIZE = 24;
    public static final int HASH_BYTE_SIZE = 24;
    public static final int PBKDF2_ITERATIONS = 1000;

    public static final int ITERATION_INDEX = 0;
    public static final int SALT_INDEX = 1;
    public static final int PBKDF2_INDEX = 2;

    public static String createHash(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        return createHash(password.toCharArray());
    }

    public static String createHash(char[] password)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);

        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" +  toHex(hash);
    }

    public static boolean validatePassword(String password, String correctHash)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        return validatePassword(password.toCharArray(), correctHash);
    }

    public static boolean validatePassword(char[] password, String correctHash)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[ITERATION_INDEX]);
        byte[] salt = fromHex(params[SALT_INDEX]);
        byte[] hash = fromHex(params[PBKDF2_INDEX]);

        byte[] testHash = pbkdf2(password, salt, iterations, hash.length);

        return slowEquals(hash, testHash);
    }

    private static boolean slowEquals(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }

    private static byte[] fromHex(String hex)
    {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++)
        {
            binary[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }

    private static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }
}
