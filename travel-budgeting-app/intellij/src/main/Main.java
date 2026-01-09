package main;
import java.time.*;

public class Main {
    static User user = new User();
    static UI ui = new UI();

    public static void main(String[] args) {
        User firstFriend = new User();
        firstFriend.name = "John";
        user.friends.add(firstFriend);
        ui.play();

    }
}