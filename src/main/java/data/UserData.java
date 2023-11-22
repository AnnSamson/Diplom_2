package data;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private String email = "email" + (int)(Math.random() * 999999) + "@mail.ru";
    private String password = "password" + (int)(Math.random() * 999999);
    private String name = "UserName" + (int)(Math.random() * 999999);
}
