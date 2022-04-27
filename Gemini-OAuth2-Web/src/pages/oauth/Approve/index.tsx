import React from 'react';
import { GridContent } from '@ant-design/pro-layout';
import ProCard from '@ant-design/pro-card';
import { Avatar, Button, List, message, Tooltip } from 'antd';
import {
  ArrowRightOutlined,
  CheckCircleOutlined,
  CloseCircleOutlined,
  MailOutlined,
  PhoneOutlined,
  SmileOutlined,
} from '@ant-design/icons';
import { approve } from '@/services/gemini-oauth2/oauth2';
import OAuthApplicationService from '@/services/gemini-oauth2/oauth/application';
import { getRequestParams } from '@/utils/request-utils';
import styles from './index.less';

interface OAuthApproveProps {}

interface OAuthApproveState {
  application: API.OAuth.OAuthApplicationDTO;
}

class OAuthApprove extends React.Component<OAuthApproveProps, OAuthApproveState> {
  oAuthApplicationService: OAuthApplicationService = new OAuthApplicationService();
  permissions: API.OAuth.OAuthPermissionDTO[] = [
    {
      permission: '',
      avatar: <SmileOutlined />,
      title: '昵称',
      description: '昵称和头像',
    },
    {
      permission: 'email',
      avatar: <MailOutlined />,
      title: '电子邮箱',
      description: '电子邮箱地址',
    },
    {
      permission: 'mobile',
      avatar: <PhoneOutlined />,
      title: '手机号码',
      description: '手机号码',
    },
  ];

  constructor(props: OAuthApproveProps) {
    super(props);
    this.getApplication();
  }

  getApplication = async () => {
    const requestParams = getRequestParams();
    if (!requestParams.application_id) {
      message.error('OAuth应用ID不存在');
      return;
    }
    const application = await this.oAuthApplicationService.get(requestParams.application_id);
    this.setState({ application: application.data });
  };

  userApprove = async (action: string) => {
    const requestParams = getRequestParams();
    const params: API.OAuth2.OAuth2ApproveRequestDTO = {
      token: requestParams.token,
      applicationId: requestParams.application_id,
      userId: localStorage.getItem('userId')!,
      scope: requestParams.scope,
      action: action,
    };
    const response = await approve(params);
    console.log(response);
    window.location.href = response.data;
  };

  actionbutton = [
    <Button key="reject" icon={<CloseCircleOutlined />} onClick={() => this.userApprove('reject')}>
      拒绝
    </Button>,
    <Button
      key="approve"
      icon={<CheckCircleOutlined />}
      type="primary"
      onClick={() => this.userApprove('approve')}
    >
      授权
    </Button>,
  ];

  render() {
    return (
      <GridContent>
        <div className={styles.container}>
          <ProCard title="OAuth2身份认证" actions={this.actionbutton} style={{ width: '64em' }}>
            <div className={styles.avatar}>
              <Avatar size={96} src={this.state?.application.avatar} />
              <ArrowRightOutlined className={styles.arrow} />
              <Avatar size={96} src={this.state?.application.avatar} />
            </div>
            您是否允许
            <Tooltip placement={'bottomLeft'} title={this.state?.application.description}>
              <Button type="link">{this.state?.application.name}</Button>
            </Tooltip>
            进行以下操作：
            <List
              bordered
              dataSource={this.permissions}
              itemLayout={'horizontal'}
              renderItem={(item) => (
                <List.Item>
                  <List.Item.Meta
                    avatar={<Avatar size={40} icon={item.avatar} />}
                    title={<strong>{item.title}</strong>}
                    description={
                      <>
                        <strong>读取{item.permission.endsWith('_w') ? '/修改' : ''}</strong>您的
                        {item.description}
                      </>
                    }
                  />
                </List.Item>
              )}
            />
          </ProCard>
        </div>
      </GridContent>
    );
  }
}

export default OAuthApprove;
