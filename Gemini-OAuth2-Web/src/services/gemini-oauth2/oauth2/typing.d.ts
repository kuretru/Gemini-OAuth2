declare namespace API.OAuth2 {
  type OAuth2ApplicationDTO = API.BaseDTO & {
    name: string;
    avatar: string;
    description: string;
    homepage: string;
    callback: number;
  };

  type OAuth2ApplicationQuery = API.PaginationQuery & {
    name?: string;
  };
}
