import BaseService from '../base-service';

class OAuthPermissionService extends BaseService<
  API.OAuth.OAuthPermissionVO,
  API.OAuth.OAuthPermissionQuery
> {
  constructor() {
    super('/oauth/permissions');
  }
}

export default OAuthPermissionService;
