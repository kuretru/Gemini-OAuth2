import BaseService from '../base-service';

class OAuthApplicationService extends BaseService<
  API.OAuth.OAuthApplicationDTO,
  API.OAuth.OAuthApplicationQuery
> {
  constructor() {
    super('/oauth/applications');
  }
}

export default OAuthApplicationService;
