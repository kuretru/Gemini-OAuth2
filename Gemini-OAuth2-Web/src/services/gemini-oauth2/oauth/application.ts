import { request } from 'umi';
import BaseService from '../base-service';

class OAuthApplicationService extends BaseService<
  API.OAuth.OAuthApplicationDTO,
  API.OAuth.OAuthApplicationQuery
> {
  constructor() {
    super('/oauth/applications');
  }

  async getSecret(id: string): Promise<API.ApiResponse<API.OAuth.OAuthApplicationSecretVO>> {
    return request<API.ApiResponse<API.OAuth.OAuthApplicationSecretVO>>(
      `/api/oauth/applications/${id}/secret`,
      {
        method: 'get',
      },
    );
  }

  async generateSecret(id: string): Promise<API.ApiResponse<API.OAuth.OAuthApplicationSecretVO>> {
    return request<API.ApiResponse<API.OAuth.OAuthApplicationSecretVO>>(
      `/api/oauth/applications/${id}/secret`,
      {
        method: 'post',
      },
    );
  }
}

export default OAuthApplicationService;
