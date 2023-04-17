// @ts-ignore
/* eslint-disable */

declare namespace API.OAuth {
  type OAuthApplicationDTO = API.BaseDTO & {
    name: string;
    avatar: string;
    description: string;
    homepage: string;
    callback: number;
  };
  type OAuthApplicationQuery = API.PaginationQuery & {
    name?: string;
  };
  type OAuthApplicationVO = API.BaseDTO & {
    name: string;
    avatar: string;
    description: string;
    homepage: string;
  };
  type OAuthApplicationSecretVO = API.BaseDTO & {
    clientId: string;
    clientSecret: string;
    secretCreateTime: string;
  };

  type OAuthPermissionDTO = API.BaseDTO & {
    applicationId: string;
    userId: string;
    permissions: string[];
  };
  type OAuthPermissionQuery = API.PaginationQuery & {
    applicationId: string;
    userId: string;
  };
  type OAuthPermissionVO = API.BaseDTO & {
    permissions: string[];
    application: OAuthApplicationVO;
    user: API.User.UserVO;
  };
}
