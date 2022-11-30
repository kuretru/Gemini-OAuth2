import { request } from 'umi';

async function statistics(): Promise<API.ApiResponse<API.Dashboard.OAuthApplicationDTO>> {
  return request('/api/dashboard/statistics', {
    method: 'get',
  });
}

export { statistics };
