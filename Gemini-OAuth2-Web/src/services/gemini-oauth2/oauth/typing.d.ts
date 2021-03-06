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

  type OAuthPermissionDTO = {
    permission: string;
    avatar: JSX;
    title: string;
    description: string;
  };
}
