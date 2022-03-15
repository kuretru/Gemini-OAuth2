import BaseService from '../base-service';

class OAuth2ApplicationService extends BaseService<
  API.OAuth2.OAuth2ApplicationDTO,
  API.OAuth2.OAuth2ApplicationQuery
> {
  constructor() {
    super('/oauth2/applications');
  }
}

export default OAuth2ApplicationService;
