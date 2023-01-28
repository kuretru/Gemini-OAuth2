// @ts-ignore
/* eslint-disable */

declare namespace API.OAuth2 {
  type OAuth2ApproveQuery = {
    token: string;
    applicationId: string;
    userId: string;
    scope: string;
  };

  type OAuth2ApproveRequestDTO = OAuth2ApproveQuery & {
    action: string;
  };
}
