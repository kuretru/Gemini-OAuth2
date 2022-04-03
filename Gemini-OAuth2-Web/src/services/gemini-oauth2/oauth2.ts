import { request } from 'umi';

async function approve(
  record: API.OAuth2.OAuth2ApproveRequestDTO,
): Promise<API.ApiResponse<string>> {
  return request('/api/oauth2/approve', {
    method: 'post',
    data: record,
  });
}

export { approve };
