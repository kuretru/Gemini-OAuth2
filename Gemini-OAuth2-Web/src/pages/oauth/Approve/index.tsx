import React from 'react';
import ProCard from '@ant-design/pro-card';
import { GridContent } from '@ant-design/pro-layout';
import { Avatar, Button, List, message, Tooltip } from 'antd';
import {
  ArrowRightOutlined,
  CheckCircleOutlined,
  CloseCircleOutlined,
  MailOutlined,
  PhoneOutlined,
  SmileOutlined,
} from '@ant-design/icons';
import { approve, isApproved } from '@/services/gemini-oauth2/oauth2';
import OAuthApplicationService from '@/services/gemini-oauth2/oauth/application';
import { get as getUser } from '@/services/gemini-oauth2/user/user';
import { getRequestParams } from '@/utils/request-utils';
import styles from './index.less';

type OAuthPermissionRecord = {
  permission: string;
  avatar: JSX.Element;
  title: string;
  description: string;
};

interface OAuthApproveState {
  application: API.OAuth.OAuthApplicationDTO;
  user: API.User.UserDTO;
  permissions: OAuthPermissionRecord[];
}

class OAuthApprove extends React.Component<Record<string, never>, OAuthApproveState> {
  oAuthApplicationService: OAuthApplicationService = new OAuthApplicationService();
  requestParams = getRequestParams();

  constructor(props: Record<string, never>) {
    super(props);
    this.state = {
      application: {} as API.OAuth.OAuthApplicationDTO,
      user: {} as API.User.UserDTO,
      permissions: [
        {
          permission: '',
          avatar: <SmileOutlined />,
          title: '昵称',
          description: '昵称和头像',
        },
      ],
    };
    this.getPermissions();
    this.getApplication().then(() => {
      this.autoApprove();
    });
  }

  getPermissions = () => {
    const scopes: string[] = this.requestParams.scope ? this.requestParams.scope.split(',') : [];
    const permissions = this.state.permissions;
    if (scopes.find((entry) => entry.startsWith('email'))) {
      permissions.push({
        permission: scopes.find((entry) => entry.startsWith('email'))!,
        avatar: <MailOutlined />,
        title: '电子邮箱',
        description: '电子邮箱地址',
      });
    }
    if (scopes.find((entry) => entry.startsWith('mobile'))) {
      permissions.push({
        permission: scopes.find((entry) => entry.startsWith('mobile'))!,
        avatar: <PhoneOutlined />,
        title: '手机号码',
        description: '手机号码',
      });
    }
  };

  getApplication = async () => {
    if (!this.requestParams.application_id) {
      message.error('OAuth应用ID不存在');
      return;
    }
    const application = await this.oAuthApplicationService.get(this.requestParams.application_id);
    const userId = localStorage.getItem('userId');
    const user = await getUser(userId!);
    this.setState({ application: application.data, user: user.data });
  };

  autoApprove = async () => {
    if (this.requestParams.reapprove) {
      message.info('需要重新授权');
      console.log("自动授权失败：强制重新授权");
      return;
    }
    const params: API.OAuth2.OAuth2ApproveQuery = {
      token: this.requestParams.token,
      applicationId: this.requestParams.application_id,
      userId: localStorage.getItem('userId')!,
      scope: this.requestParams.scope,
    };
    isApproved(params)
      .then((response) => {
        console.log(response);
        window.location.href = response.data;
      })
      .catch(() => {
        console.log("自动授权失败：用户尚未授权");
      });
  };

  userApprove = async (action: string) => {
    const params: API.OAuth2.OAuth2ApproveRequestDTO = {
      token: this.requestParams.token,
      applicationId: this.requestParams.application_id,
      userId: localStorage.getItem('userId')!,
      scope: this.requestParams.scope,
      action: action,
    };
    const response = await approve(params);
    console.log(response);
    window.location.href = response.data;
  };

  actionbutton = [
    <Button
      key="reject"
      icon={<CloseCircleOutlined />}
      onClick={() => this.userApprove('reject')}>
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
          <ProCard className={styles.content} title="OAuth2身份认证" actions={this.actionbutton}>
            <div className={styles.avatar}>
              <Avatar size={96} src={this.state.application.avatar} />
              <ArrowRightOutlined className={styles.arrow} />
              <Avatar size={96} src={this.state.user.avatar} />
            </div>
            您是否允许
            <Tooltip placement={'bottomLeft'} title={this.state.application.description}>
              <Button type="link" style={{ padding: '0 3px' }}>
                <strong>{this.state.application.name}</strong>
              </Button>
            </Tooltip>
            进行以下操作：
            <List
              bordered
              dataSource={this.state.permissions}
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
