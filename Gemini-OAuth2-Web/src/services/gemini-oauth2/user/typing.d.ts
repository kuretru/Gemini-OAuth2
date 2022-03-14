declare namespace API.User {
  type UserDTO = API.BaseDTO & {
    username: string;
    nickname: string;
    avatar: string;
    email: string;
    mobile: string;
    isAdmin: boolean;
  };
  type UserQuery = {
    username?: string;
    nickname?: string;
    email?: string;
    mobile?: string;
    isAdmin?: boolean;
  };

  type UserLoginDTO = {
    userId: string;
    accessToken: {
      id: string;
      secret: string;
    };
  };
  type UserLoginQuery = {
    username: string;
    password: string;
    captcha?: string;
  };
}
