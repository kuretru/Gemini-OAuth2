declare namespace API.OAuth2 {
  type OAuth2ApproveRequestDTO = {
    token: string;
    applicationId: string;
    userId: string;
    scope: string;
    action: string;
  };
}
