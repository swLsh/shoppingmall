package com.koreamall.dto.user;


import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialUserDTO {
    private String id;
    private String ci;
    private String name;
    private String email;
    private String nickName;
    private String profileImageUrl;
}
