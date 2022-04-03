import React from 'react';
import { GridContent } from '@ant-design/pro-layout';
import ProCard from '@ant-design/pro-card';
import { Button } from 'antd';
import { CheckCircleOutlined, CloseCircleOutlined } from '@ant-design/icons';
import { approve } from '@/services/gemini-oauth2/oauth2';
import { getRequestParams } from '@/utils/request-utils';

interface OAuthApproveProps {}

interface OAuthApproveState {}

class OAuthApprove extends React.Component<OAuthApproveProps, OAuthApproveState> {
  fetchData = (action: string) => {
    const requestParams = getRequestParams();
    const params: API.OAuth2.OAuth2ApproveRequestDTO = {
      token: requestParams.token,
      applicationId: requestParams.application_id,
      userId: sessionStorage.getItem('userId')!,
      scope: requestParams.scope,
      action: action,
    };
    console.log(params);
    return approve(params);
  };

  actionbutton = [
    <Button key="reject" icon={<CloseCircleOutlined />} onClick={() => this.fetchData('reject')}>
      拒绝
    </Button>,
    <Button
      key="approve"
      icon={<CheckCircleOutlined />}
      type="primary"
      onClick={() => this.fetchData('approve')}
    >
      授权
    </Button>,
  ];

  render() {
    return (
      <GridContent>
        <ProCard title="xx" actions={this.actionbutton}>
          内容
        </ProCard>
      </GridContent>
    );
  }
}

export default OAuthApprove;
